import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ArticleSearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { ArticleFullDto, ArticleSearchDto, CreateArticleDto, ReadArticleSummaryDto, UpdateArticleDto } from "../models/Article";
import { Observable } from "rxjs";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";

@Injectable({
    providedIn: 'root'
})
export class ArticleService {
    private apiUrl = "http://localhost:8080/api/v1/articles";

    constructor(
        private http: HttpClient,
        private urlBuilderService: SearchUrlBuilderService
    ) {}

    searchArticles(searchParams: ArticleSearchParams): Observable<PaginatedResults<ArticleSearchDto>> {
        const url = this.urlBuilderService.buildSearchUrl(this.apiUrl + "/search", searchParams);

        return this.http.get<PaginatedResults<ArticleSearchDto>>(url);
    }

    getArticle(articleId: number): Observable<ArticleFullDto> {
        return this.http.get<ArticleFullDto>(`${this.apiUrl}/${articleId}`);
    }

    readArticle(articleId: number, userId: number): Observable<ReadArticleSummaryDto> {
        return this.http.post<ReadArticleSummaryDto>(`${this.apiUrl}/read`, { articleId, userId });
    }

    createArticle(articleDto: CreateArticleDto): Observable<ArticleFullDto> {
        return this.http.post<ArticleFullDto>(this.apiUrl, articleDto);
    }

    updateArticle(articleDto: UpdateArticleDto): Observable<ArticleFullDto> {
        return this.http.put<ArticleFullDto>(this.apiUrl, articleDto);
    }

    deleteArticle(articleId: number) {
        return this.http.delete(`${this.apiUrl}/${articleId}`);
    }
}

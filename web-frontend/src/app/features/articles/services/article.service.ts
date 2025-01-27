import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ArticleSearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { ArticleFullDto, ArticleSearchDto, CreateArticleDto, UpdateArticleDto } from "../models/Article";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ArticleService {
    private apiUrl = "http://localhost:8080/api/v1/articles";

    constructor(private http: HttpClient) {}

    searchArticles(searchParams: ArticleSearchParams): Observable<PaginatedResults<ArticleSearchDto>> {
        const url = this.apiUrl +
            "/search?searchQuery=" + (searchParams.searchQuery ?? "") +
            "&sortBy=" + (searchParams.sortBy ?? "createdAt") +
            "&isAscending=" + (searchParams.isAscending ?? true) +
            "&page=" + (searchParams.page ?? 1) +
            "&itemsPerPage=" + (searchParams.itemsPerPage ?? 20);

        return this.http.get<PaginatedResults<ArticleSearchDto>>(url);
    }

    getArticle(articleId: number): Observable<ArticleFullDto> {
        return this.http.get<ArticleFullDto>(`${this.apiUrl}/${articleId}`);
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

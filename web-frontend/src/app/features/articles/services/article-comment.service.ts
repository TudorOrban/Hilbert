import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { SearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { Observable } from "rxjs";
import { ArticleCommentDto, CreateCommentDto } from "../models/ArticleComment";
import { SearchUrlBuilderService } from "../../../shared/search/services/SearchUrlBuilderService";

@Injectable({
    providedIn: 'root'
})
export class ArticleCommentService {
    private apiUrl = "http://localhost:8080/api/v1/article-comments";

    constructor(
        private http: HttpClient,
        private urlBuilderService: SearchUrlBuilderService,
    ) {}

    searchComments(searchParams: SearchParams): Observable<PaginatedResults<ArticleCommentDto>> {
        const url = this.urlBuilderService.buildSearchUrl(this.apiUrl + "/search", searchParams);

        return this.http.get<PaginatedResults<ArticleCommentDto>>(url);
    }

    createComment(commentDto: CreateCommentDto): Observable<ArticleCommentDto> {
        return this.http.post<ArticleCommentDto>(this.apiUrl, commentDto);
    }

    deleteComment(commentId: number) {
        this.http.delete(`${this.apiUrl}/${commentId}`);
    }
}
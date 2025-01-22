import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ArticleService } from "../../services/article.service";

@Component({
    selector: "app-article",
    imports: [],
    templateUrl: "./article.component.html",
    styleUrl: "./article.component.css",
})
export class ArticleComponent implements OnInit {
    articleId?: number;

    constructor(
        private readonly route: ActivatedRoute,
        private readonly articleService: ArticleService
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.articleId = Number(params.get("articleId"));

            this.loadArticle();
        });
    }

    private loadArticle() {
        if (!this.articleId) {
            return;
        }

        this.articleService.getArticle(this.articleId).subscribe(
            (data) => {
                console.log("Data: ", data);
            },
            (error) => {
                console.error("Error: ", error);
            }
        );
    }
}

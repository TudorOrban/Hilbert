import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { ArticleService } from "../../services/article.service";
import { ArticleFullDto } from "../../models/Article";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faEllipsisVertical, faEye, faSquareCheck, faStar } from "@fortawesome/free-solid-svg-icons";
import { LanguageOptionsService } from "../../../../shared/language/services/language-options.service";
import { OverlayedTextComponent } from "../overlayed-text/overlayed-text.component";

@Component({
    selector: "app-article",
    imports: [FontAwesomeModule, OverlayedTextComponent],
    templateUrl: "./article.component.html",
    styleUrl: "./article.component.css",
})
export class ArticleComponent implements OnInit {
    articleId?: number;
    article?: ArticleFullDto;

    languageService: LanguageOptionsService;

    constructor(
        private readonly route: ActivatedRoute,
        private readonly articleService: ArticleService,
        languageService: LanguageOptionsService
    ) {
        this.languageService = languageService;
    }

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
                this.article = data;
            },
            (error) => {
                console.error("Error: ", error);
            }
        );
    }

    faStar = faStar;
    faEye = faEye;
    faSquareCheck = faSquareCheck;
    faEllipsisVertical = faEllipsisVertical;
}

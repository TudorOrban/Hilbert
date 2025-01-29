import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ArticleService } from "../../services/article.service";
import { ArticleFullDto } from "../../models/Article";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { faAngleRight, faCheck, faEllipsisVertical } from "@fortawesome/free-solid-svg-icons";
import { LanguageOptionsService } from "../../../../shared/language/services/language-options.service";
import { OverlayedTextComponent } from "./overlayed-text/overlayed-text.component";
import { AuthService } from "../../../../core/user/services/auth.service";
import { ArticleHeaderComponent } from "./article-header/article-header.component";

@Component({
    selector: "app-article",
    imports: [FontAwesomeModule, OverlayedTextComponent, ArticleHeaderComponent],
    templateUrl: "./article.component.html",
    styleUrl: "./article.component.css",
})
export class ArticleComponent implements OnInit {
    articleId?: number;
    article?: ArticleFullDto;
    userId?: number;

    languageService: LanguageOptionsService;

    constructor(
        languageService: LanguageOptionsService,
        private readonly articleService: ArticleService,
        private readonly authService: AuthService,
        private readonly route: ActivatedRoute,
        private readonly router: Router,
    ) {
        this.languageService = languageService;
    }

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.articleId = Number(params.get("articleId"));

            this.loadArticle();
        });
        this.authService.getCurrentUser().subscribe((user) => {
            this.userId = user?.id;
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

    markArticleAsRead() {
        if (!this.userId) {
            console.error("You are not logged in");
            return;
        }
        if (!this.articleId) {
            return;
        }
        
        this.articleService.readArticle(this.articleId, this.userId).subscribe(
            (data) => {
                this.router.navigate([`/reading/${this.articleId}/read`], {
                    state: { 
                        summary: data,
                        article: this.article,
                    }
                });
            },
            (error) => {
                console.error("Failed to Mark as Read: ", error);
            }
        );        
    }

    faEllipsisVertical = faEllipsisVertical;
    faCheck = faCheck;
    faAngleRight = faAngleRight;
}

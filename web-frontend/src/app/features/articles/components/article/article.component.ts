import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { ArticleService } from "../../services/article.service";
import { ArticleFullDto } from "../../models/Article";
import { LanguageOptionsService } from "../../../../shared/language/services/language-options.service";
import { OverlayedTextComponent } from "./overlayed-text/overlayed-text.component";
import { AuthService } from "../../../../core/user/services/auth.service";
import { ArticleHeaderComponent } from "./article-header/article-header.component";
import { OptionsBarComponent } from "./options-bar/options-bar.component";
import { UserDataDto } from "../../../../core/user/models/User";
import { Language } from "../../../../shared/language/models/Language";

@Component({
    selector: "app-article",
    imports: [OverlayedTextComponent, ArticleHeaderComponent, OptionsBarComponent],
    templateUrl: "./article.component.html",
    styleUrl: "./article.component.css",
})
export class ArticleComponent implements OnInit {
    articleId?: number;
    article?: ArticleFullDto;
    user?: UserDataDto;

    isArticleRead?: boolean = false;

    constructor(
        readonly languageService: LanguageOptionsService,
        private readonly articleService: ArticleService,
        private readonly authService: AuthService,
        private readonly route: ActivatedRoute,
        private readonly router: Router,
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe((user) => {
            if (!user) {
                return;
            }
            this.user = user;

            this.route.paramMap.subscribe((params) => {
                this.articleId = Number(params.get("articleId"));

                this.loadArticle();
            });
        });
    }

    private loadArticle(): void {
        if (!this.articleId) {
            return;
        }

        this.articleService.getArticle(this.articleId).subscribe(
            (data) => {
                this.article = data;
                this.updateIsArticleRead();
            },
            (error) => {
                console.error("Error: ", error);
            }
        );
    }

    updateIsArticleRead(): void {
        const foundArticleId = this.user?.profileDto?.learningData?.languageData?.[this.article?.language ?? Language.NONE]
            ?.readArticleIds?.find(id => id == this.article?.id);
        this.isArticleRead = !!foundArticleId;
    }

    markArticleAsRead(): void {
        if (!this.user?.id) {
            console.error("You are not logged in");
            return;
        }
        if (!this.articleId) {
            return;
        }
        
        this.articleService.readArticle(this.articleId, this.user?.id).subscribe(
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

}

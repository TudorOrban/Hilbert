import { Component } from "@angular/core";
import { CreateArticleDto, DifficultyLevel } from "../../models/Article";
import { FormsModule, NgForm } from "@angular/forms";
import { Language } from "../../../../shared/language/models/Language";
import { CommonModule } from "@angular/common";
import { LanguageSelectorComponent } from "../../../../shared/common/components/language-selector/language-selector.component";
import { ArticleService } from "../../services/article.service";
import { AuthService } from "../../../../core/user/services/auth.service";

@Component({
    selector: "app-add-article",
    imports: [CommonModule, FormsModule, LanguageSelectorComponent],
    templateUrl: "./add-article.component.html",
    styleUrl: "./add-article.component.css",
})
export class AddArticleComponent {
    article: CreateArticleDto = {
        userId: -1,
        title: "",
        content: "",
        language: Language.NONE,
        level: DifficultyLevel.NONE,
    };
    submitted = false;

    constructor(
        private authService: AuthService,
        private articleService: ArticleService,
    ) {
      this.authService.getCurrentUser().subscribe(
          (user) => {
              if (user?.id) {
                  this.article.userId = user?.id;
              } else {
                  console.error("You are not logged in");
              }
          }
      );
    }

    onSubmit(form: NgForm) {
        this.submitted = true;

        if (this.article.userId == -1) {
            console.error("You are not logged in");
            return;
        }

        if (!form.valid) {
            console.error("Invalid form!");
            return;
        }

        this.articleService.createArticle(this.article).subscribe(
            (data) => {
                console.log("Successful creation: ", data);
            },
            (error) => {
                console.error("An error occured while trying to create the Article: ", error?.messsage);
            }
        );
    }
}

import { Component } from "@angular/core";
import { ArticleStatus, CreateArticleDto, DifficultyLevel } from "../../models/Article";
import { FormsModule, NgForm } from "@angular/forms";
import { Language } from "../../../../shared/language/models/Language";
import { CommonModule } from "@angular/common";
import { LanguageSelectorComponent } from "../../../../shared/language/components/language-selector/language-selector.component";
import { ArticleService } from "../../services/article.service";
import { AuthService } from "../../../../core/user/services/auth.service";
import { Router } from "@angular/router";
import { ProgressDialogComponent } from "../../../../shared/common/components/progress-dialog/progress-dialog.component";

@Component({
    selector: "app-add-article",
    imports: [CommonModule, FormsModule, LanguageSelectorComponent, ProgressDialogComponent],
    templateUrl: "./add-article.component.html",
    styleUrl: "./add-article.component.css",
})
export class AddArticleComponent {
    article: CreateArticleDto = {
        userId: -1,
        title: "",
        content: "",
        language: Language.FRENCH,
        level: DifficultyLevel.NONE,
        status: ArticleStatus.DRAFT
    };
    submitted = false;

    isProgressDialogOpen = false;
    progressPercentage = 0;
    estimatedTime = 0;

    constructor(
        private authService: AuthService,
        private articleService: ArticleService,
        private router: Router
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
        
        this.showProgressBar();

        this.articleService.createArticle(this.article).subscribe(
            (data) => {
                console.log("Successful creation: ", data);
                this.isProgressDialogOpen = false;
                this.router.navigate([`/reading/${data.id}`]);
            },
            (error) => {
                console.error("An error occured while trying to create the Article: ", error?.messsage);
            }
        );
    }

    showProgressBar(): void {
        this.isProgressDialogOpen = true;
        this.progressPercentage = 0;

        this.estimatedTime = this.calculateEstimatedTime(this.article.content);
        const updateInterval = 1000;
        const increment = 100 / this.estimatedTime;

        const interval = setInterval(() => {
            this.progressPercentage = Math.floor(this.progressPercentage + increment);
            if (this.progressPercentage >= 100) {
                this.progressPercentage = 100;
                clearInterval(interval);
            }
        }, updateInterval);
    }

    calculateEstimatedTime(content: string): number {
        if (!content) {
            return 3;
        }
      
        // Split content into words
        const words = content.trim().split(/\s+/);
      
        // Calculate estimated time: 1/5 second per word, as indicated by performance tests
        const performanceSlope = 1 / 6;
        const wordCount = words.length;
        const estimatedTime = performanceSlope * wordCount;
        
        return Math.max(estimatedTime, 3);
    }
}

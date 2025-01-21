import { Component } from "@angular/core";
import { CreateArticleDto, DifficultyLevel } from "../../models/Article";
import { AbstractControl, FormsModule, NgForm } from "@angular/forms";
import { Language } from "../../../../shared/language/models/Language";
import { CommonModule } from "@angular/common";

@Component({
    selector: "app-add-article",
    imports: [CommonModule, FormsModule],
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

    onSubmit(form: NgForm) {
        console.log("SUbmitting");
        this.submitted = true;

        if (form.valid) {
            console.log("Form submitted!: ", this.article);
        }
    }
}

import { Component, OnInit } from "@angular/core";
import { GrammarLessonService } from "../../services/grammar-lesson.service";
import { GrammarLessonDto } from "../../models/GrammarLesson";
import { GrammarHeaderComponent } from "./grammar-header/grammar-header.component";
import { GrammarListComponent } from "./grammar-list/grammar-list.component";

@Component({
    selector: "app-grammar",
    imports: [GrammarHeaderComponent, GrammarListComponent],
    templateUrl: "./grammar.component.html",
    styleUrl: "./grammar.component.css",
})
export class GrammarComponent implements OnInit {
    lessons?: GrammarLessonDto[];

    constructor(
        private readonly lessonService: GrammarLessonService
    ) {}

    ngOnInit(): void {
        this.lessonService.searchLessons(false).subscribe({
            next: (data) => {
                if (!data) return;

                console.log("Data: ", data);
                this.lessons = data;
            },
            error: (e) => {
                console.error("Error searching lessons: ", e);
            }
        })
    }
}

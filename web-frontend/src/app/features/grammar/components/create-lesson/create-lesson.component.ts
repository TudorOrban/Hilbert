import { Component } from '@angular/core';
import { LessonExercise, LessonExerciseType } from '../../models/GrammarLesson';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CreateExerciseComponent } from "./create-exercise/create-exercise.component";

@Component({
    selector: 'app-create-lesson',
    imports: [CommonModule, FontAwesomeModule, CreateExerciseComponent],
    templateUrl: './create-lesson.component.html',
    styleUrl: './create-lesson.component.css'
})
export class CreateLessonComponent {
    lessonExercises: LessonExercise[] = [];

    addExercise(): void {
        const newExercise: LessonExercise = {
            exerciseType: LessonExerciseType.NEW,
        };

        this.lessonExercises.push(newExercise);
    }

    changeExerciseType(index: number, newType: LessonExerciseType): void {
        if (index >= 0 && index < this.lessonExercises.length) {
            this.lessonExercises[index].exerciseType = newType;
        } else {
            console.warn(`Attempted to change type for invalid index: ${index}`);
        }
    }

    faPlus = faPlus;
    LessonExerciseType = LessonExerciseType;
}

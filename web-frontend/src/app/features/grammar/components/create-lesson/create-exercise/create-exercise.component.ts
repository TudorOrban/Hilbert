import { Component, Input } from '@angular/core';
import { LessonExercise, LessonExerciseType } from '../../../models/GrammarLesson';
import { CommonModule } from '@angular/common';
import { ExerciseData, ExerciseType } from '../../../models/Exercise';
import { EnumSelectorComponent } from "../../../../../shared/common/components/enum-selector/enum-selector.component";

@Component({
    selector: 'app-create-exercise',
    imports: [CommonModule, EnumSelectorComponent],
    templateUrl: './create-exercise.component.html',
    styleUrl: './create-exercise.component.css'
})
export class CreateExerciseComponent {
    @Input() newExerciseData?: ExerciseData;


    handleExerciseTypeChange(newType?: ExerciseType): void {
        if (!newType || !this.newExerciseData) return;
        this.newExerciseData.exerciseType = newType;
    }

    ExerciseType = ExerciseType;
    LessonExerciseType = LessonExerciseType;
}

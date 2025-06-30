import { Component, Input } from '@angular/core';
import { LessonExerciseType } from '../../../models/GrammarLesson';
import { CommonModule } from '@angular/common';
import { ExerciseAnswerType, ExerciseData, ExerciseType } from '../../../models/Exercise';
import { EnumSelectorComponent } from "../../../../../shared/common/components/enum-selector/enum-selector.component";
import { LanguageSelectorComponent } from "../../../../../shared/language/components/language-selector/language-selector.component";
import { Language } from '../../../../../shared/language/models/Language';
import { FormsModule } from '@angular/forms';

@Component({
    selector: 'app-create-exercise',
    imports: [CommonModule, FormsModule, EnumSelectorComponent, LanguageSelectorComponent],
    templateUrl: './create-exercise.component.html',
    styleUrl: './create-exercise.component.css'
})
export class CreateExerciseComponent {
    @Input() newExerciseData!: ExerciseData;

    srcSentenceString: string = "";
    destSentenceString: string = "";
    isSrcSentenceEditable: boolean = true;
    isDestSentenceEditable: boolean = true;

    handleSrcLanguageChange(newLanguage: Language): void {
        this.newExerciseData.srcLanguage = newLanguage;
    }

    handleDestLanguageChange(newLanguage: Language): void {
        this.newExerciseData.destLanguage = newLanguage;
    }

    handleExerciseTypeChange(newType?: ExerciseType): void {
        if (!newType) return;
        this.newExerciseData.exerciseType = newType;
    }

    handleAnswerTypeChange(newType?: ExerciseAnswerType): void {
        if (!newType) return;
        this.newExerciseData.answerType = newType;
    }

    toggleSrcSentenceEdit(): void {
        this.isSrcSentenceEditable = !this.isSrcSentenceEditable;
        if (!this.isSrcSentenceEditable) {
            console.log("S: ", this.srcSentenceString);
            const segmenter = new Intl.Segmenter([], { granularity: 'word' });
            const segmentedText = segmenter.segment(this.srcSentenceString);
            this.newExerciseData.srcSentence = [...segmentedText].filter(s => s.isWordLike).map(s => s.segment);
        }
    }

    toggleDestSentenceEdit(): void {
        this.isDestSentenceEditable = !this.isDestSentenceEditable;
    }

    ExerciseType = ExerciseType;
    LessonExerciseType = LessonExerciseType;
    ExerciseAnswerType = ExerciseAnswerType;
}

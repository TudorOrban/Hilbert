import { Component, Input } from '@angular/core';
import { GrammarLessonDto } from '../../../models/GrammarLesson';

@Component({
  selector: 'app-grammar-list',
  imports: [],
  templateUrl: './grammar-list.component.html',
  styleUrl: './grammar-list.component.css'
})
export class GrammarListComponent {
    @Input() lessons?: GrammarLessonDto[];
}

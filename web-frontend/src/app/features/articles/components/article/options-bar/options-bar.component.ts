import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faAngleRight, faCheck, faEllipsisVertical } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-options-bar',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './options-bar.component.html',
  styleUrl: './options-bar.component.css'
})
export class OptionsBarComponent {
    @Input() markArticleAsRead!: () => void;
    @Input() isRead?: boolean = false;

    faEllipsisVertical = faEllipsisVertical;
    faCheck = faCheck;
    faAngleRight = faAngleRight;
}

import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-progress-dialog',
  imports: [],
  templateUrl: './progress-dialog.component.html',
  styleUrl: './progress-dialog.component.css'
})
export class ProgressDialogComponent {
    @Input() isOpen = false;

    @Input() title = 'Processing...';
    @Input() message = 'Please wait while we process your request.';
    @Input() progressPercentage = 0;
}

import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'app-progress-dialog',
  imports: [],
  templateUrl: './progress-dialog.component.html',
  styleUrl: './progress-dialog.component.css'
})
export class ProgressDialogComponent implements OnChanges {
    @Input() isOpen = false;

    @Input() title = "Processing...";
    @Input() message = "Please wait while we process your request.";
    @Input() progressPercentage = 0;
    @Input() totalTime = 0; 

    eta = ""; 

    ngOnChanges(changes: SimpleChanges) {
        if (changes["progressPercentage"] || changes["totalTime"]) {
            this.calculateETA();
        }
    }

    calculateETA() {
        if (this.progressPercentage >= 100) {
            this.eta = "Almost done...";
            return;
        }

        console.log("Percentage and time: ", this.progressPercentage, this.totalTime);

        const remainingPercentage = 100 - this.progressPercentage;
        const remainingTime = (remainingPercentage / 100) * this.totalTime;

        if (remainingPercentage <= 0) {
            this.eta = "Almost done...";
        } else {
            this.eta = this.formatTime(remainingTime);
        }
    }


    formatTime(seconds: number): string {
        if (seconds < 60) {
            return `${Math.ceil(seconds)} s`; 
        } else {
            const minutes = Math.floor(seconds / 60);
            const remainingSeconds = Math.ceil(seconds % 60);
            return `${minutes} m ${remainingSeconds} s`; 
        }
    }
}

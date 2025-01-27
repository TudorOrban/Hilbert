import { CommonModule } from "@angular/common";
import { Component, Input, OnInit } from "@angular/core";

@Component({
    selector: "app-popover",
    imports: [CommonModule],
    templateUrl: "./popover.component.html",
    styleUrl: "./popover.component.css",
})
export class PopoverComponent implements OnInit {
    @Input() text: string = "";
    @Input() visible: boolean = false;
    @Input() position: { top: number; left: number } = { top: 0, left: 0 };
    
    ngOnInit(): void {
        console.log("Working: ", this.text);
    }
}

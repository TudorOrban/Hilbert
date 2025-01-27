import { CommonModule } from "@angular/common";
import { Component, Input } from "@angular/core";

@Component({
    selector: "app-popover",
    imports: [CommonModule],
    templateUrl: "./popover.component.html",
    styleUrl: "./popover.component.css",
})
export class PopoverComponent {
    @Input() text: string = "";
    @Input() visible: boolean = false;
    @Input() position: { top: number; left: number } = { top: 0, left: 0 };
}

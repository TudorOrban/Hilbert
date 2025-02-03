import { CommonModule } from "@angular/common";
import { Component, EventEmitter, Input, Output } from "@angular/core";
import { FormsModule } from "@angular/forms";

@Component({
    selector: "app-enum-selector",
    imports: [CommonModule, FormsModule],
    templateUrl: "./enum-selector.component.html",
    styleUrl: "./enum-selector.component.css",
})
export class EnumSelectorComponent<T> {
    @Input() enumType!: { [key: string]: T };
    @Input() selectedValue!: T;
    @Output() selectedValueChange = new EventEmitter<T>();

    get enumValues(): T[] {
        return Object.values(this.enumType);
    }

    onSelectChange(event: Event): void {
        const selectElement = event.target as HTMLSelectElement;
        const value = selectElement.value as unknown as T;
        if (this.selectedValue !== value) {
            this.selectedValue = value;
            this.selectedValueChange.emit(value);
        }
    }
}

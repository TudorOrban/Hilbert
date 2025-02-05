import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UIItem } from '../../types/common';

@Component({
  selector: 'app-selector',
  imports: [CommonModule, FormsModule],
  templateUrl: './selector.component.html',
  styleUrl: './selector.component.css'
})
export class SelectorComponent {
    @Input() options?: UIItem[];
    @Input() selectedValue?: string;

    @Output() selectedValueChange = new EventEmitter<string>();

    onSelectChange(value: string) {
        this.selectedValueChange.emit(value);
    }
}

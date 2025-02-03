import { Component, EventEmitter, Input, Output } from '@angular/core';
import { UIItem } from '../../types/UIItem';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navigation-menu',
  imports: [CommonModule],
  templateUrl: './navigation-menu.component.html',
  styleUrl: './navigation-menu.component.css'
})
export class NavigationMenuComponent {
    @Input() navItems?: UIItem[];
    @Input() selectedItemValue?: string;
    @Output() selectedItemChange = new EventEmitter<string>();

    selectItem(itemValue: string) {
        this.selectedItemValue = itemValue;
        this.selectedItemChange.emit(itemValue);
    }
}

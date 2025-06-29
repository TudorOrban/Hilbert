import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faArrowDownShortWide, faArrowUpWideShort, faPlus } from '@fortawesome/free-solid-svg-icons';
import { SearchInputComponent } from "../../../../../shared/common/components/search-input/search-input.component";

@Component({
    selector: 'app-grammar-header',
    imports: [FontAwesomeModule, SearchInputComponent],
    templateUrl: './grammar-header.component.html',
    styleUrl: './grammar-header.component.css'
})
export class GrammarHeaderComponent {
    faPlus = faPlus;
    faArrowUpWideShort = faArrowUpWideShort;
    faArrowDownShortWide = faArrowDownShortWide;

    constructor(
        private router: Router
    ) {}

    handleAddLesson() {
        this.router.navigate(["/grammar/add-lesson"]);
    }
}

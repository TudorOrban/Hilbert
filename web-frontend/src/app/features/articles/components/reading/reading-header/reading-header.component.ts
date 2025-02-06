import { Component } from "@angular/core";
import { SearchInputComponent } from "../../../../../shared/common/components/search-input/search-input.component";
import {
    faArrowUpWideShort,
    faArrowDownShortWide,
    faPlus,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeModule } from "@fortawesome/angular-fontawesome";
import { Router } from "@angular/router";

@Component({
    selector: "app-reading-header",
    imports: [FontAwesomeModule, SearchInputComponent],
    templateUrl: "./reading-header.component.html",
    styleUrl: "./reading-header.component.css",
})
export class ReadingHeaderComponent {
    faPlus = faPlus;
    faArrowUpWideShort = faArrowUpWideShort;
    faArrowDownShortWide = faArrowDownShortWide;

    constructor(
        private router: Router
    ) {}
    
    handleAddArticle() {
        this.router.navigate(["/reading/add-article"]);
    }
}

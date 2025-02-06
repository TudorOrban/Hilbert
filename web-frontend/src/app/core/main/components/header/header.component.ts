import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFire, faUser } from '@fortawesome/free-solid-svg-icons';
import { Language } from '../../../../shared/language/models/Language';
import { LanguageOptionsService } from '../../../../shared/language/services/language-options.service';
import { UserbarComponent } from "../userbar/userbar.component";

@Component({
  selector: 'app-header',
  imports: [CommonModule, FontAwesomeModule, UserbarComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
    currentLanguage: Language = Language.ENGLISH;
    isUserbarOpen: boolean = false;

    constructor(
        readonly languageService: LanguageOptionsService,
    ) {}

    toggleIsUserbarOpen(): void {
        this.isUserbarOpen = !this.isUserbarOpen;
    }

    faUser = faUser;
    faFire = faFire;
}

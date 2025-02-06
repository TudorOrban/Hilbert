import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFire, faUser } from '@fortawesome/free-solid-svg-icons';
import { Language } from '../../../../shared/language/models/Language';
import { LanguageOptionsService } from '../../../../shared/language/services/language-options.service';

@Component({
  selector: 'app-header',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
    currentLanguage: Language = Language.ENGLISH;

    constructor(
        readonly languageService: LanguageOptionsService,
    ) {}


    faUser = faUser;
    faFire = faFire;
}

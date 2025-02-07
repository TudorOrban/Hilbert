import { Component } from '@angular/core';
import { LanguageOptionsService } from '../../../../shared/language/services/language-options.service';
import { AuthService } from '../../../user/services/auth.service';
import { CurrentLanguageService } from '../../../user/services/current-language.service';
import { Language } from '../../../../shared/language/models/Language';
import { UserDataDto } from '../../../user/models/User';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-current-language-selector',
  imports: [CommonModule],
  templateUrl: './current-language-selector.component.html',
  styleUrl: './current-language-selector.component.css'
})
export class CurrentLanguageSelectorComponent {
    user?: UserDataDto;
    languages?: Language[];
    isLanguageBarOpen: boolean = false;

    constructor(
        private readonly authService: AuthService,
        private readonly currentLanguageService: CurrentLanguageService,
        readonly languageService: LanguageOptionsService,
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (data) => {
                if (!data) {
                    return;
                }
                this.user = data;
            }
        );
    }

    getCurrentLanguage(): Language {
        return this.currentLanguageService.getLanguage();
    }

    toggleLanguageBar(): void {
        this.isLanguageBarOpen = !this.isLanguageBarOpen;
    }

    Language = Language;
}

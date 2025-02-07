import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faFire, faUser } from '@fortawesome/free-solid-svg-icons';
import { Language } from '../../../../shared/language/models/Language';
import { LanguageOptionsService } from '../../../../shared/language/services/language-options.service';
import { UserbarComponent } from "../userbar/userbar.component";
import { AuthService } from '../../../user/services/auth.service';
import { CurrentLanguageSelectorComponent } from "../current-language-selector/current-language-selector.component";

@Component({
  selector: 'app-header',
  imports: [CommonModule, FontAwesomeModule, UserbarComponent, CurrentLanguageSelectorComponent],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {
    isLoggedIn: boolean = false;
    isUserbarOpen: boolean = false;

    constructor(
        private readonly authService: AuthService,
        readonly languageService: LanguageOptionsService,
    ) {}
    
    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (data) => {
                if (!data?.id) {
                    this.isLoggedIn = false;
                    return;
                }
                this.isLoggedIn = true;
            }
        );
    }

    toggleIsUserbarOpen(): void {
        this.isUserbarOpen = !this.isUserbarOpen;
    }

    faUser = faUser;
    faFire = faFire;
}

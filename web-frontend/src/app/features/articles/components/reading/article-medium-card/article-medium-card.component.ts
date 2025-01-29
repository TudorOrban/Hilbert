import { Component, Input } from '@angular/core';
import { ArticleSearchDto } from '../../../models/Article';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { faStar, faEye } from '@fortawesome/free-solid-svg-icons';
import { FormsModule } from '@angular/forms';
import { LanguageOptionsService } from '../../../../../shared/language/services/language-options.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-article-medium-card',
  imports: [CommonModule, FontAwesomeModule, FormsModule],
  templateUrl: './article-medium-card.component.html',
  styleUrl: './article-medium-card.component.css'
})
export class ArticleMediumCardComponent {
    @Input() article?: ArticleSearchDto;

    faStar = faStar;
    faEye = faEye;

    languageService: LanguageOptionsService;

    constructor(
        languageService: LanguageOptionsService,
        private router: Router
    ) {
        this.languageService = languageService;
    }

    navigateToArticle(): void {
        this.router.navigate([`/reading/${this.article?.id}`]);
    }
}

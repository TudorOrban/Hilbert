import { Component, Input } from '@angular/core';
import { LanguageOptionsService } from '../../../../shared/language/services/language-options.service';
import { ArticleFullDto } from '../../models/Article';
import { faEye, faStar } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-article-header',
  imports: [FontAwesomeModule],
  templateUrl: './article-header.component.html',
  styleUrl: './article-header.component.css'
})
export class ArticleHeaderComponent {
    @Input() article?: ArticleFullDto;
    languageService: LanguageOptionsService;

    constructor(
        languageService: LanguageOptionsService,
    ) {
        this.languageService = languageService;
    }


    faEye = faEye;
    faStar = faStar;
}

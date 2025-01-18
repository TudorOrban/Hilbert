import { Component, Input } from '@angular/core';
import { ArticleSearchDto } from '../../models/Article';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { faStar } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-article-medium-card',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './article-medium-card.component.html',
  styleUrl: './article-medium-card.component.css'
})
export class ArticleMediumCardComponent {
    @Input() article?: ArticleSearchDto;

    faStar = faStar;
}

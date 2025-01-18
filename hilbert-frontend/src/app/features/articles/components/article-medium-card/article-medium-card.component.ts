import { Component, Input } from '@angular/core';
import { ArticleSearchDto } from '../../models/Article';

@Component({
  selector: 'app-article-medium-card',
  imports: [],
  templateUrl: './article-medium-card.component.html',
  styleUrl: './article-medium-card.component.css'
})
export class ArticleMediumCardComponent {
    @Input() article?: ArticleSearchDto;
}

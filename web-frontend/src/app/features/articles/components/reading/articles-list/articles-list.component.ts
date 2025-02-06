import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { ArticleMediumCardComponent } from "../article-medium-card/article-medium-card.component";
import { PaginatedResults } from '../../../../../shared/search/models/Search';
import { ArticleSearchDto } from '../../../models/Article';

@Component({
  selector: 'app-articles-list',
  imports: [CommonModule, ArticleMediumCardComponent],
  templateUrl: './articles-list.component.html',
  styleUrl: './articles-list.component.css'
})
export class ArticlesListComponent {
    @Input() articles?: PaginatedResults<ArticleSearchDto>;
}

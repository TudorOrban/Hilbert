import { Component, OnInit } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { ArticleService } from '../../services/article.service';
import { PaginatedResults } from '../../../../shared/search/models/Search';
import { ArticleSearchDto } from '../../models/Article';
import { AdvancedSearchPanelComponent } from './advanced-search-panel/advanced-search-panel.component';
import { ReadingHeaderComponent } from "./reading-header/reading-header.component";
import { ArticlesListComponent } from "./articles-list/articles-list.component";

@Component({
  selector: 'app-reading',
  imports: [CommonModule, FontAwesomeModule, AdvancedSearchPanelComponent, ReadingHeaderComponent, ArticlesListComponent],
  templateUrl: './reading.component.html',
  styleUrl: './reading.component.css'
})
export class ReadingComponent implements OnInit {
    articles?: PaginatedResults<ArticleSearchDto>;

    constructor(
        private articleService: ArticleService,
    ) {}

    ngOnInit() {
        this.articleService.searchArticles({}).subscribe(
            (data) => {
                console.log("Articles: ", data);
                this.articles = data;
            },
            (error) => {
                console.error("Error searching articles: ", error);
            }
        )
    }
}

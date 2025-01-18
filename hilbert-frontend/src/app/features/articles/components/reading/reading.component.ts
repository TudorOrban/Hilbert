import { Component, OnInit } from '@angular/core';
import { SearchInputComponent } from '../../../../shared/common/components/search-input/search-input.component';
import { faArrowUpWideShort, faArrowDownShortWide } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { ArticleMediumCardComponent } from '../article-medium-card/article-medium-card.component';
import { ArticleService } from '../../services/article.service';
import { AdvancedSearchPanelComponent } from '../advanced-search-panel/advanced-search-panel.component';
import { PaginatedResults } from '../../../../shared/search/models/Search';
import { ArticleSearchDto } from '../../models/Article';

@Component({
  selector: 'app-reading',
  imports: [CommonModule, FontAwesomeModule, SearchInputComponent, ArticleMediumCardComponent, AdvancedSearchPanelComponent],
  templateUrl: './reading.component.html',
  styleUrl: './reading.component.css'
})
export class ReadingComponent implements OnInit {
    articles?: PaginatedResults<ArticleSearchDto>;

    constructor(
        private articleService: ArticleService
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











    faArrowUpWideShort = faArrowUpWideShort;
    faArrowDownShortWide = faArrowDownShortWide;
}

import { Component, OnInit } from '@angular/core';
import { ArticleFullDto, ReadArticleSummaryDto } from '../../models/Article';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCaretDown, faCaretUp, faStar } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-read-article-summary',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './read-article-summary.component.html',
  styleUrl: './read-article-summary.component.css'
})
export class ReadArticleSummaryComponent implements OnInit {
    summary?: ReadArticleSummaryDto;
    article?: ArticleFullDto;
    rating: number = 0;
    maxRating: number = 5;
    isNewWordsListExpanded: boolean = false;
    isReadNextListExpanded: boolean = false;

    constructor(private readonly route: ActivatedRoute) {}

    ngOnInit(): void {
        this.summary = history.state.summary;
        this.article = history.state.article;

        console.log("Summary: ", this.summary);
    }

    setRating(rating: number): void {
        this.rating = rating;
    }

    submitRating(): void {
        console.log("Rating submitted: ", this.rating);
        // Rating submission
    }

    toggleNewWordsListExpanded(): void {
        this.isNewWordsListExpanded = !this.isNewWordsListExpanded;
    }

    toggleReadNextListExpanded(): void {
        this.isReadNextListExpanded = !this.isReadNextListExpanded;
    }
    
    Object = Object;

    faStar = faStar;
    faCaretUp = faCaretUp;
    faCaretDown = faCaretDown;

    getNewWordsCaretIcon(): any {
        return this.isNewWordsListExpanded ? this.faCaretUp : this.faCaretDown;
    }

    getReadNextCaretIcon(): any {
        return this.isReadNextListExpanded ? this.faCaretUp : this.faCaretDown;
    }
}

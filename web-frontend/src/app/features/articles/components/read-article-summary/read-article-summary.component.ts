import { Component, OnInit } from '@angular/core';
import { ArticleFullDto, ReadArticleSummaryDto } from '../../models/Article';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCaretDown, faCaretUp, faStar, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { ArticleCommentService } from '../../services/article-comment.service';
import { CommentSearchParams, PaginatedResults } from '../../../../shared/search/models/Search';
import { ArticleCommentDto } from '../../models/ArticleComment';

@Component({
  selector: 'app-read-article-summary',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './read-article-summary.component.html',
  styleUrl: './read-article-summary.component.css'
})
export class ReadArticleSummaryComponent implements OnInit {
    summary?: ReadArticleSummaryDto;
    article?: ArticleFullDto;
    comments?: PaginatedResults<ArticleCommentDto>;

    pageNumber: number = 0;

    rating: number = 0;
    maxRating: number = 5;
    submittedRating: boolean = false;

    isNewWordsExpanded: boolean = false;
    isReadNextExpanded: boolean = false;
    isCommentsExpanded: boolean = false;

    constructor(
        private readonly route: ActivatedRoute,
        private readonly commentService: ArticleCommentService,
    ) {}

    ngOnInit(): void {
        this.summary = history.state.summary;
        this.article = history.state.article;

        this.loadMoreComments();
    }

    loadMoreComments(): void {
        this.pageNumber = this.pageNumber + 1;
        const searchParams: CommentSearchParams = {
            articleId: this.article?.id,
            sortBy: "createdAt",
            page: this.pageNumber,
            itemsPerPage: 1
        };

        this.commentService.searchComments(searchParams).subscribe(
            (data) => {
                if (this.comments) {
                    this.comments.results.push(...data.results);
                    this.comments.totalCount = data.totalCount;
                } else {
                    this.comments = data;
                }
            },
            (error) => {
                console.error("Error searching comments: ", error);
            }
        );
    }
    

    setRating(rating: number): void {
        this.rating = rating;
    }

    submitRating(): void {
        console.log("Rating submitted: ", this.rating);
        this.submittedRating = true;
        // Rating submission
    }

    toggleNewWordsExpanded(): void {
        this.isNewWordsExpanded = !this.isNewWordsExpanded;
    }

    toggleReadNextExpanded(): void {
        this.isReadNextExpanded = !this.isReadNextExpanded;
    }

    toggleCommentsExpanded(): void {
        this.isCommentsExpanded = !this.isCommentsExpanded;
    }

    getNewWordsCaretIcon(): IconDefinition {
        return this.isNewWordsExpanded ? this.faCaretUp : this.faCaretDown;
    }

    getReadNextCaretIcon(): IconDefinition {
        return this.isReadNextExpanded ? this.faCaretUp : this.faCaretDown;
    }

    getCommentsCaretIcon(): IconDefinition {
        return this.isCommentsExpanded ? this.faCaretUp : this.faCaretDown;
    }

    Object = Object;

    faStar = faStar;
    faCaretUp = faCaretUp;
    faCaretDown = faCaretDown;
}

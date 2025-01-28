import { Component, OnInit } from '@angular/core';
import { ArticleFullDto, ReadArticleSummaryDto } from '../../models/Article';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faCaretDown, faCaretUp, faPlus, faStar, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { ArticleCommentService } from '../../services/article-comment.service';
import { CommentSearchParams, PaginatedResults } from '../../../../shared/search/models/Search';
import { ArticleCommentDto, CreateCommentDto } from '../../models/ArticleComment';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../../core/user/services/auth.service';

@Component({
  selector: 'app-read-article-summary',
  imports: [CommonModule, FontAwesomeModule, FormsModule],
  templateUrl: './read-article-summary.component.html',
  styleUrl: './read-article-summary.component.css'
})
export class ReadArticleSummaryComponent implements OnInit {
    summary?: ReadArticleSummaryDto;
    article?: ArticleFullDto;
    comments?: PaginatedResults<ArticleCommentDto>;
    userId?: number;

    pageNumber: number = 0;

    rating: number = 0;
    maxRating: number = 5;
    submittedRating: boolean = false;

    isNewWordsExpanded: boolean = false;
    isReadNextExpanded: boolean = false;
    isCommentsExpanded: boolean = false;
    
    isAddCommentOn: boolean = false;
    addCommentContent: string = "";

    constructor(
        private readonly route: ActivatedRoute,
        private readonly commentService: ArticleCommentService,
        private readonly authService: AuthService,
    ) {}

    ngOnInit(): void {
        this.summary = history.state.summary;
        this.article = history.state.article;

        this.loadMoreComments();
        this.getCurrentUserId();
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

    getCurrentUserId(): void {
        this.authService.getCurrentUser().subscribe(
            (user) => {
                this.userId = user?.id;
            }
        );
    }

    toggleAddCommentMode(): void {
        this.isAddCommentOn = !this.isAddCommentOn;
    }
    
    createComment(): void {
        if (!this.userId) {
            console.error("You are not logged in.");
            return;
        }
        if (!this.article?.id) {
            return;
        }
        const commentDto: CreateCommentDto = { userId: this.userId, articleId: this.article.id, content: this.addCommentContent };
        
        this.commentService.createComment(commentDto).subscribe(
            (comment) => {
                console.log("Comment: ", comment);
            },
            (error) => {
                console.error("Error creating comment: ", error);
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
    faPlus = faPlus;
}

import { Component, OnInit } from '@angular/core';
import { ArticleFullDto, ReadArticleSummaryDto } from '../../models/Article';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-read-article-summary',
  imports: [CommonModule],
  templateUrl: './read-article-summary.component.html',
  styleUrl: './read-article-summary.component.css'
})
export class ReadArticleSummaryComponent implements OnInit {
    summary?: ReadArticleSummaryDto;
    article?: ArticleFullDto;

    constructor(private readonly route: ActivatedRoute) {}

    ngOnInit(): void {
        this.summary = history.state.summary;
        this.article = history.state.article;

        console.log("Summary: ", this.summary);
    }

    Object = Object;
}

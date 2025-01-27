import { Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { ArticleFullDto } from '../../models/Article';
import { CommonModule } from '@angular/common';
import { PopoverComponent } from "../../../../shared/common/components/popover/popover.component";

@Component({
  selector: 'app-overlayed-text',
  imports: [CommonModule, PopoverComponent],
  templateUrl: './overlayed-text.component.html',
  styleUrl: './overlayed-text.component.css'
})
export class OverlayedTextComponent implements OnChanges {
    @Input() article?: ArticleFullDto;

    words?: string[];
    selectedWord: string = "";
    translation: string = "";
    popoverVisible: boolean = false;
    popoverPosition: { top: number, left: number } = { top: 0, left: 0 };

    @ViewChild('contentContainer') contentContainer!: ElementRef;

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["article"] && this.article?.content) {
            this.words = this.article?.content.split(/\s+/);
        }
    }

    showPopover(event: MouseEvent, word: string): void {
      const target = event.target as HTMLElement;
      if (!target) {
        return;
      }

      this.selectedWord = word;
      this.translation = this.translate(word);
      this.popoverVisible = true;
      
      const leftOffset = 30;
      const topOffset = 60;
      const rect = target.getBoundingClientRect();
      const containerRect = this.contentContainer.nativeElement.getBoundingClientRect();
      const containerScrollTop = this.contentContainer.nativeElement.scrollTop;

      this.popoverPosition = {
          top: rect.top - containerRect.top + containerScrollTop + rect.height / 2 - topOffset,
          left: rect.left - containerRect.left + rect.width / 2 - leftOffset
      };
  }

    hidePopover(): void {
        this.popoverVisible = false;
    }

    translate(word: string): string {
        return this.article?.translatedContent.translationMap[word]?.[0] ?? "";
    }
}

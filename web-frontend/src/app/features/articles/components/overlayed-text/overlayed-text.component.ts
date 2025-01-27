import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
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

    ngOnChanges(changes: SimpleChanges): void {
        if (changes["article"] && this.article?.content) {
            this.words = this.article?.content.split(/\s+/);
        }
    }

    showPopover(event: MouseEvent, word: string): void {
        this.selectedWord = word;
        this.translation = this.translate(word);
        console.log("Translation: ", this.translation);
        this.popoverVisible = true;
        const sidebarOffset = 335;
        const headerOffset = 240;
        this.popoverPosition = { top: event.clientY - headerOffset, left: event.clientX - sidebarOffset };
    }

    hidePopover(): void {
        this.popoverVisible = false;
    }

    translate(word: string): string {
        return "Translation of " + word;
    }
}

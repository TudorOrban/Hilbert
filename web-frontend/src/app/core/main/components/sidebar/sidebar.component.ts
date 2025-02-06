import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { UIItem } from '../../../../shared/common/types/common';
import {
    faHome,
    faBook,
    faFont,
    faSpellCheck,
    faComment,
} from '@fortawesome/free-solid-svg-icons';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, FontAwesomeModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
    currentRoute?: string;
    sidebarItems: UIItem[] = [
        { label: "Home", value: "home", icon: faHome, link: "/home" },
        { label: "Reading", value: "reading", icon: faBook, link: "/reading" },
        { label: "Vocabulary", value: "vocabulary", icon: faFont, link: "/vocabulary" },
        { label: "Grammar", value: "grammar", icon: faSpellCheck, link: "/grammar" },
        { label: "Chat", value: "chat", icon: faComment, link: "/chat" },
    ];

    constructor(
        private readonly router: Router,
    ) {}

    navigateTo(item?: UIItem) {
        if (!item) {
            return;
        }

        this.currentRoute = item.value;
        this.router.navigate([item?.link ?? 'not-found']);
    }

    faHome = faHome;
}

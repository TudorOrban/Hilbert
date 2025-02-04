import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { UIItem } from '../../../../shared/common/types/common';
import {
    faHome,
    faBook,
    faFont,
    faSpellCheck,
    faComment,
    faUser,
} from '@fortawesome/free-solid-svg-icons';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../user/services/auth.service';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, FontAwesomeModule, RouterModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent implements OnInit {
    username?: string;

    constructor(
        private readonly authService: AuthService,
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (data) => {
                if (!data?.username) {
                    return;
                }

                this.username = data?.username;
                this.sidebarItems.push({ 
                    label: "Profile", value: "profile", icon: faUser, 
                    link: `${this.username ?? "not-found"}/profile` 
                });
            }
        );
    }

    faHome = faHome;
    sidebarItems: UIItem[] = [
        { label: "Home", value: "home", icon: faHome, link: "/home" },
        { label: "Reading", value: "reading", icon: faBook, link: "/reading" },
        { label: "Vocabulary", value: "vocabulary", icon: faFont, link: "/vocabulary" },
        { label: "Grammar", value: "grammar", icon: faSpellCheck, link: "/grammar" },
        { label: "Chat", value: "chat", icon: faComment, link: "/chat" },
    ];

}

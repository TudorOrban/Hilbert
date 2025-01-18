import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { UIItem } from '../../../../shared/common/types/UIItem';
import {
    faHome,
    faBook,
    faFont,
    faSpellCheck,
    faComment,
    faUser,
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-sidebar',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {

    faHome = faHome;

    sidebarItems: UIItem[] = [
        { label: "Home", value: "home", icon: faHome },
        { label: "Reading", value: "reading", icon: faBook },
        { label: "Vocabulary", value: "vocabulary", icon: faFont },
        { label: "Grammar", value: "grammar", icon: faSpellCheck },
        { label: "Chat", value: "chat", icon: faComment },
        { label: "Profile", value: "profile", icon: faUser },
    ];

}

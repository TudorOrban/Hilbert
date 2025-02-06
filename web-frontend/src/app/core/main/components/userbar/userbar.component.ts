import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faGear, faRightFromBracket, faUser } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from '../../../user/services/auth.service';
import { UserDataDto } from '../../../user/models/User';
import { UIItem } from '../../../../shared/common/types/common';

@Component({
  selector: 'app-userbar',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './userbar.component.html',
  styleUrl: './userbar.component.css'
})
export class UserbarComponent implements OnInit {
    user?: UserDataDto;
    navigationItems: UIItem[] = [];

    constructor(
        private readonly authService: AuthService,
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (data) => {
                if (!data) {
                    return;
                }

                this.user = data;
                this.navigationItems = [
                    { label: this.user?.username, value: "profile", link: this.user?.username + "/profile", icon: faUser },
                    { label: "Settings", value: "settings", link: "/settings", icon: faGear },
                    { label: "Log Out", value: "logout", link: "", icon: faRightFromBracket }
                ];
            }
        );
    }

    faUser = faUser;
}

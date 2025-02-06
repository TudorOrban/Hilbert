import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faGear, faRightFromBracket, faUser } from '@fortawesome/free-solid-svg-icons';
import { AuthService } from '../../../user/services/auth.service';
import { UserDataDto } from '../../../user/models/User';
import { UIItem } from '../../../../shared/common/types/common';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-userbar',
  imports: [CommonModule, FontAwesomeModule, RouterModule],
  templateUrl: './userbar.component.html',
  styleUrl: './userbar.component.css'
})
export class UserbarComponent implements OnInit {
    user?: UserDataDto;
    navigationItems: UIItem[] = [];
    currentRoute: string = "";

    constructor(
        private readonly authService: AuthService,
        private readonly router: Router
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

    navigateTo(item?: UIItem): void {
        if (!item) {
            return;
        }

        this.currentRoute = item.value;
        this.router.navigate([item?.link ?? 'not-found']);
    }

    faUser = faUser;
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../core/user/services/auth.service';
import { UserService } from '../../../../core/user/services/user.service';
import { UserDataDto } from '../../../../core/user/models/User';
import { UiUtilService } from '../../../../shared/common/services/ui-util.service';
import { CommonModule } from '@angular/common';
import { Language } from '../../../../shared/language/models/Language';
import { LanguageOptionsService } from '../../../../shared/language/services/language-options.service';
import { LabelValueComponent } from "../../../../shared/common/components/label-value/label-value.component";

@Component({
  selector: 'app-profile',
  imports: [CommonModule, LabelValueComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
    username?: string;
    currentUserId?: number;
    userData?: UserDataDto;
    languages: Language[] = [];

    constructor(
        private readonly userService: UserService,
        private readonly authService: AuthService,
        readonly languageService: LanguageOptionsService,
        private readonly uiUtilService: UiUtilService,
        private readonly route: ActivatedRoute,
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.username = params.get("username") ?? undefined;
            if (!this.username) {
                return;
            }

            this.userService.getUserByUsername(this.username, true).subscribe(
                (data) => {
                    this.userData = data;
                    console.log(data?.profileDto?.learningData);
                    this.languages = Object.keys(this.userData.profileDto.learningData?.languageData ?? {}) as Language[];
                },
                (error) => {
                    console.error("Failed to load user: ", error);
                }
            );
        }); 
        this.authService.getCurrentUser().subscribe(
            (data) => {
                this.currentUserId = data?.id;
            }
        );
    }


    formatDate(dateString?: string): string {
        return this.uiUtilService.formatDate(dateString);
    }
}

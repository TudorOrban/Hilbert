import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../../core/user/services/auth.service';
import { UserDataDto } from '../../../../core/user/models/User';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

@Component({
  selector: 'app-settings',
  imports: [CommonModule, FontAwesomeModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.css'
})
export class SettingsComponent implements OnInit {
    user?: UserDataDto;

    constructor(
        private readonly authService: AuthService
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (data) => {
                if (!data) {
                    return;
                }
                this.user = data;
            }
        );
    }
}

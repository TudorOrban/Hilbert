import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../core/user/services/auth.service';
import { UserService } from '../../../../core/user/services/user.service';

@Component({
  selector: 'app-profile',
  imports: [],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
    username?: string;
    currentUserId?: number;

    constructor(
        private readonly userService: UserService,
        private readonly authService: AuthService,
        private readonly route: ActivatedRoute,
    ) {}

    ngOnInit(): void {
        this.route.paramMap.subscribe((params) => {
            this.username = params.get("username") ?? undefined;
            if (!this.username) {
                return;
            }

            this.userService.getUserByUsername(this.username).subscribe(
                (data) => {
                    console.log("Data: ", data);
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
}

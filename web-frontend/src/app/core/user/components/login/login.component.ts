import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
    username: string = "";
    password: string = "";
    invalidCredentials: boolean = false;

    constructor(
        private readonly authService: AuthService,
        private readonly router: Router
    ) {}

    onSubmit(): void {
        this.invalidCredentials = false;

        this.authService.logIn({ username: this.username, password: this.password }).subscribe({
            next: () => {

            },
            error: (error) => {
                this.invalidCredentials = true;
            }
        });
    }

    goToSignUp(): void {
        this.router.navigate(["/sign-up"]);
    }
}

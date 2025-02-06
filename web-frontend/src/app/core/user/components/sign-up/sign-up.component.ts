import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../services/user.service';
import { CreateUserDto } from '../../models/User';

@Component({
  selector: 'app-sign-up',
  imports: [CommonModule, FormsModule],
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
    username: string = "";
    email: string = "";
    password: string = "";
    failedSignUp: boolean = false;

    constructor(
        private readonly userService: UserService,
        private readonly authService: AuthService,
        private readonly router: Router
    ) {}

    onSubmit(): void {
        this.failedSignUp = false;

        const userDto: CreateUserDto = {
            username: this.username,
            email: this.email,
            password: this.password
        };

        this.userService.createUser(userDto).subscribe({
            next: () => {
                this.authService.logIn({ username: this.username, password: this.password });
                this.router.navigate(["/home"]);
            },
            error: (error) => {
                this.failedSignUp = true;
            }
        });
    }

    goToSignUp(): void {
        this.router.navigate(["/sign-up"]);
    }
}

import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
    username = '';
    password = '';

    constructor(
        private readonly authService: AuthService
    ) {}

    onSubmit(): void {
        this.authService.logIn({ username: this.username, password: this.password });
    }

    goToSignUp(): void {

    }
}

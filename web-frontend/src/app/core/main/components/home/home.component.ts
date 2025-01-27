import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../user/services/auth.service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

    constructor(
        private readonly authService: AuthService
    ) {}

    ngOnInit(): void {
        this.authService.getCurrentUser().subscribe(
            (data) => {
                console.log("User logged in: ", data);
            },
            (error) => {
                console.error("User not logged in: ", error);
            }
        );
    }
}

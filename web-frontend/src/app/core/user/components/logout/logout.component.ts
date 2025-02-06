import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-logout',
  imports: [],
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent implements OnInit {

    constructor(
        private readonly authService: AuthService,
    ) {}

    ngOnInit(): void {
        console.log("in logout");
        this.authService.logOut();
    }
}

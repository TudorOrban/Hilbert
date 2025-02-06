import { Component, OnInit } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './core/user/services/auth.service';
import { SidebarComponent } from './core/main/components/sidebar/sidebar.component';
import { HeaderComponent } from "./core/main/components/header/header.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, SidebarComponent, HeaderComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
    title = 'hilbert-frontend';

    constructor(
        private authService: AuthService,
    ) {}

    ngOnInit(): void {
        this.authService.loadUser();
    }
}

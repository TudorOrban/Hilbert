import { Routes } from '@angular/router';
import { LoginComponent } from './core/user/components/login/login.component';
import { AuthGuard } from './core/main/routing/AuthGuard';
import { HomeComponent } from './core/main/components/home/home.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] }
];

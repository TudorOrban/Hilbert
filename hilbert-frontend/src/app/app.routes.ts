import { Routes } from '@angular/router';
import { LoginComponent } from './core/user/components/login/login.component';
import { AuthGuard } from './core/main/routing/AuthGuard';
import { HomeComponent } from './core/main/components/home/home.component';
import { ReadingComponent } from './features/articles/components/reading/reading.component';
import { VocabularyComponent } from './features/vocabulary/components/vocabulary/vocabulary.component';
import { ProfileComponent } from './core/user/components/profile/profile.component';
import { ChatComponent } from './features/chats/components/chat/chat.component';
import { GrammarComponent } from './features/exercises/components/grammar/grammar.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'reading', component: ReadingComponent, canActivate: [AuthGuard] },
    { path: 'vocabulary', component: VocabularyComponent, canActivate: [AuthGuard] },
    { path: 'grammar', component: GrammarComponent, canActivate: [AuthGuard] },
    { path: 'chat', component: ChatComponent, canActivate: [AuthGuard] },
    { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
];

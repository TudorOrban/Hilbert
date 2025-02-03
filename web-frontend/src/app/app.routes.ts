import { Routes } from '@angular/router';
import { LoginComponent } from './core/user/components/login/login.component';
import { AuthGuard } from './core/main/routing/AuthGuard';
import { HomeComponent } from './core/main/components/home/home.component';
import { ReadingComponent } from './features/articles/components/reading/reading.component';
import { VocabularyComponent } from './features/vocabulary/components/vocabulary/vocabulary.component';
import { ProfileComponent } from './core/user/components/profile/profile.component';
import { GrammarComponent } from './features/exercises/components/grammar/grammar.component';
import { AddArticleComponent } from './features/articles/components/add-article/add-article.component';
import { ArticleComponent } from './features/articles/components/article/article.component';
import { ReadArticleSummaryComponent } from './features/articles/components/read-article-summary/read-article-summary.component';
import { ChatsComponent } from './features/chats/components/chats/chats.component';
import { ChatComponent } from './features/chats/components/chat/chat.component';
import { BotChatComponent } from './features/chats/components/botchat/bot-chat.component';
import { NewChatComponent } from './features/chats/components/new-chat/new-chat.component';

export const routes: Routes = [
    { path: 'login', component: LoginComponent },
    { path: '', redirectTo: 'login', pathMatch: 'full' },
    { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'reading', component: ReadingComponent, canActivate: [AuthGuard] },
    { path: 'reading/add-article', component: AddArticleComponent, canActivate: [AuthGuard] },
    { path: 'reading/:articleId', component: ArticleComponent, canActivate: [AuthGuard] },
    { path: 'reading/:articleId/read', component: ReadArticleSummaryComponent, canActivate: [AuthGuard] },
    { path: 'vocabulary', component: VocabularyComponent, canActivate: [AuthGuard] },
    { path: 'grammar', component: GrammarComponent, canActivate: [AuthGuard] },
    { path: 'chat', component: ChatsComponent, canActivate: [AuthGuard] },
    { path: 'chat/new-chat', component: NewChatComponent, canActivate: [AuthGuard] },
    { path: 'chat/:chatId', component: ChatComponent, canActivate: [AuthGuard] },
    { path: 'bot-chat/:botChatId', component: BotChatComponent, canActivate: [AuthGuard] },
    { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] },
];

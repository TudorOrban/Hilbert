import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginDto, LoginResponseDto, UserDataDto } from "../models/User";
import { BehaviorSubject, catchError, map, Observable, of } from "rxjs";
import { UserService } from "./user.service";
import { CookieService } from "ngx-cookie-service";
import { Router } from "@angular/router";
import * as jwt_decoder from "jwt-decode";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl: string = "http://localhost:8080/api/v1/auth";
    private currentUserSubject = new BehaviorSubject<UserDataDto | null>(null);

    constructor(
        private http: HttpClient,
        private router: Router,
        private readonly userService: UserService,
        private readonly cookieService: CookieService
    ) {}

    logIn(loginDto: LoginDto) {
        return this.http.post<LoginResponseDto>(`${this.apiUrl}/login`, loginDto).subscribe(
            (responseDto) => {
                this.cookieService.set('jwtToken', responseDto.accessToken);
                this.fetchAndSetCurrentUser(loginDto.username);
                this.router.navigate(['/home']);
            },
            (err) => {
                console.error("Login error:", err);
            },
        );
    }

    loadUser() {
        const username = this.getUsernameFromToken();
        if (!username) {
            console.error("Invalid token");
            this.cookieService.delete('jwtToken');
            this.router.navigate(['/login']);
            return;
        }

        this.fetchAndSetCurrentUser(username);
    }

    public getUsernameFromToken(): string | null {
        const token = this.cookieService.get('jwtToken');
        if (!token) {
            return null;
        }

        const decodedToken = jwt_decoder.jwtDecode<{ sub: string}>(token);
        return decodedToken.sub;
    }

    fetchAndSetCurrentUser(username: string): void {
        this.userService.getUserByUsername(username).subscribe(
            (user) => {
                console.log("User: ", user);
                this.setCurrentUser(user);
            },
            (error) => {
                console.error("Error fetching user: ", error);
                this.setCurrentUser(null);
            }
        )
    }

    setCurrentUser(user: UserDataDto | null): void {
        this.currentUserSubject.next(user);
    }

    getCurrentUser(): Observable<UserDataDto | null> {
        return this.currentUserSubject.asObservable();
    }

}

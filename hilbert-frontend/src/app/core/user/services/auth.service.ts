import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { LoginDto, LoginResponseDto, UserDataDto } from "../models/User";
import { BehaviorSubject, Observable } from "rxjs";
import { UserService } from "./user.service";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl: string = "http://localhost:8080/api/v1/login";
    private currentUserSubject = new BehaviorSubject<UserDataDto | null>(null);

    constructor(
        private http: HttpClient,
        private readonly userService: UserService,
    ) {}

    logIn(loginDto: LoginDto): Observable<LoginResponseDto> {
        return this.http.post<LoginResponseDto>(this.apiUrl, loginDto);
    }

    fetchAndSetCurrentUser(username: string): void {
        this.userService.getUserByUsername(username).subscribe(
            (user) => {
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

import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CreateUserDto, UpdateUserDto, UserDataDto } from "../models/User";
import { BehaviorSubject, Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl: string = "http://localhost:8080/api/v1/users";
    private currentUserSubject = new BehaviorSubject<UserDataDto | null>(null);

    constructor(private http: HttpClient) {}

    fetchAndSetCurrentUser(username: string): void {
        this.getUserByUsername(username).subscribe(
            (user) => {
                this.setCurrentUser(user);
            },
            (error) => {
                console.error('Error fetching user: ', error);
                this.setCurrentUser(null);
            }
        )
    }

    getUserByUsername(username: string): Observable<UserDataDto> {
        return this.http.get<UserDataDto>(`${this.apiUrl}/${username}`);
    }

    createUser(userDto: CreateUserDto): Observable<UserDataDto> {
        return this.http.post<UserDataDto>(this.apiUrl, userDto);
    }

    updateUser(userDto: UpdateUserDto): Observable<UserDataDto> {
        return this.http.put<UserDataDto>(this.apiUrl, userDto);
    }

    deleteUser(id: number) {
        this.http.delete(`${this.apiUrl}/${id}`);
    }


    setCurrentUser(user: UserDataDto | null): void {
        this.currentUserSubject.next(user);
    }

    getCurrentUser(): Observable<UserDataDto | null> {
        return this.currentUserSubject.asObservable();
    }
}

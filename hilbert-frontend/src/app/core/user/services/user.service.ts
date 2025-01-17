import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CreateUserDto, UpdateUserDto, UserDataDto } from "../models/User";
import { Observable } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl: string = "http://localhost:8080/api/v1/users";

    constructor(private http: HttpClient) {}

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

}

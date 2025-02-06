import { Injectable } from "@angular/core";
import { UserSettingsDto } from "../models/UserSettings";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";

@Injectable({
    providedIn: "root"
})
export class UserSettingsService {
    private apiUrl: string = "http://localhost:8080/user-settings";

    constructor(
        private http: HttpClient
    ) {}

    getUserSettings(userId: string): Observable<UserSettingsDto> {
        return this.http.get<UserSettingsDto>(`${this.apiUrl}/${userId}`);
    }
}
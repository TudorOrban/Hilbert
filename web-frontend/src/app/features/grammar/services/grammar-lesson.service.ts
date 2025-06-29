import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment.development";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { CreateLessonDto, GrammarLessonDto } from "../models/GrammarLesson";

@Injectable({
    providedIn: "root"
})
export class GrammarLessonService {
    private apiUrl = `${environment.apiUrl}/grammar-lessons`;

    constructor(private http: HttpClient) {}

    searchLessons(includeExercises?: boolean): Observable<GrammarLessonDto[]> {
        return this.http.get<GrammarLessonDto[]>(
            this.apiUrl, 
            { params: { includeExercises: includeExercises ?? false}}
        );
    }

    createLesson(lessonDto: CreateLessonDto): Observable<GrammarLessonDto> {
        return this.http.post<GrammarLessonDto>(this.apiUrl, lessonDto);
    }

    deleteLesson(id: number) {
        this.http.delete(`${this.apiUrl}/${id}`);
    }
}
import { Injectable } from "@angular/core";
import { environment } from "../../../../environments/environment.development";
import { Observable } from "rxjs";
import { CreateExerciseDto, ExerciseAnswerDto, ExerciseFullDto } from "../models/Exercise";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: "root"
})
export class ExerciseService {
    private apiUrl = `${environment.apiUrl}/exercises`;

    constructor(private http: HttpClient) {}

    getExerciseById(id: number): Observable<ExerciseFullDto> {
        return this.http.get<ExerciseFullDto>(`${this.apiUrl}/${id}`);
    }

    createExercise(exerciseDto: CreateExerciseDto): Observable<ExerciseFullDto> {
        return this.http.post<ExerciseFullDto>(this.apiUrl, exerciseDto);
    }

    answerExercise(exerciseDto: ExerciseAnswerDto): Observable<boolean> {
        return this.http.post<boolean>(`${this.apiUrl}/answer`, exerciseDto);
    }

    deleteExercise(id: number) {
        this.http.delete(`${this.apiUrl}/${id}`);
    }
}
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class UiUtilService {
    

    formatDate(dateString?: string): string {
        if (!dateString) {
            return "";
        }
        
        const date = new Date(dateString);
        const now = new Date();

        const isToday = (
            date.getDate() === now.getDate() &&
            date.getMonth() === now.getMonth() &&
            date.getFullYear() === now.getFullYear()
        );

        const isYesterday = (
            date.getDate() === now.getDate() - 1 &&
            date.getMonth() === now.getMonth() &&
            date.getFullYear() === now.getFullYear()
        );

        if (isToday) {
            return this.formatTime(date);
        } else if (isYesterday) {
            return 'Yesterday';
        } else {
            return this.formatDateNicely(date);
        }
    }

    private formatTime(date: Date): string {
        const hours = date.getHours().toString().padStart(2, '0');
        const minutes = date.getMinutes().toString().padStart(2, '0');
        return `${hours}:${minutes}`;
    }

    private formatDateNicely(date: Date): string {
        const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'long', day: 'numeric' };
        return date.toLocaleDateString(undefined, options);
    }
}
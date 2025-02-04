import { Injectable } from "@angular/core";

@Injectable({
    providedIn: "root"
})
export class NavigationUtilService {
    private data: Record<string, string> = {};

    setData(key: string, value: string) {
        this.data[key] = value;
    }

    getData(key: string): string | undefined {
        return this.data[key];
    }
}
import { Injectable } from "@angular/core";
import { SearchParams } from "../models/Search";

@Injectable({
    providedIn: "root"
})
export class SearchUrlBuilderService {

    buildSearchUrl(baseUrl: string, searchParams: SearchParams): string {
        const url = baseUrl +
            "?searchQuery=" + (searchParams.searchQuery ?? "") +
            "&sortBy=" + (searchParams.sortBy ?? "createdAt") +
            "&isAscending=" + (searchParams.isAscending ?? true) +
            "&page=" + (searchParams.page ?? 1) +
            "&itemsPerPage=" + (searchParams.itemsPerPage ?? 20);
        
        return url;
    }
}
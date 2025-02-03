import { Injectable } from "@angular/core";
import { SearchParams, UserSearchParams } from "../models/Search";
import { Language } from "../../language/models/Language";

@Injectable({
    providedIn: "root",
})
export class SearchUrlBuilderService {

    buildUserSearchUrl(baseUrl: string, searchParams: UserSearchParams): string {
        const url = this.buildSearchUrl(baseUrl, searchParams);

        const languages = searchParams?.languageLevels;
        if (languages && Object.keys(languages).length > 0) {
            const languageParam = Object.keys(languages)
                .map((key) => {
                    const language = key as Language;
                    const level = languages[language];
                    return `${language}:${level}`;
                })
                .join(",");
            return `${url}&languages=${encodeURIComponent(languageParam)}`;
        }

        return url;
    }

    buildSearchUrl(baseUrl: string, searchParams: SearchParams): string {
        const url =
            baseUrl +
            "?searchQuery=" +
            (searchParams.searchQuery ?? "") +
            "&sortBy=" +
            (searchParams.sortBy ?? "createdAt") +
            "&isAscending=" +
            (searchParams.isAscending ?? true) +
            "&page=" +
            (searchParams.page ?? 1) +
            "&itemsPerPage=" +
            (searchParams.itemsPerPage ?? 20);

        return url;
    }
}

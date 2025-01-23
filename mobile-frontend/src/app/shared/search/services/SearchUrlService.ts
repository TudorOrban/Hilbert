import { SearchParams } from "../models/Search";

export const SearchUrlService = {
    constructSearchUrl: (searchParams: SearchParams): string => {
        const urlSuffix = "?searchQuery=" + (searchParams.searchQuery ?? "") +
            "&sortBy=" + (searchParams.sortBy ?? "createdAt") +
            "&isAscending=" + (searchParams.isAscending ?? true) +
            "&page=" + (searchParams.page ?? 1) +
            "&itemsPerPage=" + (searchParams.itemsPerPage ?? 20);

        return urlSuffix;
    }
}
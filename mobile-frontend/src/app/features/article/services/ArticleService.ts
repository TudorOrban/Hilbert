import axiosInstance from "../../../core/main/config/AxiosInstance";
import { ArticleSearchParams, PaginatedResults } from "../../../shared/search/models/Search";
import { SearchUrlService } from "../../../shared/search/services/SearchUrlService";
import { ArticleFullDto, ArticleSearchDto, CreateArticleDto, UpdateArticleDto } from "../models/Article";

const RESOURCE_PATH = "/articles";

export const ArticleService = {
    
    searchArticles: async (searchParams: ArticleSearchParams): Promise<PaginatedResults<ArticleSearchDto>> => {
        const searchUrlSuffix = RESOURCE_PATH + "/search" + 
            SearchUrlService.constructSearchUrl(searchParams); 

        const response = await axiosInstance.get<PaginatedResults<ArticleSearchDto>>(searchUrlSuffix);
        return response.data;
    },

    getArticle: async (articleId: number): Promise<ArticleFullDto> => {
        const response = await axiosInstance.get<ArticleFullDto>(`${RESOURCE_PATH}/${articleId}`);
        return response.data;
    },

    createArticle: async (articleDto: CreateArticleDto): Promise<ArticleFullDto> => {
        const response = await axiosInstance.post<ArticleFullDto>(RESOURCE_PATH, articleDto);
        return response.data;
    },

    updateArticle: async (articleDto: UpdateArticleDto): Promise<ArticleFullDto> => {
        const response = await axiosInstance.put<ArticleFullDto>(RESOURCE_PATH, articleDto);
        return response.data;
    },

    deleteArticle: async (articleId: number): Promise<void> => {
        axiosInstance.delete(`${RESOURCE_PATH}/${articleId}`);
    }
}
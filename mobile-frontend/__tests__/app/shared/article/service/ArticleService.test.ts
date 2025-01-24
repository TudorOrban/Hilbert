import { SearchParams } from "../../../../../src/app/shared/search/models/Search";
import { SearchUrlService } from "../../../../../src/app/shared/search/services/SearchUrlService";
import { ArticleService } from "../../../../../src/app/features/article/services/ArticleService";

jest.mock("../../../../../src/app/shared/search/services/SearchUrlService");

jest.mock("../../../../../src/app/core/main/config/AxiosInstance", () => ({
    get: jest.fn(),
}));

const axiosInstance = require("../../../../../src/app/core/main/config/AxiosInstance");

describe("ArticleService.searchArticles", () => {
    beforeEach(() => {
      jest.clearAllMocks();
    });
    
    it("should call axiosInstance.get with the correct url and searchParams", async () => {
        // Arrange
        const searchParams: SearchParams = {
            searchQuery: "test",
            sortBy: "createdAt",
            isAscending: true,
            page: 1,
            itemsPerPage: 20,
        };

        const expectedUrl =
            "?searchQuery=test&sortBy=createdAt&isAscending=true&page=1&itemsPerPage=20";
        const mockConstructSearchUrl =
            SearchUrlService.constructSearchUrl as jest.Mock;
        mockConstructSearchUrl.mockReturnValueOnce(expectedUrl);

        const mockResponse = { data: {} };
        axiosInstance.get.mockResolvedValue(mockResponse);

        // Act
        const response = await ArticleService.searchArticles(searchParams);

        // Assert
        expect(axiosInstance.get).toHaveBeenCalledWith(
            `/articles/search${expectedUrl}`,
        );
        expect(SearchUrlService.constructSearchUrl).toHaveBeenCalledWith(
            searchParams,
        );
        expect(response).toEqual(mockResponse.data);
    });

    it("should handle errors from axiosInstance.get", async () => {
        // Arrange
        const searchParams: SearchParams = {
            searchQuery: "test",
        };
        const error = new Error("Network Error");
        axiosInstance.get.mockRejectedValue(error);

        // Act & Assert
        try {
            await ArticleService.searchArticles(searchParams);
            fail("An error should have been thrown");
        } catch (error) {
            expect(error).toEqual(error);
        }
    });
});

describe("ArticleService.getArticle", () => {
    beforeEach(() => {
      jest.clearAllMocks();
    });

    it("should call axiosInstance.get with the correct url", async () => {
        // Arrange
        const articleId = 1;

        const mockResponse = { data: {} };
        axiosInstance.get.mockResolvedValue(mockResponse);

        // Act
        const response = await ArticleService.getArticle(articleId);

        // Assert
        expect(axiosInstance.get).toHaveBeenCalledWith(`/articles/${articleId}`);
        expect(response).toEqual(mockResponse.data);
    });
});
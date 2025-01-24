import { ScrollView, View } from "react-native";
import { PaginatedResults } from "../../../shared/search/models/Search";
import { ArticleSearchDto } from "../models/Article";
import ArticleMediumCard from "./ArticleMediumCard";
import { useTailwind } from "tailwind-rn";

interface ArticlesListProps {
    searchResults?: PaginatedResults<ArticleSearchDto>;
}

const ArticlesList: React.FC<ArticlesListProps> = ({
    searchResults
}) => {
    const tailwind = useTailwind();

    return (
        <ScrollView style={tailwind("px-4")}>
            {searchResults?.results.map((article) => (
                <ArticleMediumCard key={article.id} article={article} />
            ))}
        </ScrollView>
    );
}


export default ArticlesList;
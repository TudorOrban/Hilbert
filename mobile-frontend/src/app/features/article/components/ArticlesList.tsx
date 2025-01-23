import { View } from "react-native";
import { PaginatedResults } from "../../../shared/search/models/Search";
import { ArticleSearchDto } from "../models/Article";
import ArticleMediumCard from "./ArticleMediumCard";

interface ArticlesListProps {
    searchResults?: PaginatedResults<ArticleSearchDto>;
}

const ArticlesList: React.FC<ArticlesListProps> = ({
    searchResults
}) => {

    return (
        <View>
            {searchResults?.results.map((article) => (
                <View>
                    <ArticleMediumCard article={article} />
                </View>
            ))}
        </View>
    );
}


export default ArticlesList;
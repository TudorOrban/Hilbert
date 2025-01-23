import { Text, View } from "react-native";
import { ArticleSearchDto } from "../models/Article";

interface ArticleMediumCardProps {
    article: ArticleSearchDto;
}

const ArticleMediumCard: React.FC<ArticleMediumCardProps> = ({
    article,
}) => {

    return (
        <View>
            <Text>{article?.title}</Text>
        </View>
    );
}


export default ArticleMediumCard;
import { RouteProp } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import { useEffect, useState } from "react";
import { View } from "react-native";
import { ArticleFullDto } from "../models/Article";
import { ArticleService } from "../services/ArticleService";
import ArticleHeader from "../components/ArticleHeader";
import ArticleContent from "./ArticleContent";

type ArticleScreenNavigationProp = StackNavigationProp<RootStackParamList, "Article">;
type ArticleScreenRouteProp = RouteProp<RootStackParamList, "Article">;

type Props = {
    navigation: ArticleScreenNavigationProp;
    route: ArticleScreenRouteProp;
};

const ArticleScreen: React.FC<Props> = ({ navigation, route }) => {
    const { itemId } = route.params;

    const [article, setArticle] = useState<ArticleFullDto>();

    useEffect(() => {
        const loadArticle = async () => {
            const loadedArticle = await ArticleService.getArticle(itemId);
            setArticle(loadedArticle);
        }

        loadArticle();
    }, []);
    
    return (
        <View>
            <ArticleHeader article={article} />  

            <ArticleContent article={article} />
        </View>
    );
}

export default ArticleScreen;
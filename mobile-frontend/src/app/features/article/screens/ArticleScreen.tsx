import { RouteProp } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import { useEffect, useState } from "react";
import { Image, Text, View } from "react-native";
import { useTailwind } from "tailwind-rn";
import { ArticleFullDto } from "../models/Article";
import { ArticleService } from "../services/ArticleService";
import { LanguageIconService } from "../../../shared/language/services/LanguageIconService";

type ArticleScreenNavigationProp = StackNavigationProp<RootStackParamList, "Article">;
type ArticleScreenRouteProp = RouteProp<RootStackParamList, "Article">;

type Props = {
    navigation: ArticleScreenNavigationProp;
    route: ArticleScreenRouteProp;
};

const ArticleScreen: React.FC<Props> = ({ navigation, route }) => {
    const { itemId } = route.params;

    const [article, setArticle] = useState<ArticleFullDto>();

    const loadArticle = async () => {
        const loadedArticle = await ArticleService.getArticle(itemId);
        setArticle(loadedArticle);
    }

    useEffect(() => {
        loadArticle();
    }, []);

    const tailwind = useTailwind();
    
    const languageIcon = LanguageIconService.getIconByLanguage(article?.language);

    return (
        <View style={tailwind("")}>
            <View style={tailwind("px-8 py-4 border-b border-gray-300")}>
                <Text style={tailwind("text-xl font-semibold")}>{article?.title}</Text>
                <View style={tailwind("flex flex-row items-center pt-4")}>
                    <Text style={tailwind("mr-2 text-lg font-semibold")}>Language: </Text>
                    <Image source={languageIcon} style={{ width: 24, height: 16 }} />
                    
                    <Text style={tailwind("ml-12 mr-2 text-lg font-semibold")}>Level: </Text>
                    <Text>{article?.level}</Text>
                </View>
            </View>            
        </View>
    );
}

export default ArticleScreen;
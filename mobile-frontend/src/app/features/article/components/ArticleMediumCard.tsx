import { Image, Text, TouchableOpacity, View } from "react-native";
import { ArticleSearchDto } from "../models/Article";
import { useTailwind } from "tailwind-rn";
import Ionicons from 'react-native-vector-icons/Ionicons';
import { LanguageIconService } from "../../../shared/language/services/LanguageIconService";
import { useNavigation } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";

interface ArticleMediumCardProps {
    article: ArticleSearchDto;
}

const ArticleMediumCard: React.FC<ArticleMediumCardProps> = ({
    article,
}) => {
    const tailwind = useTailwind();
    const navigation = useNavigation<StackNavigationProp<RootStackParamList, "Reading">>();

    const languageIcon = LanguageIconService.getIconByLanguage(article.language);

    const navigateToArticle = () => {
        navigation.navigate("Article", { itemId: article.id });
    }

    return (
        <TouchableOpacity onPress={navigateToArticle} style={tailwind("flex flex-row items-center my-2 px-4 py-2 bg-gray-50 border border-gray-300 rounded-md")}>
            <View style={tailwind("flex-grow flex-shrink mr-4")}>
                <Text style={tailwind("text-lg font-semibold")}>{article?.title}</Text>
                <Text style={tailwind("text-gray-800")}>{article?.description ?? ""}</Text>

                <View style={tailwind("flex flex-row items-center pt-4")}>
                    <Text style={tailwind("mr-2 font-semibold")}>Language: </Text>
                    <Image source={languageIcon} style={{ width: 24, height: 16 }} />

                    <Text style={tailwind("ml-8 font-semibold")}>Level: </Text>
                    <Text style={tailwind("font-semibold")}>{article.level}</Text>
                </View>
            </View>
            <View>
                <View style={tailwind("flex flex-row items-center w-24 px-2 py-1 mb-2 border border-gray-300 rounded-md")}>
                    <Ionicons name="star" />
                    <Text style={tailwind("mx-1 text-gray-800 font-semibold")}>{article.averageRating ?? 0}</Text>
                    <Text>({article?.numberOfRatings ?? 0})</Text>
                </View>
                <View style={tailwind("flex flex-row items-center w-24 px-2 py-1 border border-gray-300 rounded-md")}>
                    <Ionicons name="eye" />
                    <Text style={tailwind("mx-1 text-gray-800 font-semibold")}>{article?.readCount ?? 0}</Text>
                </View>
            </View>
        </TouchableOpacity>
    );
}


export default ArticleMediumCard;
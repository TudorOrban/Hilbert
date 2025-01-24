import { Image, Text, View } from "react-native";
import { useTailwind } from "tailwind-rn";
import Ionicons from 'react-native-vector-icons/Ionicons';
import { LanguageIconService } from "../../../shared/language/services/LanguageIconService";
import { ArticleFullDto } from "../models/Article";

interface ArticleHeaderProps {
    article?: ArticleFullDto;
}

const ArticleHeader: React.FC<ArticleHeaderProps> = ({
    article,
}) => {
    const tailwind = useTailwind();

    const languageIcon = LanguageIconService.getIconByLanguage(article?.language);

    return (
        <View style={tailwind("px-8 py-4 border-b border-gray-300")}>
            <Text style={tailwind("text-xl font-semibold")}>{article?.title}</Text>
            <Text style={tailwind("text-gray-800 py-2")}>{article?.description}</Text>
            <View style={tailwind("flex flex-row items-center")}>
                <Text style={tailwind("mr-2 text-lg font-semibold")}>Language: </Text>
                <Image source={languageIcon} style={{ width: 24, height: 16 }} />
                
                <Text style={tailwind("ml-12 mr-2 text-lg font-semibold")}>Level: </Text>
                <Text style={tailwind("font-semibold")}>{article?.level}</Text>
            </View>

            <View style={tailwind("flex flex-row items-center pt-4")}>
                <View style={tailwind("flex flex-row items-center w-24 px-2 py-1 mr-4 border border-gray-400 rounded-md")}>
                    <Ionicons name="star" />
                    <Text style={tailwind("mx-1 text-gray-800 font-semibold")}>{article?.averageRating ?? 0}</Text>
                    <Text>({article?.numberOfRatings ?? 0})</Text>
                </View>
                <View style={tailwind("flex flex-row items-center w-24 px-2 py-1 border border-gray-400 rounded-md")}>
                    <Ionicons name="eye" />
                    <Text style={tailwind("mx-1 text-gray-800 font-semibold")}>{article?.readCount ?? 0}</Text>
                </View>
            </View>   
        </View> 
    );
}

export default ArticleHeader;
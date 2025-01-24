import { Text, View } from "react-native";
import { ArticleSearchDto } from "../models/Article";
import { useTailwind } from "tailwind-rn";
import Ionicons from 'react-native-vector-icons/Ionicons';

interface ArticleMediumCardProps {
    article: ArticleSearchDto;
}

const ArticleMediumCard: React.FC<ArticleMediumCardProps> = ({
    article,
}) => {
    const tailwind = useTailwind();

    return (
        <View style={tailwind("flex flex-row items-center my-2 px-4 py-2 border border-gray-300 rounded-md")}>
            <View style={tailwind("flex-grow flex-shrink")}>
                <Text style={tailwind("text-lg font-semibold")}>{article?.title}</Text>
                <Text style={tailwind("text-gray-800")}>{article?.description ?? ""}</Text>

                <View style={tailwind("flex flex-row items-center pt-4")}>
                    <Text style={tailwind("font-semibold")}>Language: </Text>
                    
                </View>
            </View>
            <View>
                <View style={tailwind("flex flex-row items-center w-24 px-2 py-1 mb-2 bg-gray-100 border border-gray-200 rounded-md")}>
                    <Ionicons name="star" />
                    <Text style={tailwind("mx-1")}>{article.averageRating ?? 0}</Text>
                    <Text>({article?.numberOfRatings ?? 0})</Text>
                </View>
                <View style={tailwind("flex flex-row items-center w-24 px-2 py-1 bg-gray-100 border border-gray-200 rounded-md")}>
                    <Ionicons name="eye" />
                    <Text style={tailwind("mx-1")}>{article?.readCount ?? 0}</Text>
                </View>
            </View>
        </View>
    );
}


export default ArticleMediumCard;
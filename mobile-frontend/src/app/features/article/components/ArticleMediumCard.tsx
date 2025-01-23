import { Text, View } from "react-native";
import { ArticleSearchDto } from "../models/Article";
import { useTailwind } from "tailwind-rn";

interface ArticleMediumCardProps {
    article: ArticleSearchDto;
}

const ArticleMediumCard: React.FC<ArticleMediumCardProps> = ({
    article,
}) => {
    const tailwind = useTailwind();

    return (
        <View style={tailwind("flex flex-row items-center my-2 px-4 py-2 border border-gray-300 rounded-md shadow-sm")}>
            <View style={tailwind("flex-grow")}>
                <Text style={tailwind("text-lg font-semibold")}>{article?.title}</Text>
                <Text style={tailwind("text-gray-800")}>{article?.description ?? ""}</Text>

                <View style={tailwind("flex flex-row items-center pt-4")}>
                    <Text>Language: </Text>
                </View>
            </View>
            <View>
                <Text>test</Text>
            </View>
        </View>
    );
}


export default ArticleMediumCard;
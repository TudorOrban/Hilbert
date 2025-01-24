import { RouteProp } from "@react-navigation/native";
import { StackNavigationProp } from "@react-navigation/stack";
import { Text, View } from "react-native";

type ArticleScreenNavigationProp = StackNavigationProp<RootStackParamList, "Article">;
type ArticleScreenRouteProp = RouteProp<RootStackParamList, "Article">;

type Props = {
    navigation: ArticleScreenNavigationProp;
    route: ArticleScreenRouteProp;
};

const ArticleScreen: React.FC<Props> = ({ navigation, route }) => {
    const { itemId } = route.params;
    return (
        <View>
            <Text>Article ID: {itemId}</Text>
        </View>
    );
}

export default ArticleScreen;
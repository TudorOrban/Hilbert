import { StackNavigationProp } from "@react-navigation/stack";
import { Text, View } from "react-native";


type CreateArticleScreenNavigationProp = StackNavigationProp<RootStackParamList, "CreateArticle">;

type Props = {
    navigation: CreateArticleScreenNavigationProp;
};

const CreateArticleScreen: React.FC<Props> = ({ navigation }) => {

    return (
        <View>
            <Text>Create Article</Text>
        </View>
    );
}

export default CreateArticleScreen;
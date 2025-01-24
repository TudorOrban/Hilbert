import { createStackNavigator } from "@react-navigation/stack";
import ReadingScreen from "../../../features/article/screens/ReadingScreen";
import CreateArticleScreen from "../../../features/article/screens/CreateArticleScreen";
import ArticleScreen from "../../../features/article/screens/ArticleScreen";

const ReadingStack = createStackNavigator();

const ReadingStackNavigator = () => {
    return (
        <ReadingStack.Navigator initialRouteName="Reading">
            <ReadingStack.Screen 
                name="Reading" 
                component={ReadingScreen} 
                options={{
                    headerShown: false,
                }}
            />
            <ReadingStack.Screen
                name="Article"
                component={ArticleScreen}
                options={{
                    headerShown: false,
                }}
            />
            <ReadingStack.Screen 
                name="CreateArticle" 
                component={CreateArticleScreen} 
                options={{
                    title: "Create Article",
                    headerBackTitle: "Back",
                }}
            />            
        </ReadingStack.Navigator>
    );
}

export default ReadingStackNavigator;
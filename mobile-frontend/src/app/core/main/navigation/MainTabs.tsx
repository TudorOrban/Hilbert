import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import Ionicons from 'react-native-vector-icons/Ionicons';
import HomeScreen from "../screens/HomeScreen";
import GrammarScreen from "../../../features/exercise/screens/GrammarScreen";
import VocabularyScreen from "../../../features/vocabulary/screens/Vocabulary";
import ChatScreen from "../../../features/chat/screens/ChatScreen";
import ReadingStackNavigator from "./ReadingStackNavigation";

const Tab = createBottomTabNavigator();

const MainTabs = () => {
    const iconMap: Record<string, string> = {
        "Home": "home",
        "Reading": "list",
        "Vocabulary": "book",
        "Exercises": "language",
        "Chat": "chatbox"
    };

    return (
        <Tab.Navigator
            screenOptions={({ route }) => ({
                tabBarIcon: ({ focused, color, size }) => {
                    let iconName = iconMap[route.name];

                    return <Ionicons name={iconName ?? "home"} size={size} color={color} />
                },
            })}
        >
            <Tab.Screen name="Home" component={HomeScreen} />
            <Tab.Screen name="Reading" component={ReadingStackNavigator} />
            <Tab.Screen name="Vocabulary" component={VocabularyScreen} />
            <Tab.Screen name="Exercises" component={GrammarScreen} />
            <Tab.Screen name="Chat" component={ChatScreen} />            

        </Tab.Navigator>
    )
}

export default MainTabs;
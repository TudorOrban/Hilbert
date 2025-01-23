import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import HomeScreen from "../screens/HomeScreen";
import ReadingScreen from "../screens/ReadingScreen";
import GrammarScreen from "../screens/GrammarScreen";
import Ionicons from 'react-native-vector-icons/Ionicons';
import VocabularyScreen from "../screens/Vocabulary";
import ChatScreen from "../screens/Chat";

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
            <Tab.Screen name="Reading" component={ReadingScreen} />
            <Tab.Screen name="Vocabulary" component={VocabularyScreen} />
            <Tab.Screen name="Exercises" component={GrammarScreen} />
            <Tab.Screen name="Chat" component={ChatScreen} />            

        </Tab.Navigator>
    )
}

export default MainTabs;
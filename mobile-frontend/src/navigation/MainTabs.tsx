import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import HomeScreen from "../app/core/main/screens/HomeScreen";
import ReadingScreen from "../app/core/main/screens/ReadingScreen";
import GrammarScreen from "../app/core/main/screens/GrammarScreen";
import Ionicons from 'react-native-vector-icons/Ionicons';

const Tab = createBottomTabNavigator();

const MainTabs = () => {

    return (
        <Tab.Navigator
            screenOptions={({ route }) => ({
                tabBarIcon: ({ focused, color, size }) => {
                    let iconName = "";

                    if (route.name === "Home") {
                        iconName = focused ? "ios-home" : "ios-home-outline";
                    } else if (route.name === "Reading") {
                        iconName = focused ? "ios-book" : "ios-book-outline";
                    } else if (route.name === "Grammar") {
                        iconName = focused ? "ios-list" : "ios-list-outline";
                    }

                    return <Ionicons name={"home"} size={size} color={color} />
                },
            })}
        >
            <Tab.Screen name="Home" component={HomeScreen} />
            <Tab.Screen name="Reading" component={ReadingScreen} />
            <Tab.Screen name="Grammar" component={GrammarScreen} />
        </Tab.Navigator>
    )
}

export default MainTabs;
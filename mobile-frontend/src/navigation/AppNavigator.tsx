import React from 'react';
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import LoginScreen from '../app/core/user/screens/LoginScreen';
import HomeScreen from '../app/core/main/screens/HomeScreen';
import { useCurrentUser } from '../app/core/user/contexts/CurrentUserContext';


const Stack = createStackNavigator();

function AppNavigator() {
    const { currentUser, loading } = useCurrentUser();
    
    if (loading) {
        return null;
    }

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName={!!currentUser ? "Home" : "Login"}>
                <Stack.Screen name="Home" component={HomeScreen} />
                <Stack.Screen name="Login" component={LoginScreen} />
            </Stack.Navigator>
        </NavigationContainer>
    );
}

export default AppNavigator;
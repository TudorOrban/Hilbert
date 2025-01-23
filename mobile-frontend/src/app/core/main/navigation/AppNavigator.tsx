import React from 'react';
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import LoginScreen from '../../user/screens/LoginScreen';
import HomeScreen from '../screens/HomeScreen';
import { useCurrentUser } from '../../user/contexts/CurrentUserContext';
import MainTabs from './MainTabs';


const Stack = createStackNavigator();

function AppNavigator() {
    const { currentUser, loading } = useCurrentUser();
    
    if (loading) {
        return null;
    }

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName={!!currentUser ? "MainTabs" : "Login"}>
                <Stack.Screen name="MainTabs" component={MainTabs} options={{ headerShown: false }} />
                <Stack.Screen name="Login" component={LoginScreen} />
            </Stack.Navigator>
        </NavigationContainer>
    );
}

export default AppNavigator;
import React, { useEffect, useState } from 'react';
import { NavigationContainer } from "@react-navigation/native";
import { createStackNavigator } from "@react-navigation/stack";
import LoginScreen from '../app/core/user/screens/LoginScreen';
import HomeScreen from '../app/core/main/screens/HomeScreen';
import AsyncStorage from '@react-native-async-storage/async-storage';

const Stack = createStackNavigator();

function AppNavigator() {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const checkAuthentication = async () => {
            const token = await AsyncStorage.getItem("token");
            setIsAuthenticated(!!token);
            setLoading(false);
        };

        checkAuthentication();
    }, []);

    if (loading) {
        return null;
    }

    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName={isAuthenticated ? "Home" : "Login"}>
            {isAuthenticated ? (
                <Stack.Screen name="Home" component={HomeScreen} />
              ) : (
                <>
                  <Stack.Screen name="Login" component={LoginScreen} />
                </>
              )}
            </Stack.Navigator>
        </NavigationContainer>
    );
}

export default AppNavigator;
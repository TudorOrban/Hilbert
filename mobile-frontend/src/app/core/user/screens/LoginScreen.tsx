import React, { useState } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Button, StyleSheet, Text, TextInput, View } from 'react-native';
import { StackNavigationProp } from '@react-navigation/stack';
import { AuthService } from '../services/AuthService';
import { useCurrentUser } from '../contexts/CurrentUserContext';

type LoginScreenNavigationProp = StackNavigationProp<RootStackParamList, "Login">;

type Props = {
    navigation: LoginScreenNavigationProp;
};

const LoginScreen: React.FC<Props> = ({ navigation }) => {
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const { login } = useCurrentUser();

    const handleLogin = async () => {
        try {
            await login(username, password);
            
            navigation.navigate("Home");
        } catch (loginError) {
            setError("Invalid username or password");
        }
    };

    return (
        <View style={styles.container}>
            <Text style={styles.title}>Login</Text>
            <TextInput
                style={styles.input}
                placeholder="Username"
                value={username}
                onChangeText={setUsername}
            />
            <TextInput
                style={styles.input}
                placeholder="Password"
                value={password}
                onChangeText={setPassword}
                secureTextEntry
            />
            {error ? <Text style={styles.error}>{error}</Text> : null}

            <Button title="Login" onPress={handleLogin} />
        </View>
    )
};

const styles = StyleSheet.create({
    container: {
      flex: 1,
      justifyContent: "center",
      padding: 16,
    },
    title: {
      fontSize: 24,
      marginBottom: 24,
    },
    input: {
      height: 40,
      borderColor: "gray",
      borderWidth: 1,
      marginBottom: 12,
      paddingHorizontal: 8,
    },
    error: {
      color: "red",
      marginBottom: 12,
    },
});

export default LoginScreen;

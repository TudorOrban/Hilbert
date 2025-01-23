import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { Button, Text, View } from 'react-native';
import { useCurrentUser } from '../../user/contexts/CurrentUserContext';

type HomeScreenNavigationProp = StackNavigationProp<RootStackParamList, "Home">;

type Props = {
    navigation: HomeScreenNavigationProp;
};

const HomeScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    return (
        <View>
            <Text>Home Screen</Text>
            <Text>{currentUser.username}</Text>
            <Button
                title="Go to Login"
                onPress={() => navigation.navigate("Login")}
            />
        </View>
    );
}

export default HomeScreen;

import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { Button, Text, View } from 'react-native';
import { useCurrentUser } from '../../../core/user/contexts/CurrentUserContext';

type ChatScreenNavigationProp = StackNavigationProp<RootStackParamList, "Chat">;

type Props = {
    navigation: ChatScreenNavigationProp;
};

const ChatScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    return (
        <View>
            <Text>Chat Screen</Text>
            <Text>{currentUser?.username}</Text>
            <Button
                title="Go to Login"
                onPress={() => navigation.navigate("Login")}
            />
        </View>
    );
}

export default ChatScreen;

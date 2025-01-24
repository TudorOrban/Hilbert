import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { Button, Text, View } from 'react-native';
import { useCurrentUser } from '../../../core/user/contexts/CurrentUserContext';

type GrammarScreenNavigationProp = StackNavigationProp<RootStackParamList, "Grammar">;

type Props = {
    navigation: GrammarScreenNavigationProp;
};

const GrammarScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    return (
        <View>
            <Text>Grammar Screen</Text>
            <Text>{currentUser?.username}</Text>
            <Button
                title="Go to Login"
                onPress={() => navigation.navigate("Login")}
            />
        </View>
    );
}

export default GrammarScreen;

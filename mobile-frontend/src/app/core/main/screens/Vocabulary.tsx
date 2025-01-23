import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { Button, Text, View } from 'react-native';
import { useCurrentUser } from '../../user/contexts/CurrentUserContext';

type VocabularyScreenNavigationProp = StackNavigationProp<RootStackParamList, "Vocabulary">;

type Props = {
    navigation: VocabularyScreenNavigationProp;
};

const VocabularyScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    return (
        <View>
            <Text>Vocabulary Screen</Text>
            <Text>{currentUser?.username}</Text>
            <Button
                title="Go to Login"
                onPress={() => navigation.navigate("Login")}
            />
        </View>
    );
}

export default VocabularyScreen;

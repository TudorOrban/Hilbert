import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { Button, Text, View } from 'react-native';
import { useCurrentUser } from '../../user/contexts/CurrentUserContext';

type ReadingScreenNavigationProp = StackNavigationProp<RootStackParamList, "Reading">;

type Props = {
    navigation: ReadingScreenNavigationProp;
};

const ReadingScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    return (
        <View>
            
        </View>
    );
}

export default ReadingScreen;

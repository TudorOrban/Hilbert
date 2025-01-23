import { StackNavigationProp } from '@react-navigation/stack';
import React from 'react';
import { View } from 'react-native';
import { useCurrentUser } from '../../user/contexts/CurrentUserContext';
import ReadingPageHeader from '../../../features/article/components/ReadingPageHeader';
import ArticlesList from '../../../features/article/components/ArticlesList';
import AdvancedSearchPanel from '../../../features/article/components/AdvancedSearchPanel';

type ReadingScreenNavigationProp = StackNavigationProp<RootStackParamList, "Reading">;

type Props = {
    navigation: ReadingScreenNavigationProp;
};

const ReadingScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    return (
        <View>
            <ReadingPageHeader />

            <View>
                <ArticlesList />
                <AdvancedSearchPanel />
            </View>
        </View>
    );
}

export default ReadingScreen;

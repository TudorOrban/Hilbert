import { StackNavigationProp } from '@react-navigation/stack';
import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import { useCurrentUser } from '../../user/contexts/CurrentUserContext';
import ReadingPageHeader from '../../../features/article/components/ReadingPageHeader';
import ArticlesList from '../../../features/article/components/ArticlesList';
import AdvancedSearchPanel from '../../../features/article/components/AdvancedSearchPanel';
import { ArticleSearchDto, ArticleStatus, DifficultyLevel } from '../../../features/article/models/Article';
import { ArticleSearchParams, PaginatedResults } from '../../../shared/search/models/Search';
import { ArticleService } from '../../../features/article/services/ArticleService';

type ReadingScreenNavigationProp = StackNavigationProp<RootStackParamList, "Reading">;

type Props = {
    navigation: ReadingScreenNavigationProp;
};

const ReadingScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    const [searchQuery, setSearchQuery] = useState("");

    const [searchResults, setSearchResults] = useState<PaginatedResults<ArticleSearchDto>>();

    useEffect(() => {
        searchArticles();
    }, []);

    const searchArticles = async () => {
        const searchParams: ArticleSearchParams = {
            searchQuery: searchQuery,
        };

        const results = await ArticleService.searchArticles(searchParams);
        setSearchResults(results);
    }
    
    return (
        <View>
            <ReadingPageHeader searchQuery={searchQuery} setSearchQuery={setSearchQuery} handleSearch={searchArticles} />
            <AdvancedSearchPanel />

            <ArticlesList searchResults={searchResults} />
        </View>
    );
}

export default ReadingScreen;

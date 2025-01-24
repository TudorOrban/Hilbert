import { StackNavigationProp } from '@react-navigation/stack';
import React, { useEffect, useState } from 'react';
import { Text, View } from 'react-native';
import { useCurrentUser } from '../../user/contexts/CurrentUserContext';
import ReadingPageHeader from '../../../features/article/components/ReadingPageHeader';
import ArticlesList from '../../../features/article/components/ArticlesList';
import AdvancedSearchPanel from '../../../features/article/components/AdvancedSearchPanel';
import { ArticleSearchDto, ArticleStatus, DifficultyLevel } from '../../../features/article/models/Article';
import { ArticleSearchParams, PaginatedResults } from '../../../shared/search/models/Search';
import { ArticleService } from '../../../features/article/services/ArticleService';
import { useTailwind } from 'tailwind-rn';
import LoadingFallback from '../../../shared/search/components/LoadingFallback';

type ReadingScreenNavigationProp = StackNavigationProp<RootStackParamList, "Reading">;

type Props = {
    navigation: ReadingScreenNavigationProp;
};

const ReadingScreen: React.FC<Props> = ({ navigation }) => {
    const { currentUser } = useCurrentUser();

    const [searchQuery, setSearchQuery] = useState("");

    const [searchResults, setSearchResults] = useState<PaginatedResults<ArticleSearchDto>>();

    const [isLoading, setIsLoading] = useState(false);

    const tailwind = useTailwind();

    useEffect(() => {
        searchArticles();
    }, []);

    const searchArticles = async () => {
        setIsLoading(true);

        const searchParams: ArticleSearchParams = {
            searchQuery: searchQuery,
        };

        const results = await ArticleService.searchArticles(searchParams);
        setSearchResults(results);
        setIsLoading(false);
    }
    
    return (
        <View style={tailwind("h-full")}>
            <ReadingPageHeader searchQuery={searchQuery} setSearchQuery={setSearchQuery} handleSearch={searchArticles} />
            <AdvancedSearchPanel />

            {!isLoading ? (
                <ArticlesList searchResults={searchResults} />
            ) : (
                <LoadingFallback />
            )}
        </View>
    );
}

export default ReadingScreen;

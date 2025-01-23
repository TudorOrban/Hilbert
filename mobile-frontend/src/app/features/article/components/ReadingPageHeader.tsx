import { View } from "react-native";
import SearchInput from "../../../shared/search/components/SearchInput";
import { useState } from "react";
import { useTailwind } from "tailwind-rn";
import { PaginatedResults } from "../../../shared/search/models/Search";
import { ArticleSearchDto } from "../models/Article";

interface ReadingPageHeaderProps {
    searchQuery: string;
    setSearchQuery: (text: string) => void;
    handleSearch: () => void;
}

const ReadingPageHeader: React.FC<ReadingPageHeaderProps> = ({
    searchQuery,
    setSearchQuery,
    handleSearch,
}) => {


    const tailwind = useTailwind();
    

    return (
        <View style={tailwind("flex flex-row items-center px-8 py-4")}>
            <SearchInput searchQuery={searchQuery} setSearchQuery={setSearchQuery} handleSearch={handleSearch} />
        </View>
    );
}

export default ReadingPageHeader;
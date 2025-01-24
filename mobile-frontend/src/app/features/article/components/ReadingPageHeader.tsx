import { Text, TouchableOpacity, View } from "react-native";
import SearchInput from "../../../shared/search/components/SearchInput";
import { useTailwind } from "tailwind-rn";
import Ionicons from 'react-native-vector-icons/Ionicons';

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
        <View>
            <Text style={tailwind("p-4 text-xl font-semibold")}>
                Articles
            </Text>

            <View style={tailwind("flex flex-row items-center justify-between px-4 pb-4")}>
                <View style={tailwind("flex flex-row items-center space-x-2")}>
                    <SearchInput searchQuery={searchQuery} setSearchQuery={setSearchQuery} handleSearch={handleSearch} />
                </View>

                <TouchableOpacity style={tailwind("flex flex-row items-center px-2 h-8 bg-blue-600 text-white border border-gray-200 rounded-md")}>
                    <Ionicons name="add-outline" size={20} color={"white"}/>
                    <Text style={tailwind("font-semibold text-white ml-2")}>Add Article</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
}

export default ReadingPageHeader;
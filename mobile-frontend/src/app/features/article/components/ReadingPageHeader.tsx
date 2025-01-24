import { Text, TouchableOpacity, View } from "react-native";
import SearchInput from "../../../shared/search/components/SearchInput";
import { useTailwind } from "tailwind-rn";
import Ionicons from 'react-native-vector-icons/Ionicons';
import { StackNavigationProp } from "@react-navigation/stack";

type ReadingPageHeaderNavigationProp = StackNavigationProp<RootStackParamList, 'Reading'>;

interface ReadingPageHeaderProps {
    navigation: ReadingPageHeaderNavigationProp;
    searchQuery: string;
    setSearchQuery: (text: string) => void;
    handleSearch: () => void;
}

const ReadingPageHeader: React.FC<ReadingPageHeaderProps> = ({
    navigation,
    searchQuery,
    setSearchQuery,
    handleSearch,
}) => {
    const tailwind = useTailwind();

    const navigateToCreateArticle = () => {
        navigation.navigate("CreateArticle");
    }

    return (
        <View>
            <Text style={tailwind("p-4 text-xl font-semibold")}>
                Articles
            </Text>

            <View style={tailwind("flex flex-row items-center justify-between px-4 pb-4")}>
                <View style={tailwind("flex flex-row items-center")}>
                    <SearchInput searchQuery={searchQuery} setSearchQuery={setSearchQuery} handleSearch={handleSearch} />
                </View>

                <TouchableOpacity
                    style={tailwind("flex flex-row items-center px-2 h-8 bg-blue-600 text-white border border-gray-300 rounded-md")}
                    onPress={navigateToCreateArticle}
                >
                    <Ionicons name="add-outline" size={20} color={"white"}/>
                    <Text style={tailwind("font-semibold text-white ml-2")}>Add Article</Text>
                </TouchableOpacity>
            </View>
        </View>
    );
}

export default ReadingPageHeader;
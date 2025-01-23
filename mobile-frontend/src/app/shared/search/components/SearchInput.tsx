import { Keyboard, StyleSheet, TouchableOpacity, View } from "react-native";
import { TextInput } from "react-native-gesture-handler";
import { useTailwind } from "tailwind-rn";
import Ionicons from 'react-native-vector-icons/Ionicons';

interface SearchInputProps {
    searchQuery: string;
    setSearchQuery: (text: string) => void;
    handleSearch?: () => void; 
}

const SearchInput: React.FC<SearchInputProps> = ({
    searchQuery,
    setSearchQuery,
    handleSearch
}) => {
    const tailwind = useTailwind();

    const search = () => {
        handleSearch?.();
        Keyboard.dismiss();
    }
    
    return (
        <View style={tailwind("flex flex-row items-center")}>
            <TextInput
                style={tailwind("w-32 h-8 p-2 border border-gray-300 rounded-l-md")}
                placeholder="Search Articles..."
                value={searchQuery}
                onChangeText={setSearchQuery}
                onSubmitEditing={search}
                returnKeyType="search"
            />
            <TouchableOpacity
                style={tailwind('flex items-center justify-center w-8 h-8 bg-gray-800 border border-gray-300 rounded-r-md')}
                onPress={search}
            >
                <Ionicons name="search" color={"white"}/>
            </TouchableOpacity>
        </View>
    );
}

export default SearchInput;
import { StyleSheet, TouchableOpacity, View } from "react-native";
import { TextInput } from "react-native-gesture-handler";
import { useTailwind } from "tailwind-rn";
import Ionicons from 'react-native-vector-icons/Ionicons';

interface SearchInputProps {
    searchText: string;
    setSearchText: (text: string) => void;
}

const SearchInput: React.FC<SearchInputProps> = ({
    searchText,
    setSearchText
}: SearchInputProps) => {
    const tailwind = useTailwind();
    console.log(tailwind('bg-blue-600 w-20 h-20'));
    
    return (
        <View style={tailwind("flex flex-row items-center")}>
            <TextInput
                style={tailwind("w-32 h-8 p-2 border border-gray-300 rounded-l-md shadow-sm")}
                placeholder="Search Articles"
            />
            <TouchableOpacity
                style={tailwind('flex items-center justify-center w-8 h-8 bg-gray-800 border border-gray-300 rounded-r-md shadow-sm')}
            >
                <Ionicons name="search"/>
            </TouchableOpacity>
        </View>
    );
}

export default SearchInput;
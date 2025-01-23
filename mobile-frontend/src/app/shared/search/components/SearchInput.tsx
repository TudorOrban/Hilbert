import { Button, StyleSheet, TouchableOpacity, View } from "react-native";
import { TextInput } from "react-native-gesture-handler";
import { Icon } from "react-native-vector-icons/Icon";

interface SearchInputProps {
    searchText: string;
    setSearchText: (text: string) => void;
}

const SearchInput: React.FC<SearchInputProps> = ({
    searchText,
    setSearchText
}: SearchInputProps) => {
    
    return (
        <View style={styles.container}>
            <TextInput

            />
            <TouchableOpacity>
                {/* <Icon name="search"/> */}
            </TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        flexDirection: "row",
        alignItems: "center",
        width: 200,
        height: 200,
        backgroundColor: "#000"
    }
});


export default SearchInput;
import { StyleSheet, View } from "react-native";
import SearchInput from "../../../shared/search/components/SearchInput";
import { useState } from "react";


const ReadingPageHeader = () => {
    const [searchText, setSearchText] = useState('');
    
    return (
        <View style={styles.headerContainer}>
            <SearchInput searchText={searchText} setSearchText={setSearchText}/>
        </View>
    );
}

const styles = StyleSheet.create({
    headerContainer: {
        flexDirection: "row",
        alignItems: "center",
        paddingVertical: 8,
        paddingHorizontal: 16
    }
});

export default ReadingPageHeader;
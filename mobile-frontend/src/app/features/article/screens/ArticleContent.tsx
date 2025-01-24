import { Modal, Text, TouchableOpacity, View } from "react-native"
import { ArticleFullDto } from "../models/Article";
import { useTailwind } from "tailwind-rn";
import { useState } from "react";

interface ArticleContentProps {
    article?: ArticleFullDto;
}

const ArticleContent: React.FC<ArticleContentProps> = ({
    article,
}) => {
    const tailwind = useTailwind();

    const [selectedWord, setSelectedWord] = useState<string | null>();
    const [translation, setTranslation] = useState<string | null>();
    const [isModalVisible, setIsModalVisible] = useState(false);

    const handleWordPress = (word: string) => {
        const translatedWord = getTranslation(word);
        setSelectedWord(word);
        setTranslation(translatedWord);
        setIsModalVisible(true);
    }

    const getTranslation = (word: string): string => {
        return `Translation of ${word}`;
    }

    const closeModal = () => {
        setIsModalVisible(false);
        setSelectedWord(null);
        setTranslation(null);
    }

    return (
        <View style={tailwind("p-4")}>
            {article?.content.split(" ").map((word, index) => (
                <TouchableOpacity key={index} onPress={() => handleWordPress(word)}>
                    <Text style={tailwind("text-blue-500")}>{word} </Text>
                </TouchableOpacity>
            ))}

            <Modal
                animationType="slide"
                transparent={true}
                visible={isModalVisible}
                onRequestClose={closeModal}
            >
                <View style={tailwind("flex-1 justify-center items-center")}>
                    <View style={tailwind("bg-white p-4 rounded-lg")}>
                        <Text>{selectedWord}</Text>
                        <Text>{translation}</Text>
                        <TouchableOpacity
                            onPress={closeModal}
                        >
                            <Text>Close</Text>
                        </TouchableOpacity>
                    </View>
                </View>
            </Modal>
        </View>
    );
}

export default ArticleContent;
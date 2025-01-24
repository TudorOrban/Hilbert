import { StyleSheet, Text, TouchableOpacity, View } from "react-native"
import { ArticleFullDto } from "../models/Article";
import { useTailwind } from "tailwind-rn";
import { createRef, useRef, useState } from "react";

interface ArticleContentProps {
    article?: ArticleFullDto;
}

const ArticleContent: React.FC<ArticleContentProps> = ({
    article,
}) => {
    const tailwind = useTailwind();
    const [selectedWord, setSelectedWord] = useState<string | null>(null);
    const [translation, setTranslation] = useState<string | null>(null);
    const [tooltipPosition, setTooltipPosition] = useState({ x: 0, y: 0 });
    const wordRefs = useRef<{ [key: string]: React.RefObject<Text> }>({});

    const handleWordPress = (word: string) => {
        const translatedWord = getTranslation(word);
        setSelectedWord(word);
        setTranslation(translatedWord);

        const ref = wordRefs.current[word];
        if (ref && ref.current) {
            ref.current.measure((fx: number, fy: number, width: number, height: number, px: number, py: number) => {
                const HEADER_Y_ADJUSTMENT = 300;
                setTooltipPosition({ x: px, y: py + height - HEADER_Y_ADJUSTMENT });
            });
        }
    };

    const getTranslation = (word: string): string => {
        return `Translation of ${word}`;
    };

    return (
        <View style={tailwind("relative p-4")}>
            <View style={tailwind("flex-row flex-wrap")}>
                {article?.content.split(" ").map((word, index) => {
                    if (!wordRefs.current[word]) {
                        wordRefs.current[word] = createRef<Text>();
                    }
                    return (
                        <TouchableOpacity
                            key={index}
                            onPress={() => handleWordPress(word)}
                            style={tailwind('')}
                        >
                            <Text ref={wordRefs.current[word]}>{word} </Text>
                        </TouchableOpacity>
                    );
                })}
            </View>
            {selectedWord && translation && (
                <View
                    style={[
                        styles.tooltip,
                        {
                            left: tooltipPosition.x,
                            top: tooltipPosition.y,
                        },
                    ]}
                >
                    <Text>{translation}</Text>
                </View>
            )}
        </View>
    );
};

const styles = StyleSheet.create({
    tooltip: {
        position: "absolute",
        backgroundColor: "rgb(255, 255, 255)",
        padding: 10,
        borderRadius: 5,
        zIndex: 30,
    },
});


export default ArticleContent;
import React, { useState, useRef, useEffect } from 'react';
import { View, Text, Modal, TouchableOpacity, Animated } from 'react-native';
import { useTailwind } from 'tailwind-rn';

interface TooltipProps {
    children: React.ReactNode;
    content: string;
}

const Tooltip: React.FC<TooltipProps> = ({ children, content }) => {
    const tailwind = useTailwind();
    const [visible, setVisible] = useState(false);
    const [position, setPosition] = useState({ x: 0, y: 0 });
    const tooltipRef = useRef<Animated.Value>(new Animated.Value(0));

    const showTooltip = (event: any) => {
        const { pageX, pageY } = event.nativeEvent;
        setPosition({ x: pageX, y: pageY });
        setVisible(true);
        Animated.spring(tooltipRef.current, {
            toValue: 1,
            useNativeDriver: true,
        }).start();
    };

    return (
        <View style={tailwind("relative")}>
            <TouchableOpacity onPress={showTooltip} onLongPress={showTooltip}>
                {children}
            </TouchableOpacity>
            {visible && (
                <Animated.View
                    style={[
                        tailwind('absolute bg-white p-2 rounded shadow-lg'),
                        {
                            transform: [
                                {
                                    translateY: tooltipRef.current.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: [position.y + 20, position.y + 20],
                                    }),
                                },
                                {
                                    translateX: tooltipRef.current.interpolate({
                                        inputRange: [0, 1],
                                        outputRange: [position.x, position.x],
                                    }),
                                },
                            ],
                            opacity: tooltipRef.current,
                        },
                    ]}
                >
                    <Text style={tailwind('text-black')}>{content}</Text>
                </Animated.View>
            )}
        </View>
    );
};

export default Tooltip;

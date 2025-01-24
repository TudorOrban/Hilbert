import React from 'react';
import { View, ActivityIndicator } from 'react-native';
import { useTailwind } from 'tailwind-rn';

const LoadingFallback = () => {
    const tailwind = useTailwind();

    return (
        <View style={tailwind("flex-1 justify-center items-center h-full")}>
            <ActivityIndicator size="large" color="#0000ff" />
        </View>
    );
}

export default LoadingFallback;
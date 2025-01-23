import React from 'react';
import {GestureHandlerRootView} from 'react-native-gesture-handler';
import AppNavigator from './src/navigation/AppNavigator';
import {Providers} from './src/app/core/main/providers/Providers';

const App = () => {
    return (
        <GestureHandlerRootView style={{flex: 1}}>
            <Providers>
                <AppNavigator />
            </Providers>
        </GestureHandlerRootView>
    );
};

export default App;

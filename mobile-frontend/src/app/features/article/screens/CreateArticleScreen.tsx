import { StackNavigationProp } from "@react-navigation/stack";
import { Controller, useForm } from "react-hook-form";
import { Button, Text, View } from "react-native";
import { ScrollView, TextInput } from "react-native-gesture-handler";
import { useTailwind } from "tailwind-rn";
import { Language } from "../../../shared/language/models/Language";
import { Picker } from "@react-native-picker/picker";
import { UITextService } from "../../../shared/common/utils/services/UITextService";
import { ArticleStatus, CreateArticleDto, DifficultyLevel } from "../models/Article";
import { ArticleService } from "../services/ArticleService";
import { useCurrentUser } from "../../../core/user/contexts/CurrentUserContext";

type CreateArticleScreenNavigationProp = StackNavigationProp<RootStackParamList, "CreateArticle">;

type Props = {
    navigation: CreateArticleScreenNavigationProp;
};

const CreateArticleScreen: React.FC<Props> = ({ navigation }) => {
    const tailwind = useTailwind();

    const { currentUser } = useCurrentUser();

    const {
        control,
        handleSubmit,
        formState: { errors }
    } = useForm<CreateArticleDto>({
        defaultValues: {
            title: "",
            description: "",
            content: "",
            language: Language.ENGLISH,
            level: DifficultyLevel.A1,
            status: ArticleStatus.PRIVATE,
        },
    });

    const onSubmit = async (data: CreateArticleDto) => {
        if (!currentUser?.id) {
            console.error("User not logged in");
            navigation.navigate("Login");
            return;
        }
        data.userId = currentUser.id;

        try {
            const response = await ArticleService.createArticle(data);
            navigation.navigate("Article", {
                itemId: response.id,
            });
        } catch (error) {
            console.error("An error occured trying to create the article: ", error);
            throw error;
        }
    }

    return (
        <ScrollView style={tailwind("p-4")}>

            <Text style={tailwind("font-semibold text-gray-800 mb-4")}>Title</Text>
            <Controller
                control={control}
                name="title"
                rules={{ required: "Title is required", minLength: 3, maxLength: 80 }}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                        style={tailwind("p-2 mb-4 border border-gray-300 rounded-md")}
                        onBlur={onBlur}
                        onChangeText={onChange}
                        value={value}
                    />
                )}
            />
            {errors.title && <Text style={tailwind("text-red-600 mb-4")}>{errors.title.message}</Text>}

            <Text style={tailwind("font-semibold text-gray-800 mb-4")}>Description</Text>
            <Controller
                control={control}
                name="description"
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                        style={tailwind("border border-gray-300 rounded-md p-2 mb-4")}
                        onBlur={onBlur}
                        onChangeText={onChange}
                        value={value}
                    />
                )}
            />
            {errors.description && <Text style={tailwind("text-red-600 mb-4")}>{errors.description.message}</Text>}

            <Text style={tailwind("font-semibold text-gray-800 mb-4")}>Language</Text>
            <Controller
                control={control}
                name="language"
                rules={{ required: "Language is required" }}
                render={({ field: { onChange, value } }) => (
                    <View style={tailwind("border border-gray-300 rounded-md mb-4")}>
                        <Picker
                            selectedValue={value}
                            onValueChange={onChange}
                        >
                            {Object.values(Language).map((lang) => (
                                <Picker.Item key={lang} label={UITextService.decapitalizeEnumString(lang)} value={lang} />
                            ))}
                        </Picker>
                    </View>
                )}
            />
            {errors.language && <Text style={tailwind("text-red-500 mb-4")}>{errors.language.message}</Text>}

            <Text style={tailwind("font-semibold text-gray-800 mb-4")}>Level</Text>
            <Controller
                control={control}
                name="level"
                rules={{ required: "Level is required" }}
                render={({ field: { onChange, value } }) => (
                    <View style={tailwind("border border-gray-300 rounded-md mb-4")}>
                        <Picker
                            selectedValue={value}
                            onValueChange={onChange}
                        >
                            {Object.values(DifficultyLevel).map((level) => (
                                <Picker.Item key={level} label={level.toString()} value={level} />
                            ))}
                        </Picker>
                    </View>
                )}
            />
            {errors.level && <Text style={tailwind("text-red-500 mb-4")}>{errors.level.message}</Text>}

            <Text style={tailwind("font-semibold text-gray-800 mb-4")}>Content</Text>
            <Controller
                control={control}
                name="content"
                rules={{ required: "Content is required" }}
                render={({ field: { onChange, onBlur, value } }) => (
                    <TextInput
                        style={tailwind("border border-gray-300 rounded-md p-2 mb-4")}
                        onBlur={onBlur}
                        onChangeText={onChange}
                        value={value}
                        multiline
                    />
                )}
            />
            {errors.content && <Text style={tailwind("text-red-600 mb-4")}>{errors.content.message}</Text>}

            
            <Text style={tailwind("font-semibold text-gray-800 mb-4")}>Status</Text>
            <Controller
                control={control}
                name="status"
                rules={{ required: "Status is required" }}
                render={({ field: { onChange, value } }) => (
                    <View style={tailwind("border border-gray-300 rounded-md mb-4")}>
                        <Picker
                            selectedValue={value}
                            onValueChange={onChange}
                        >
                            {Object.values(ArticleStatus).map((status) => (
                                <Picker.Item key={status} label={UITextService.decapitalizeEnumString(status)} value={status} />
                            ))}
                        </Picker>
                    </View>
                )}
            />
            {errors.status && <Text style={tailwind("text-red-500 mb-4")}>{errors.status.message}</Text>}

            <Button 
                title="Submit" 
                onPress={handleSubmit(onSubmit)}
            />
        </ScrollView>
    );
}

export default CreateArticleScreen;
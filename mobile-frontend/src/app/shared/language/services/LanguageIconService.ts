import { ImageSourcePropType } from "react-native"
import { Language } from "../models/Language"


export const LanguageIconService = {

    getIconByLanguage(language?: Language): ImageSourcePropType | undefined {
        if (!language) {
            return undefined;
        }
        
        // Manual switch as Image name in require has to be known statically
        switch (language) {
            case Language.ENGLISH:
                return require("../../../../assets/flags/gb.png");
            case Language.SPANISH:
                return require("../../../../assets/flags/es.png");
            case Language.FRENCH:
                return require("../../../../assets/flags/fr.png");
            case Language.GERMAN:
                return require("../../../../assets/flags/de.png");
            case Language.PORTUGUESE:
                return require("../../../../assets/flags/pt.png");
            case Language.ITALIAN:
                return require("../../../../assets/flags/it.png");
            case Language.JAPANESE:
                return require("../../../../assets/flags/jp.png");
            case Language.CHINESE:
                return require("../../../../assets/flags/cn.png");
            case Language.RUSSIAN:
                return require("../../../../assets/flags/ru.png");
                                                                
            default:
                return undefined;
        }
    }
}
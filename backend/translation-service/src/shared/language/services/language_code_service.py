
from src.shared.language.models.language import Language


class LanguageCodeService:
    def __init__(self):
        self.language_codes = {
            Language.ENGLISH: "en",
            Language.FRENCH: "fr",
            Language.SPANISH: "es",
            Language.GERMAN: "de",
            Language.PORTUGUESE: "pt",
            Language.ITALIAN: "it",
            Language.JAPANESE: "ja",
            Language.CHINESE: "zh",
            Language.RUSSIAN: "ru"
        }

    def get_language_code(self, language: Language):
        return self.language_codes.get(language, "en")
    
    def get_model_name(self, src_language: Language, dest_language: Language):
        return "Helsinki-NLP/opus-mt-" + self.get_language_code(src_language) + "-" + self.get_language_code(dest_language)

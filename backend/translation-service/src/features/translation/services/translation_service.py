from datetime import datetime, timedelta
import os
import re
from typing import Dict, List, Tuple
from transformers import AutoModelForSeq2SeqLM, AutoTokenizer

from src.features.translation.dtos.translation_dtos import TranslationRequestDto, TranslationResponseDto
from src.shared.language.models.language import Language
from src.shared.language.services.language_code_service import LanguageCodeService

class TranslationService:
    def __init__(self):
        self.language_code_service = LanguageCodeService()
        self.model = None
        self.tokenizer = None
        self.preload_models()
        self.cache: Dict[str, Tuple[List[str], str]] = {}
        self.cache_ttl = timedelta(hours=4)

    def translate(self, translation_request: TranslationRequestDto) -> TranslationResponseDto:
        self.load_model(translation_request.src_language, translation_request.dest_language)
        
        # Break text into unique words to be translated
        words = re.findall(r'\b\w+\b', translation_request.content)
        unique_words = list(set(words))

        translation_map: Dict[str, List[str]] = {}

        for word in unique_words:
            # Look in the cache
            cache_key = (translation_request.src_language, translation_request.dest_language, word)
            if cache_key in self.cache:
                cached_entry = self.cache[cache_key]
                if datetime.now() - cached_entry["timestamp"] < self.cache_ttl:
                    translation_map[word] = cached_entry["translation"]
                    continue

            # Run inference
            inputs = self.tokenizer(word, return_tensors="pt")
            outputs = self.model.generate(**inputs)
            translation = self.tokenizer.decode(outputs[0], skip_special_tokens=True)
            translation_map[word] = [translation]

            # Cache result
            self.cache[cache_key] = {
                "translation": [translation],
                "timestamp": datetime.now()
            }

        return TranslationResponseDto(translation_map, translation_request.src_language, translation_request.dest_language)


    """
    Function to preload the OpusMT models for all the supported language pairs
    Can be activated by creating a .env file in the root with PRELOAD_MODELS=True
    """
    def preload_models(self):
        preload_models_flag = os.getenv("PRELOAD_MODELS", "FALSE").lower() in ["true", "1", "t", "y", "yes"]
        if preload_models_flag == False:
            return
        
        for src_language in Language:
            for dest_language in Language:
                if src_language == Language.NONE | dest_language == Language.NONE | src_language == dest_language:
                    return
                self.load_model(src_language, dest_language)
    
    def load_model(self, src_language: Language, dest_language: Language):
        model_name = self.language_code_service.get_model_name(src_language, dest_language)
        self.model = AutoModelForSeq2SeqLM.from_pretrained(model_name)
        self.tokenizer = AutoTokenizer.from_pretrained(model_name)
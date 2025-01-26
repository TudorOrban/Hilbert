

from dataclasses import dataclass
from typing import Dict, List
from src.shared.language.models.language import Language


@dataclass
class TranslationRequestDto:
    content: str
    src_language: Language
    dest_language: Language

@dataclass
class TranslationResponseDto:
    translation: Dict[str, List[str]]
    src_language: Language
    dest_language: Language

    def to_dict(self):
        result = {}
        for key, value in self.__dict__.items():
            parts = key.split('_')
            camel_key = parts[0] + ''.join(part.capitalize() for part in parts[1:])
            result[camel_key] = value
        return result
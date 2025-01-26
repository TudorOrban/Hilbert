

from dataclasses import dataclass
from src.shared.language.models.language import Language


@dataclass
class TranslationRequestDto:
    content: str
    src_language: Language
    dest_language: Language

from dataclasses import dataclass
from typing import List

from src.features.translation.dtos.translation_dtos import TranslationResponseDto
from src.shared.language.models.language import Language

@dataclass
class MessageSearchDto:
    id: int
    is_user: bool
    content: str

@dataclass
class ChatInputDto:
    input_text: str
    previous_messages: List[MessageSearchDto]
    language: Language = None
    dest_language: Language = Language.ENGLISH
    should_translate: bool = False

@dataclass
class ChatOutputDto:
    response: str
    translation: TranslationResponseDto = None
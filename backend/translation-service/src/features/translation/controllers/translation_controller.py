from flask import Blueprint, request
from src.features.translation.dtos.translation_request_dto import TranslationRequestDto
from src.features.translation.services.translation_service import TranslationService
from src.shared.language.models.language import Language

translation_bp = Blueprint("translation", __name__)
translation_service = TranslationService();

@translation_bp.route('/translate', methods=['POST'])
def translate() -> tuple[str, int]:
    try:
        data = request.get_json() 
        dto = TranslationRequestDto(
            content=data.get("content", ""),
            src_language=Language[data.get("srcLanguage", "NONE")],
            dest_language=Language[data.get("destLanguage", "NONE")]
        )

        translation = translation_service.translate(dto)

        return translation, 200 
    except Exception as e: 
        return str(e), 500
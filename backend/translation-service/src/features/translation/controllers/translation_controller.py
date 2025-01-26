import json
from flask import Blueprint, jsonify, request
from src.shared.common.util.json_encoder import CustomJSONEncoder
from src.features.translation.dtos.translation_dtos import TranslationRequestDto, TranslationResponseDto
from src.features.translation.services.translation_service import TranslationService
from src.shared.language.models.language import Language

translation_bp = Blueprint("translation", __name__)
translation_service = TranslationService();

@translation_bp.route('/translate', methods=['POST'])
def translate() -> tuple[TranslationResponseDto, int]:
    try:
        data = request.get_json() 
        dto = TranslationRequestDto(
            content=data.get("content", ""),
            src_language=Language[data.get("srcLanguage", "NONE")],
            dest_language=Language[data.get("destLanguage", "NONE")]
        )

        response_dto = translation_service.translate(dto)
        response_json = json.dumps(response_dto.__dict__, cls=CustomJSONEncoder)
        return response_json, 200
    except Exception as e: 
        return str(e), 500
    

# Register Custom JSON Ecnoder
@translation_bp.after_request
def apply_caching(response):
    response.content_type = "application/json"
    response.data = json.dumps(json.loads(response.data), cls=CustomJSONEncoder)
    return response
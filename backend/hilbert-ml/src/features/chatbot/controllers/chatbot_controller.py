from typing import Any
from flask import Blueprint, Response, request, jsonify

from src.shared.language.models.language import Language
from src.features.chatbot.dto.chat_dtos import ChatInputDto, MessageSearchDto
from src.features.chatbot.services.chatbot_service import ChatbotService

chatbot_bp = Blueprint("chatbot", __name__)
chatbot_service = ChatbotService();

@chatbot_bp.route("/chatbot", methods=["POST"])
def chatbot() -> str:
    data = request.get_json()
    input_dto = get_chat_input_dto(data)

    response = chatbot_service.generate_response(input_dto)

    return jsonify(response)

@chatbot_bp.route("/chatbot/stream", methods=["POST"])
def chatbotstream() -> Response:
    data = request.get_json()
    input_dto = get_chat_input_dto(data)

    def generate():
        for chunk in chatbot_service.generate_response_stream(input_dto):
            yield f"data: {chunk}\n\n"

    return Response(generate(), mimetype="text/event-stream")


# Util
def get_chat_input_dto(data: Any) -> ChatInputDto:
    previous_messages = [MessageSearchDto(**filter_relevant_properties(msg)) for msg in data["messages"]]
    language_str = data.get("language", "NONE")
    language = Language[language_str] if language_str in Language.__members__ else Language.NONE

    return ChatInputDto(
        input_text=data["inputText"],
        previous_messages=previous_messages,
        language=language
    )

def filter_relevant_properties(msg: dict) -> dict:
    relevant_keys = {'id', 'isUser', 'content'}
    return {camel_to_snake(k): v for k, v in msg.items() if k in relevant_keys}

def camel_to_snake(name: str) -> str:
    import re
    return re.sub(r'(?<!^)(?=[A-Z])', '_', name).lower()

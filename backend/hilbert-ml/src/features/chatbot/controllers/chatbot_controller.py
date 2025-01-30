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

    print(f"Input text: {input_dto.input_text}")
    response = chatbot_service.generate_response(input_dto)
    print(f"Returning: {response}")

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
    previous_messages = [MessageSearchDto(**msg) for msg in data["previous_messages"]]
    language_str = data.get("language", "NONE")
    language = Language[language_str] if language_str in Language.__members__ else Language.NONE

    return ChatInputDto(
        input_text=data["input_text"],
        previous_messages=previous_messages,
        language=language
    )
from flask import Blueprint, request, jsonify

from src.features.chatbot.services.chatbot_service import generate_response

chatbot_bp = Blueprint("chatbot", __name__)

@chatbot_bp.route('/chatbot', methods=['POST'])
def chatbot() -> str:
    data = request.get_json()
    input_text = data.get("text")
    print(f"Input text: {input_text}")
    response = generate_response(input_text)
    print(f"Returning: {response}")
    return jsonify({"response": response})

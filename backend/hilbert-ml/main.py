import os
from dotenv import load_dotenv
from flask import Flask
from src.features.translation.controllers.translation_controller import translation_bp
from src.features.chatbot.controllers.chatbot_controller import chatbot_bp

load_dotenv()

API_VERSION = os.getenv("API_VERSION")
URL_PREFIX = "/api/v" + API_VERSION




app = Flask(__name__)

app.register_blueprint(translation_bp, url_prefix=URL_PREFIX)
app.register_blueprint(chatbot_bp, url_prefix=URL_PREFIX)
    
if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000, debug=True)

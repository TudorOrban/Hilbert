import os
from dotenv import load_dotenv
from flask import Flask
from src.features.translation.controllers.translation_controller import translation_bp

load_dotenv()

API_VERSION = os.getenv("API_VERSION")
URL_PREFIX = "/api/v" + API_VERSION

app = Flask(__name__)

app.register_blueprint(translation_bp, url_prefix=URL_PREFIX)
    
if __name__ == "__main__":
    app.run(debug=True)

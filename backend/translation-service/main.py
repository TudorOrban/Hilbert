from flask import Flask
from src.features.translation.controllers.translation_controller import translation_bp

app = Flask(__name__)

app.register_blueprint(translation_bp, url_prefix="/api")
    
if __name__ == "__main__":
    app.run(debug=True)

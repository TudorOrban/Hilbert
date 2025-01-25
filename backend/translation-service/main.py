from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/translate', methods=['POST'])
def translate() -> tuple[str, int]:
    try:
        data = request.get_json() 
        text: str = data.get('text', '') 

        translation: str = "Translated text"  

        return translation, 200 
    except Exception as e: 
        return str(e), 500
    
if __name__ == "__main__":
    app.run(debug=True)
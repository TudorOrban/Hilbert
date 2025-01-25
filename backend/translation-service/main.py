from flask import Flask, request, jsonify
from transformers import AutoModelForSeq2SeqLM, AutoTokenizer

app = Flask(__name__)

model_name = "Helsinki-NLP/opus-mt-fr-en"
model = AutoModelForSeq2SeqLM.from_pretrained(model_name)
tokenizer = AutoTokenizer.from_pretrained(model_name)


@app.route('/translate', methods=['POST'])
def translate() -> tuple[str, int]:
    try:
        data = request.get_json() 
        text: str = data.get('text', '') 

        inputs = tokenizer(text, return_tensors="pt")

        outputs = model.generate(**inputs)
        translation = tokenizer.decode(outputs[0], skip_special_tokens=True)

        return translation, 200 
    except Exception as e: 
        return str(e), 500
    
if __name__ == "__main__":
    app.run(debug=True)

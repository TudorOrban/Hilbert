from transformers import AutoModelForSeq2SeqLM, AutoTokenizer

from src.features.translation.dtos.translation_request_dto import TranslationRequestDto

class TranslationService:
    def __init__(self, model_name: str = "Helsinki-NLP/opus-mt-fr-en"):
        self.model = AutoModelForSeq2SeqLM.from_pretrained(model_name)
        self.tokenizer = AutoTokenizer.from_pretrained(model_name)

    def translate(self, translation_request: TranslationRequestDto) -> str:
        inputs = self.tokenizer(translation_request.content, return_tensors="pt")
        outputs = self.model.generate(**inputs)
        translation = self.tokenizer.decode(outputs[0], skip_special_tokens=True)
        return translation
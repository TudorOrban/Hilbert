import os
import re
from typing import Any, Generator
from ollama import Client

from src.features.chatbot.dto.chat_dtos import ChatInputDto, ChatOutputDto, MessageSearchDto
from src.shared.language.models.language import Language

class ChatbotService:
    RESPONSE_LENGTH: str = 10

    def __init__(self):
        self.ollama = Client()
        self.model_name = os.getenv("CHATBOT_MODEL_NAME")

    def generate_response(self, input_dto: ChatInputDto) -> ChatOutputDto:
        try:
            input_prompt = self.construct_input_prompt(input_dto)

            response = self.ollama.generate(model=self.model_name, prompt=input_prompt)

            response_text = self.extract_response(response['response'])

            return ChatOutputDto(
                response=response_text
            )
        except Exception as e:
            print(f"Error: {e}")
            return "Error generating response."

    def generate_response_stream(self, input_dto: ChatInputDto) -> Generator[str, None, None]:
        try:
            input_prompt = self.construct_input_prompt(input_dto)
            response_generator = self.ollama.generate(model=self.model_name, prompt=input_prompt, stream=True)

            for chunk in response_generator:
                yield self.extract_response(chunk["response"])
        except Exception as e:
            print(f"Error: {e}")
            yield "Error generating response."


    def construct_input_prompt(self, input_dto: ChatInputDto) -> str:
        language = input_dto.language or Language.GERMAN;
        input_prompt = f"Your purpose is to have a conversation with the User in {language.toString()} on whatever topic they like to discuss. \n"
        
        if input_dto.previous_messages != None:
            input_prompt += "Here is the conversation you've had so far: \n"
            for message in input_dto.previous_messages:
                if message.is_user:
                    input_prompt += f"--User: ${message.content} \n"
                else:
                    input_prompt += f"--You: ${message.content} \n"
            
        input_prompt += f"You now have to respond to the following User message: {input_dto.input_text} \n"
        input_prompt += f"Remember to respond only in {language.toString()}. "
        print(input_prompt)
        return input_prompt
    

    @staticmethod
    def extract_response(response_text: str) -> str:
        # Extract the text after the <think> element
        match = re.search(r'</think>(.*)$', response_text, re.DOTALL)
        if match:
            return match.group(1).strip()
        else:
            return response_text
        
    
        

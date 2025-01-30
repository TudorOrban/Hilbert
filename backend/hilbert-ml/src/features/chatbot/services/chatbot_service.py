from ollama import Client

def generate_response(input_text: str):
    try:
        ollama = Client()
        input = f"Your purpose is to have a conversation with the user in french on whatever they like to discuss. Respond in french. User input: {input_text}"
        response = ollama.generate(model="deepseek-r1:1.5b", prompt=input)
        return response['response']
    except Exception as e:
        print(f"Error: {e}")
        return "Error generating response."
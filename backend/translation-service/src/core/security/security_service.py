
import os
from flask import Request


class SecurityService:

    def can_access_translate_route(self, request: Request) -> bool:
        received_api_key = request.headers.get("API-Key")
        expected_api_key = os.getenv("API_KEY")

        return received_api_key == expected_api_key
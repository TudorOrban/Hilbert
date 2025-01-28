

import unittest
from unittest.mock import MagicMock, patch

from src.features.translation.dtos.translation_dtos import TranslationRequestDto
from src.features.translation.services.translation_service import TranslationService
from src.shared.language.models.language import Language


class TestTranslationService(unittest.TestCase):

    def setUp(self):
        self.service = TranslationService()
        self.service.model = MagicMock()
        self.service.tokenizer = MagicMock()

    @patch("src.features.translation.services.translation_service.TranslationService.load_model")
    def test_translate(self, mock_load_model):
        # Arrange
        self.service.tokenizer.return_value = MagicMock()
        self.service.model.generate.return_value = MagicMock()
        self.service.tokenizer.decode.side_effect = lambda x, **kwargs: "translated_word"

        translation_request = TranslationRequestDto(
            content="This is a test",
            src_language=Language.ENGLISH,
            dest_language=Language.FRENCH
        )

        # Act
        result = self.service.translate(translation_request)

        # Assert
        expected_translation_map = {
            "This": "translated_word",
            "is": "translated_word",
            "a": "translated_word",
            "test": "translated_word",
        }
        self.assertEqual(result.translation, expected_translation_map)
        self.assertEqual(result.src_language, Language.ENGLISH)
        self.assertEqual(result.dest_language, Language.FRENCH)


if __name__ == "__main__":
    unittest.main()
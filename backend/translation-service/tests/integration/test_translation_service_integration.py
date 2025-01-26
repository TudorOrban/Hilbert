import unittest

from src.features.translation.dtos.translation_dtos import TranslationRequestDto
from src.features.translation.services.translation_service import TranslationService
from src.shared.language.models.language import Language


class TestTranslationServiceIntegration(unittest.TestCase):

    def setUp(self):
        self.service = TranslationService()

    def test_translate_integration(self):
        # Arrange
        translation_request = TranslationRequestDto(
            content="happy love friend book dog",
            src_language=Language.ENGLISH,
            dest_language=Language.FRENCH
        )

        # Act
        result = self.service.translate(translation_request)

        # Assert
        self.assertIsInstance(result.translation, dict)
        self.assertEqual(result.src_language, Language.ENGLISH)
        self.assertEqual(result.dest_language, Language.FRENCH)

        self.assertEqual(result.translation["happy"], "heureux")
        self.assertEqual(result.translation["love"], "amour")
        self.assertEqual(result.translation["friend"], "ami")
        self.assertEqual(result.translation["book"], "livre")
        self.assertEqual(result.translation["dog"], "chien")

if __name__ == '__main__':
    unittest.main()
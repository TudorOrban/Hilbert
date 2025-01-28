
import os
import time
import unittest

from src.features.translation.dtos.translation_dtos import TranslationRequestDto
from src.features.translation.services.translation_service import TranslationService
from src.shared.language.models.language import Language

"""
Class to test how the translate function scales with time. Results (without caching):
- 10 words: 8.36 s
- 50 words: 13.23 s
- 150 words: 28.59 s
- 300 words: 58.44 s
The results show a superlinear growth that requires future investigation.
"""
class TestTranslationServicePerformance(unittest.TestCase):

    def setUp(self):
        self.service = TranslationService()

    @unittest.skipIf(os.getenv('RUN_PERFORMANCE_TESTS') is None, "Skipping performance test")
    def test_translate_performance(self):
        tests = {
            10: "Science is a systematic discipline that builds and organises knowledge",
            50: "Science is a systematic discipline that builds and organises knowledge in the form of testable hypotheses and predictions about the universe. Modern science is typically divided into two or three major branches:[3] the natural sciences (e.g., physics, chemistry, and biology), which study the physical world; and the behavioural sciences",
            150: "Science is a systematic discipline that builds and organises knowledge in the form of testable hypotheses and predictions about the universe. Modern science is typically divided into two or three major branches:[3] the natural sciences (e.g., physics, chemistry, and biology), which study the physical world; and the behavioural sciences (e.g., economics, psychology, and sociology), which study individuals and societies.[4][5] The formal sciences (e.g., logic, mathematics, and theoretical computer science), which study formal systems governed by axioms and rules,[6][7] are sometimes described as being sciences as well; however, they are often regarded as a separate field because they rely on deductive reasoning instead of the scientific method or empirical evidence as their main methodology.[8][9] Applied sciences are disciplines that use scientific knowledge for practical purposes, such as engineering and medicine. The history of science spans the majority of the historical record, with the earliest identifiable predecessors to modern science dating",
            300: "Science is a systematic discipline that builds and organises knowledge in the form of testable hypotheses and predictions about the universe. Modern science is typically divided into two or three major branches:[3] the natural sciences (e.g., physics, chemistry, and biology), which study the physical world; and the behavioural sciences (e.g., economics, psychology, and sociology), which study individuals and societies.[4][5] The formal sciences (e.g., logic, mathematics, and theoretical computer science), which study formal systems governed by axioms and rules,[6][7] are sometimes described as being sciences as well; however, they are often regarded as a separate field because they rely on deductive reasoning instead of the scientific method or empirical evidence as their main methodology.[8][9] Applied sciences are disciplines that use scientific knowledge for practical purposes, such as engineering and medicine. The history of science spans the majority of the historical record, with the earliest identifiable predecessors to modern science dating to the Bronze Age in Egypt and Mesopotamia (c. 3000–1200 BCE). Their contributions to mathematics, astronomy, and medicine entered and shaped the Greek natural philosophy of classical antiquity, whereby formal attempts were made to provide explanations of events in the physical world based on natural causes, while further advancements, including the introduction of the Hindu–Arabic numeral system, were made during the Golden Age of India. Scientific research deteriorated in these regions after the fall of the Western Roman Empire during the Early Middle Ages (400–1000 CE), but in the Medieval renaissances (Carolingian Renaissance, Ottonian Renaissance and the Renaissance of the 12th century) scholarship flourished again. Some Greek manuscripts lost in Western Europe were preserved and expanded upon in the Middle East during the Islamic Golden Age, along with the later efforts of Byzantine Greek scholars who brought Greek manuscripts from the dying Byzantine Empire to Western Europe at the start"
        }

        for word_count, content in tests.items():
            translation_request = TranslationRequestDto(
                content=content,
                src_language=Language.ENGLISH,
                dest_language=Language.FRENCH
            )

            start_time = time.time()
            result = self.service.translate(translation_request)
            end_time = time.time()

            elapsed_time = end_time - start_time
            print(f"Time taken to translate {word_count} words: {elapsed_time} seconds")

            self.assertIsInstance(result.translation, dict)

if __name__ == "__main__":
    unittest.main()
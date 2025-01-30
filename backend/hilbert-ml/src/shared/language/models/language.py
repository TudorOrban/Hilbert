from enum import Enum

class Language(Enum):
    NONE = "NONE"
    ENGLISH = "ENGLISH"
    SPANISH = "SPANISH"
    FRENCH = "FRENCH"
    GERMAN = "GERMAN"
    PORTUGUESE = "PORTUGUESE"
    ITALIAN = "ITALIAN"
    JAPANESE = "JAPANESE"
    CHINESE = "CHINESE"
    RUSSIAN = "RUSSIAN"

    def toString(self):
        match self:
            case self.NONE:
                return "None"
            case self.ENGLISH:
                return "English"
            case self.SPANISH:
                return "Spanish"
            case self.FRENCH:
                return "French"
            case self.GERMAN:
                return "German"
            case self.PORTUGUESE:
                return "Portuguese"
            case self.ITALIAN:
                return "Italian"
            case self.JAPANESE:
                return "Japanese"
            case self.CHINESE:
                return "Chinese"
            case self.RUSSIAN:
                return "Russian"

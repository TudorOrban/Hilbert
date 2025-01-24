
export const UITextService = {
    decapitalizeEnumString(enumString?: string): string | undefined {
        if (!enumString) {
            return undefined;
        }
        return enumString.charAt(0) + String(enumString).slice(1).toLowerCase();
    }
}
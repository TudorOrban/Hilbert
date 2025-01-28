package com.hilbert.shared.sanitization.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SanitizationServiceTest {

    private SanitizationServiceImpl sanitizationService;

    @BeforeEach
    public void setUp() {
        sanitizationService = new SanitizationServiceImpl();
    }

    @Test
    public void whenInputIsNull_thenSanitizeShouldReturnNull() {
        String result = sanitizationService.sanitize(null);
        assertNull(result, "sanitize should return null when input is null");
    }

    @Test
    public void whenInputContainsAllowedTags_thenSanitizeShouldReturnString() {
        String input = "<b>bold</b> <i>italic</i> <u>underline</u> <strong>strong</strong> <em>emphasis</em> <sub>subscript</sub> <sup>superscript</sup>";
        String expected = "<b>bold</b> <i>italic</i> <u>underline</u> <strong>strong</strong> <em>emphasis</em> <sub>subscript</sub> <sup>superscript</sup>";
        String result = sanitizationService.sanitize(input);
        assertEquals(expected, result, "sanitize should not strip down allowed tags");
    }

    @Test
    public void whenInputContainsDisallowedTags_thenSanitizeShouldReturnSanitizedString() {
        String input = "<script>alert('xss')</script> <div>div content</div>";
        String expected = "div content";
        String result = sanitizationService.sanitize(input);
        assertEquals(expected, result, "sanitize should remove disallowed tags");
    }

    @Test
    public void whenInputContainsAllowedAttributes_thenSanitizeShouldReturnSanitizedString() {
        String input = "<a href=\"http://example.com\">link</a>";
        String expected = "<a href=\"http://example.com\">link</a>";
        String result = sanitizationService.sanitize(input);
        assertEquals(expected, result, "sanitize should retain allowed attributes");
    }

    @Test
    public void whenInputContainsDisallowedAttributes_thenSanitizeShouldReturnSanitizedString() {
        String input = "<a href=\"http://example.com\" onclick=\"alert('xss')\">link</a>";
        String expected = "<a href=\"http://example.com\">link</a>";
        String result = sanitizationService.sanitize(input);
        assertEquals(expected, result, "sanitize should remove disallowed attributes");
    }
}

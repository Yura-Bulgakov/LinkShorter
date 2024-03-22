package org.example.linkshorter.util;

import org.example.linkshorter.dto.TokenCreationDto;
import org.example.linkshorter.entity.LongLink;
import org.example.linkshorter.repository.LongLinkRepository;
import org.example.linkshorter.repository.ShortLinkRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LinkValidatorTest {
    @Mock
    private ShortLinkRepository shortLinkRepository;
    @Mock
    private LongLinkRepository longLinkRepository;
    @InjectMocks
    private LinkValidator linkValidator;

    @Test
    void testValidateValidLink() {
        String validLink = "http://yury.com";
        assertTrue(linkValidator.isValid(validLink));
    }

    @Test
    void testValidateHttpsLink() {
        String validLink = "https://yury.com";
        assertTrue(linkValidator.isValid(validLink));
    }

    @Test
    void testValidateWWWLink() {
        String validLink = "https://www.baeldung.com/java-validate-url";
        assertTrue(linkValidator.isValid(validLink));
    }

    @Test
    void testValidateLinkWithSubdomain() {
        String validLink = "https://sub.yury.com";
        assertTrue(linkValidator.isValid(validLink));
    }

    @Test
    void testValidateLinkWithMultiSubdomain() {
        String validLink = "https://subsubsub.subsub.sub.yury.com";
        assertTrue(linkValidator.isValid(validLink));
    }

    @Test
    void testValidateLinkWithPath() {
        String validLink = "http://yury.com/admin/stat";
        assertTrue(linkValidator.isValid(validLink));
    }

    @Test
    void testValidateInvalidLink() {
        String invalidLink = "invalid_link";
        assertFalse(linkValidator.isValid(invalidLink));
    }

    @Test
    void testValidateNullLink() {
        String nullLink = null;
        assertFalse(linkValidator.isValid(nullLink));
    }

    @Test
    void testValidateEmptyLink() {
        String emptyLink = "";
        assertFalse(linkValidator.isValid(emptyLink));
    }

    @Test
    void testValidateLinkWithInvalidSymbols() {
        String linkWithInvalidCharacters = "http://example!@#$%^&*().com";
        assertFalse(linkValidator.isValid(linkWithInvalidCharacters));
    }

    @ParameterizedTest
    @CsvSource({
            "http://www.test.com, www.test.com",
            "https://sub.test.com/path/page, sub.test.com",
            "ftp://ftp.test.com/file.txt, ftp.test.com",
            "www.test.com, null",
            "invalid, null",
            "http://www.test.com:8080, www.test.com",
            "https://www.test.com/path/page?param1=value1&param2=value2, www.test.com",
            "http://192.168.1.1, 192.168.1.1",
            "https://sub1.sub2.test.com, sub1.sub2.test.com",
            ", null",
            "null, null"
    })
    void testGetDomain(String url, String expectedDomain) {
        if (expectedDomain.equals("null")) {
            assertNull(linkValidator.getDomain(url));
        } else {
            assertEquals(expectedDomain, linkValidator.getDomain(url));
        }
    }

    @Test
    public void testValidateWithInvalidToken() {
        TokenCreationDto dto = new TokenCreationDto();
        dto.setToken("invalid-token!");
        Errors errors = new BeanPropertyBindingResult(dto, "tokenCreationDto");

        linkValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Токен содержит недопустимые символы! Допустимы латинские заглавные и прописные буквы и цифры 0-9",
                errors.getFieldError("token").getDefaultMessage());
    }

    @Test
    public void testValidateWithDuplicateToken() {
        TokenCreationDto dto = new TokenCreationDto();
        dto.setToken("validtoken");

        when(shortLinkRepository.existsByToken("validtoken")).thenReturn(true);
        Errors errors = new BeanPropertyBindingResult(dto, "tokenCreationDto");

        linkValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Токен уже существует!", errors.getFieldError("token").getDefaultMessage());
    }

    @Test
    public void testValidateWithForbiddenUrl() {
        String url = "http://test.ru";
        LongLink link = new LongLink(url);
        link.setForbidden(true);
        TokenCreationDto dto = new TokenCreationDto();
        dto.setUrl(url);

        when(longLinkRepository.findByLongLink(url)).thenReturn(Optional.of(link));
        Errors errors = new BeanPropertyBindingResult(dto, "tokenCreationDto");

        linkValidator.validate(dto, errors);

        assertEquals(1, errors.getErrorCount());
        assertEquals("Ссылка заблокирована!", errors.getFieldError("url").getDefaultMessage());
    }


}
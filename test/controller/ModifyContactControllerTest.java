package controller;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModifyContactControllerTest {

    private ModifyContactController controller;

    @BeforeEach
    void setUp() {
        controller = new ModifyContactController();
    }

    @Test
    void testIsEmailValid_ValidEmails() {
        assertTrue(controller.isEmailValid("test@example.com"));
        assertTrue(controller.isEmailValid("user.name+tag+sorting@example.com"));
        assertTrue(controller.isEmailValid("my.email@subdomain.example.org"));
    }

    @Test
    void testIsEmailValid_InvalidEmails() {
        assertFalse(controller.isEmailValid("invalid-email"));
        assertFalse(controller.isEmailValid("user@com"));
        assertFalse(controller.isEmailValid("user@domain,com"));
        assertFalse(controller.isEmailValid("user@domain..com"));
    }
}
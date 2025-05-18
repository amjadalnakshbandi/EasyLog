package user.valueObject;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    void constructorTest_ValidEmail() throws IOException {
        // Arrange
        String validEmail = "test@example.com";

        // Act
        Email email = new Email(validEmail);

        // Assert
        assertNotNull(email);
        assertEquals(validEmail, email.getEmail());
    }

    @Test
    void constructorTest_InvalidEmail() {
        // Arrange
        String invalidEmail = "invalid_email";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new Email(invalidEmail));
        assertEquals("Invalid email format", exception.getMessage());
    }

    @Test
    void constructorTest_NullEmail() {
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new Email(null));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }

    @Test
    void constructorTest_BlankEmail() {
        // Arrange
        String blankEmail = "   ";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new Email(blankEmail));
        assertEquals("Email cannot be null or empty", exception.getMessage());
    }
}

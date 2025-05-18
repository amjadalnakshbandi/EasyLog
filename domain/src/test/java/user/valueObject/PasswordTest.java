package user.valueObject;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    @Test
    void constructorTest_ValidPassword() throws IOException {
        // Arrange
        String validPassword = "StrongPass123!";

        // Act
        Password password = new Password(validPassword);

        // Assert
        assertNotNull(password);
        assertEquals(validPassword, password.getPassword());
    }

    @Test
    void constructorTest_InvalidPassword() {
        // Arrange
        String invalidPassword = "123"; // zu kurz oder ohne Sonderzeichen etc., je nach Regel

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new Password(invalidPassword));
        assertEquals("Password does not meet the required format", exception.getMessage());
    }

    @Test
    void constructorTest_NullPassword() {
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new Password(null));
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }

    @Test
    void constructorTest_BlankPassword() {
        // Arrange
        String blankPassword = "   ";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new Password(blankPassword));
        assertEquals("Password cannot be null or empty", exception.getMessage());
    }
}

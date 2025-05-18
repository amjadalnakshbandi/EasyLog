package user.valueObject;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FirstNameTest {

    @Test
    void constructorTest_ValidFirstName() throws IOException {
        // Arrange
        String validFirstName = "John";

        // Act
        FirstName firstName = new FirstName(validFirstName);

        // Assert
        assertNotNull(firstName);
        assertEquals(validFirstName, firstName.getFirstName());
    }

    @Test
    void constructorTest_InvalidFirstName() {
        // Arrange
        String invalidFirstName = "J0hn@123";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new FirstName(invalidFirstName));
        assertEquals("First name must only contain letters, apostrophes, or hyphens, and be 1–50 characters long", exception.getMessage());
    }

    @Test
    void constructorTest_NullFirstName() {
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new FirstName(null));
        assertEquals("First name cannot be null or empty", exception.getMessage());
    }

    @Test
    void constructorTest_BlankFirstName() {
        // Arrange
        String blankFirstName = "   ";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new FirstName(blankFirstName));
        assertEquals("First name cannot be null or empty", exception.getMessage());
    }
}

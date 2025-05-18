package user.valueObject;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class LastNameTest {

    @Test
    void constructorTest_ValidLastName() throws IOException {
        // Arrange
        String validLastName = "Smith";

        // Act
        LastName lastName = new LastName(validLastName);

        // Assert
        assertNotNull(lastName);
        assertEquals(validLastName, lastName.getLastname());
    }

    @Test
    void constructorTest_InvalidLastName() {
        // Arrange
        String invalidLastName = "Sm1th@123";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new LastName(invalidLastName));
        assertEquals("Last name must only contain letters, apostrophes, or hyphens, and be 1–50 characters long", exception.getMessage());
    }

    @Test
    void constructorTest_NullLastName() {
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new LastName(null));
        assertEquals("Last name cannot be null or empty", exception.getMessage());
    }

    @Test
    void constructorTest_BlankLastName() {
        // Arrange
        String blankLastName = "   ";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new LastName(blankLastName));
        assertEquals("Last name cannot be null or empty", exception.getMessage());
    }
}

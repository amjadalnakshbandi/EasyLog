package order.valueobject;

import order.valueObject.PhoneName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNameTest {

    @Test
    void constructorTest_ValidPhoneName() throws IOException {
        // Arrange
        String validPhoneName = "Samsung Galaxy S24";

        // Act
        PhoneName phoneName = new PhoneName(validPhoneName);

        // Assert
        assertNotNull(phoneName);
        assertEquals(validPhoneName, phoneName.getName());
    }

    @Test
    void constructorTest_NullPhoneName() {
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new PhoneName(null));
        assertEquals("Phone name cannot be null or empty", exception.getMessage());
    }

    @Test
    void constructorTest_BlankPhoneName() {
        // Arrange
        String blankPhoneName = "   ";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new PhoneName(blankPhoneName));
        assertEquals("Phone name cannot be null or empty", exception.getMessage());
    }
}

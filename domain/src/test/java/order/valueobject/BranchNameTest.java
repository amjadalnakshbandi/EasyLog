package order.valueobject;

import order.valueObject.BranchName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class BranchNameTest {

    @Test
    void constructorTest_ValidBranchName() throws IOException {
        // Arrange
        String validBranchName = "Downtown Store";

        // Act
        BranchName branchName = new BranchName(validBranchName);

        // Assert
        assertNotNull(branchName);
        assertEquals(validBranchName, branchName.getName());
    }

    @Test
    void constructorTest_NullBranchName() {
        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new BranchName(null));
        assertEquals("Branch name cannot be null or empty", exception.getMessage());
    }

    @Test
    void constructorTest_BlankBranchName() {
        // Arrange
        String blankBranchName = "   ";

        // Act & Assert
        IOException exception = assertThrows(IOException.class, () -> new BranchName(blankBranchName));
        assertEquals("Branch name cannot be null or empty", exception.getMessage());
    }
}

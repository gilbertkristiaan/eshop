package id.ac.ui.cs.advprog.eshop;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class EshopApplicationTests {

    @Test
    void contextLoads() {
        // This test ensures that the Spring application context initializes correctly.
        // Assertions are unnecessary since any failure in loading the context will cause the test to fail.
    }

    @Test
    void main() {
        assertDoesNotThrow(() -> EshopApplication.main(new String[] {}));
    }
}

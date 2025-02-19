package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    Product product;

    @BeforeEach
    void setUp(){
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId());
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName());
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity());
    }

    @Test
    void testUpdateProductId() {
        String newId = "new-id-456";
        this.product.setProductId(newId);
        assertEquals(newId, this.product.getProductId());
    }

    @Test
    void testUpdateProductName() {
        String nowName = "CM Punk";
        this.product.setProductName(nowName);
        assertEquals(nowName, this.product.getProductName());
    }

    @Test
    void testUpdateProductQuantity() {
        int nowQuantity = 200;
        this.product.setProductQuantity(nowQuantity);
        assertEquals(nowQuantity, this.product.getProductQuantity());
    }
}
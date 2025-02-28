package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {

        Iterator<Product> productIterator = productRepository.findAll();
        while (productIterator.hasNext()) {
            Product product = productIterator.next();
            productRepository.delete(product.getProductId());
        }
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(123);
        Product result = productRepository.create(product);

        assertNotNull(result);
        assertNotNull(result.getProductId());
        assertEquals(product.getProductName(), result.getProductName());
        assertEquals(product.getProductQuantity(), result.getProductQuantity());

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(savedProduct.getProductId(), product.getProductId());
        assertEquals(savedProduct.getProductName(), product.getProductName());
        assertEquals(savedProduct.getProductQuantity(), product.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductName("Sampo Cap A");
        product1.setProductQuantity(123);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Sampo Cap B");
        product2.setProductQuantity(234);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product firstSavedProduct = productIterator.next();
        assertEquals(product1.getProductName(), firstSavedProduct.getProductName());

        assertTrue(productIterator.hasNext());
        Product secondSavedProduct = productIterator.next();
        assertEquals(product2.getProductName(), secondSavedProduct.getProductName());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductName("Product Name");
        product.setProductQuantity(123);
        Product savedProduct = productRepository.create(product);
        String productId = savedProduct.getProductId();

        Product updatedProduct = new Product();
        updatedProduct.setProductId(productId);
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(productId, updatedProduct);
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(200, result.getProductQuantity());

        Product foundProduct = productRepository.findById(productId);
        assertNotNull(foundProduct);
        assertEquals("New Name", foundProduct.getProductName());
        assertEquals(200, foundProduct.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product invalidProduct = new Product();
        invalidProduct.setProductId("non-existent-id");
        invalidProduct.setProductName("New Product");
        invalidProduct.setProductQuantity(100);

        Product result = productRepository.update("non-existent-id", invalidProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductName("Product to Delete");
        product.setProductQuantity(100);
        Product savedProduct = productRepository.create(product);
        String productId = savedProduct.getProductId();

        productRepository.delete(productId);

        Product foundProduct = productRepository.findById(productId);
        assertNull(foundProduct);
    }

    @Test
    void testDeleteProductNotFound() {
        productRepository.delete("non-existent-id");

    }

    @Test
    void testDeleteProductWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        Product savedProduct1 = productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        Product savedProduct2 = productRepository.create(product2);

        productRepository.delete(savedProduct1.getProductId());

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product remainingProduct = productIterator.next();
        assertEquals(savedProduct2.getProductId(), remainingProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductName("Test Find By ID");
        product.setProductQuantity(123);
        Product savedProduct = productRepository.create(product);

        Product result = productRepository.findById(savedProduct.getProductId());
        assertNotNull(result);
        assertEquals(savedProduct.getProductName(), result.getProductName());
        assertEquals(savedProduct.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testFindByIdNotFound() {
        Product result = productRepository.findById("non-existent-id");
        assertNull(result);
    }

    @Test
    void testFindByIdWithNullId() {
        Product result = productRepository.findById(null);
        assertNull(result);
    }

    @Test
    void testDeleteWithNullId() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        try {
            productRepository.delete(null);

            Iterator<Product> products = productRepository.findAll();
            assertTrue(products.hasNext());
        } catch (Exception e) {

        }
    }

    @Test
    void testProductValidation() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);
        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getProductId());
        assertEquals("Test Product", savedProduct.getProductName());
        assertEquals(10, savedProduct.getProductQuantity());
    }

    @Test
    void testFindByIdWithEmptyId() {
        Product result = productRepository.findById("");
        assertNull(result);
    }

    @Test
    void testUpdateWithNullId() {
        Product product = new Product();
        product.setProductName("Old Product");
        product.setProductQuantity(100);
        Product savedProduct = productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update(null, updatedProduct);
        assertNull(result);
    }

    @Test
    void testUpdateWithEmptyId() {
        Product product = new Product();
        product.setProductName("Old Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductName("Updated Product");
        updatedProduct.setProductQuantity(200);

        Product result = productRepository.update("", updatedProduct);
        assertNull(result);
    }

    @Test
    void testUpdateWithNullProduct() {
        Product product = new Product();
        product.setProductName("Old Product");
        product.setProductQuantity(100);
        Product savedProduct = productRepository.create(product);

        Product result = productRepository.update(savedProduct.getProductId(), null);
        assertNull(result);
    }

    @Test
    void testFindByIdWhenProductIdIsNull() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product result = productRepository.findById("random-id");
        assertNull(result);
    }

    @Test
    void testFindByIdWithProductNullId() {
        Product product = new Product();
        product.setProductName("Product With Null ID");
        product.setProductQuantity(100);

        Product createdProduct = productRepository.create(product);
        createdProduct.setProductId(null);
        Product foundProduct = productRepository.findById("any-id");
        assertNull(foundProduct);
        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());
    }
}
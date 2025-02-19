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

    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("id1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(123);
        Product result = productRepository.create(product);
        assertNotNull(result);
        assertEquals(product, result);
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
        product1.setProductId("id1");
        product1.setProductName("Sampo Cap A");
        product1.setProductQuantity(123);
        productRepository.create(product1);
        Product product2 = new Product();
        product2.setProductId("id2");
        product2.setProductName("Sampo Cap B");
        product2.setProductQuantity(234);
        productRepository.create(product2);
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProductSuccess() {
        Product product = new Product();
        product.setProductId("new-id");
        product.setProductName("Product Name");
        product.setProductQuantity(123);
        productRepository.create(product);
        Product updatedProduct = new Product();
        updatedProduct.setProductId("new-id");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(200);
        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(200, result.getProductQuantity());
    }

    @Test
    void testEditProductPartialUpdate() {
        Product product = new Product();
        product.setProductId("new-id");
        product.setProductName("Product Name");
        product.setProductQuantity(123);
        productRepository.create(product);
        Product partialUpdate = new Product();
        partialUpdate.setProductId("new-id");
        partialUpdate.setProductName("New Name");
        partialUpdate.setProductQuantity(70);
        Product result = productRepository.update(partialUpdate);
        assertNotNull(result);
        assertEquals("New Name", result.getProductName());
        assertEquals(70, result.getProductQuantity());
    }

    @Test
    void testEditProductNotFound() {
        Product invalidProduct = new Product();
        invalidProduct.setProductId("hello-world-id");
        invalidProduct.setProductName("abcdefgk");
        invalidProduct.setProductQuantity(1);
        Product checkProd = productRepository.update(invalidProduct);
        assertNull(checkProd);
    }

    @Test
    void testDeleteProductSuccess() {
        Product product = new Product();
        product.setProductId("id-1");
        product.setProductName("New Product");
        product.setProductQuantity(100);
        productRepository.create(product);
        productRepository.delete(product.getProductId());
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductNotFound() {
        productRepository.delete("not-found-id");
        assertTrue(true);
    }

    @Test
    void testDeleteProductWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        product1.setProductName("Product 1");
        product1.setProductQuantity(100);
        productRepository.create(product1);
        Product product2 = new Product();

        product2.setProductId("id-2");
        product2.setProductName("Product 2");
        product2.setProductQuantity(200);
        productRepository.create(product2);
        productRepository.delete("id-1");

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product remainingProduct = productIterator.next();
        assertEquals("id-2", remainingProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testSimulateFindById() {
        Product product = new Product();
        product.setProductId("id-1");
        product.setProductName("New Product");
        product.setProductQuantity(123);
        productRepository.create(product);

        Product result = null;
        Iterator<Product> products = productRepository.findAll();
        while (products.hasNext()) {
            Product current = products.next();
            if ("id-1".equals(current.getProductId())) {
                result = current;
                break;
            }
        }
        assertNotNull(result);
        assertEquals(product.getProductName(), result.getProductName());
        assertEquals(product.getProductQuantity(), result.getProductQuantity());
    }

    @Test
    void testFindByIdReturnsNull() {
        boolean found = false;
        Iterator<Product> products = productRepository.findAll();
        while (products.hasNext()) {
            Product current = products.next();
            if ("gk-id".equals(current.getProductId())) {
                found = true;
                break;
            }
        }
        assertFalse(found);
    }

    @Test
    void testCreateNullProductId() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("No ID Product");
        product.setProductQuantity(30);
        Product result = productRepository.create(product);
        assertNotNull(result);
        assertNull(result.getProductId());
        assertEquals("No ID Product", result.getProductName());
        assertEquals(30, result.getProductQuantity());
    }

    @Test
    void testUpdateNullProductId() {
        Product product = new Product();
        product.setProductId("x-id");
        product.setProductName("New Product");
        productRepository.create(product);
        Product updateProduct = new Product();
        updateProduct.setProductId(null);
        updateProduct.setProductName("X Name");
        Product result = productRepository.update(updateProduct);
        Iterator<Product> products = productRepository.findAll();
        Product foundProduct = products.next();
        assertEquals("x-id", foundProduct.getProductId());
        assertEquals("New Product", foundProduct.getProductName());
    }

    @Test
    void testDeleteWithNullId() {
        Product product = new Product();
        product.setProductId("x-id");
        productRepository.create(product);

        try {
            productRepository.delete(null);

            assertTrue(true);
        } catch (NullPointerException e) {

            assertTrue(true);
        }

        Iterator<Product> products = productRepository.findAll();
        assertTrue(products.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("id-100");
        product.setProductName("Check Product");
        product.setProductQuantity(123);
        productRepository.create(product);
        Product result = productRepository.findById("id-100");
        assertNotNull(result);
        assertEquals("Check Product", result.getProductName());
        assertEquals(123, result.getProductQuantity());
    }

    @Test
    void testFindByIdNotFound() {
        Product result = productRepository.findById("kj-id");
        assertNull(result);
    }

    @Test
    void testFindByIdWithNullId() {
        Product result = productRepository.findById(null);
        assertNull(result);
    }

    @Test
    void testFindByIdWithNullProductId() {
        Product product = new Product();
        product.setProductId(null);
        product.setProductName("No ID Prod");
        productRepository.create(product);
        Product result = productRepository.findById("cand-id");
        assertNull(result);
    }

    @Test
    void testFindByIdProductIdNotEquals() {
        Product product = new Product();
        product.setProductId("pd-1");
        product.setProductName("CM Product");
        product.setProductQuantity(356);
        productRepository.create(product);
        Product result = productRepository.findById("pdd-2");
        assertNull(result);
    }
}
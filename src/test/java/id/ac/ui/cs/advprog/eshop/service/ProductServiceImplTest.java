package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setProductId("1");
        sampleProduct.setProductName("My Product");
        sampleProduct.setProductQuantity(10);
    }

    @Test
    void testSaveNewProduct() {
        when(productRepository.create(sampleProduct)).thenReturn(sampleProduct);
        Product createdProduct = productService.create(sampleProduct);
        assertNotNull(createdProduct);
        assertEquals("My Product", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
        verify(productRepository, times(1)).create(sampleProduct);
    }

    @Test
    void testRetrieveAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(sampleProduct);
        when(productRepository.findAll()).thenReturn(productList.iterator());
        List<Product> retrievedProducts = productService.findAll();
        assertNotNull(retrievedProducts);
        assertEquals(1, retrievedProducts.size());
        assertEquals("My Product", retrievedProducts.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveAllProductsWhenEmpty() {
        List<Product> emptyList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(emptyList.iterator());
        List<Product> retrievedProducts = productService.findAll();
        assertNotNull(retrievedProducts);
        assertTrue(retrievedProducts.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductByValidId() {
        when(productRepository.findById("1")).thenReturn(sampleProduct);
        Product foundProduct = productService.findById("1");
        assertNotNull(foundProduct);
        assertEquals("My Product", foundProduct.getProductName());
        assertEquals(10, foundProduct.getProductQuantity());
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testFindProductByInvalidId() {
        when(productRepository.findById("999")).thenReturn(null);
        Product foundProduct = productService.findById("999");
        assertNull(foundProduct);
        verify(productRepository, times(1)).findById("999");
    }

    @Test
    void testUpdateExistingProduct() {
        when(productRepository.update(sampleProduct)).thenReturn(sampleProduct);
        Product updatedProduct = productService.update(sampleProduct.getProductId(), sampleProduct);
        assertNotNull(updatedProduct);
        assertEquals("My Product", updatedProduct.getProductName());
        assertEquals(10, updatedProduct.getProductQuantity());
        verify(productRepository, times(1)).update(sampleProduct);
    }

    @Test
    void testUpdateNonExistentProduct() {
        when(productRepository.update(sampleProduct)).thenReturn(null);
        Product updatedProduct = productService.update(sampleProduct.getProductId(), sampleProduct);
        assertNull(updatedProduct);
        verify(productRepository, times(1)).update(sampleProduct);
    }

    @Test
    void testDeleteExistingProduct() {
        doNothing().when(productRepository).delete("1");
        assertDoesNotThrow(() -> productService.delete("1"));
        verify(productRepository, times(1)).delete("1");
    }

    @Test
    void testDeleteNonExistentProduct() {
        doNothing().when(productRepository).delete("123");
        assertDoesNotThrow(() -> productService.delete("123"));
        verify(productRepository, times(1)).delete("123");
    }
}
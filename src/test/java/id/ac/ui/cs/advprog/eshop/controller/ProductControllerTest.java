package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testDisplayProductCreationForm() {
        String viewName = productController.createProductPage(model);
        verify(model).addAttribute(eq("product"), any(Product.class));
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testSubmitNewProductWithValidData() {
        Product product = new Product();
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = productController.createProductPost(product, bindingResult, model);
        verify(productService).create(product);
        assertEquals("redirect:list", viewName);
    }

    @Test
    void testSubmitNewProductWithInvalidData() {
        Product product = new Product();
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = productController.createProductPost(product, bindingResult, model);
        verify(productService, never()).create(any(Product.class));
        assertEquals("CreateProduct", viewName);
    }

    @Test
    void testDisplayAllProducts() {
        List<Product> products = Arrays.asList(new Product(), new Product());
        when(productService.findAll()).thenReturn(products);
        String viewName = productController.productListPage(model);
        verify(model).addAttribute("products", products);
        assertEquals("productList", viewName);
    }

    @Test
    void testDisplayEditFormForExistingProduct() {
        String productId = "abcID";
        Product product = new Product();
        when(productService.findById(productId)).thenReturn(product);
        String viewName = productController.editProductPage(productId, model);
        verify(model).addAttribute("product", product);
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testDisplayEditFormForNonExistingProduct() {
        String productId = "whatKindOfId";
        when(productService.findById(productId)).thenReturn(null);
        String viewName = productController.editProductPage(productId, model);
        verify(model, never()).addAttribute(eq("product"), any());
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testUpdateExistingProductWithValidData() {
        String productId = "abcID";
        Product product = new Product();
        when(bindingResult.hasErrors()).thenReturn(false);
        String viewName = productController.editProductPost(productId, product, bindingResult);
        verify(productService).update(productId, product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void testUpdateExistingProductWithInvalidData() {
        String productId = "abcID";
        Product product = new Product();
        when(bindingResult.hasErrors()).thenReturn(true);
        String viewName = productController.editProductPost(productId, product, bindingResult);
        verify(productService, never()).update(any(String.class), any(Product.class));
        assertEquals("EditProduct", viewName);
    }

    @Test
    void testRemoveExistingProduct() {
        String productId = "abcID";
        String viewName = productController.deleteProduct(productId);
        verify(productService).delete(productId);
        assertEquals("redirect:/product/list", viewName);
    }
}

## Name: Gilbert Kristian

## Class: Adpro-A

## Student Number: 2306274951

___
# Reflection 1: Coding Standards and Secure Coding
You already implemented two new features using Spring Boot. Check again your source code and evaluate the coding standards that you have learned in this module. Write clean code principles and secure coding practices that have been applied to your code. If you find any mistake in your source code, please explain how to improve your code

---
## Coding Standards 
### 1. Meaningful Names

I already used meaningful names and coding standards in my code by ensuring that all variables, methods, and class names are clear and descriptive. My code is clean, readable, and maintainable without needing unnecessary comments.

```Java
public Product update(Product newProduct) {
for (int i = 0; i < productData.size(); i++) {
    if (productData.get(i).getProductId().equals(newProduct.getProductId())) {
        productData.set(i, newProduct);
        return newProduct;
    }
}
return null;
}
```

### 2. Functions

I already ensured that each function will be short, well named, and nicely organized, making the code more modular and maintainable.
```Java
public void delete(String productId) {
    productRepository.delete(productId);
}
```

### 3. Comments
I don't include any comments in my code because I've tried to explain my code clearly through meaningful names and structure.

### 4. Object and Data Structures
I keep the variables private to enforce encapsulation, a core principle of Object-Oriented Programming (OOP).
```Java
private String productId;
private String productName;
private int productQuantity;
```

### 5. Error Handling
I’ve implemented error handling to check if the product doesn’t exist, and it will redirect to the product list page. This keeps the code clean and robust.
```Java
@GetMapping("/edit/{productId}")
public String editProductPage(@PathVariable ("productId") String productId, Model model) {
    Product product = service.findById(productId);
    if (product == null) {
        return "redirect:/product/list";
    }
    model.addAttribute("product", product);
    return "EditProduct";
}
```

---

## Secure Coding

### 1. Input Data Validation
I've implemented validation in the `Product` class by using `@NotBlank` to ensure that the product name is provided and `@Min` to enforce a minimum value of 1 for the product quantity. (Add the dependencies in build.gradle)
```Java
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

@Getter @Setter
public class Product {
    private String productId;
    
    @NotBlank(message = "Nama produk harus ada")
    private String productName;
    
    @Min(value = 1, message = "Minimal diisi satu")
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }
}
```

### 2. Output Data Encoding
I implemented Output Data Encoding by using #strings.escapeXml() to ensure that error messages are properly escaped to prevent security vulnerabilities, particularly those related to injection attacks, like XSS attacks.
```html
<div class="form-group">
  <label for="nameInput">Name</label>
  <input th:field="*{productName}" type="text" class="form-control mb-4 col-4" id="nameInput" placeholder="Enter product name">
  <small class="text-danger" th:if="${#fields.hasErrors('productName')}" th:text="${#strings.escapeXml(#fields.errors('productName'))}"></small>    </div>
<div class="form-group">
  <label for="quantityInput">Quantity</label>
  <input th:field="*{productQuantity}" type="text" class="form-control mb-4 col-4" id="quantityInput" placeholder="Enter product quantity">
  <small class="text-danger" th:if="${#fields.hasErrors('productQuantity')}" th:text="${#strings.escapeXml(#fields.errors('productQuantity'))}"></small>
</div>
```

---
# Reflection 2: 


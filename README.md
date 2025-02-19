## Name: Gilbert Kristian - Adpro-A - 2306274951
___

# Module 1 - Coding Standards and Secure Coding

## Reflection 1: Coding Standards and Secure Coding
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
I implemented Output Data Encoding by using `#strings.escapeXml()` to ensure that error messages are properly escaped to prevent security vulnerabilities, particularly those related to injection attacks, like XSS attacks.
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
# Reflection 2: Unit Testing

1. After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors?
   <br><br>
   - After writing unit tests, I saw the code being tested behaves as expected in a variety of situations. The goal is to ensure that the code is robust, resistant to edge cases, and behaves correctly for common scenarios.
      <br><br>
   - The number of unit tests in a class depends on the complexity of the class and its responsibilities.
   <br><br>
   - To ensure unit tests effectively verify a program, cover all public methods, edge cases, and error handling scenarios while aiming for high code coverage. Use techniques like test-driven development (TDD) and mutation testing to assess test robustness. Analyze coverage reports to identify untested branches and add missing tests. A well-tested program reliably exercises all critical functions, ensuring failures provide clear debugging insights.
   <br><br>
   - 100% code coverage doesn't guarantee that your code is free of bugs or errors. Code coverage only ensures that all lines of code have been executed during testing, but it does verify that the code behaves correctly in all scenarios. Tests may still miss edge cases, logical errors, or unexpected interactions between components. Additionally, coverage metrics don't account for the quality or completeness of assertions, which means a test could execute code without actually validating its correctness. Therefore, reaching 100% coverage is helpful, but to guarantee reliability, it needs be paired with strong test and real-world validation.
   <br><br>
2. Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables. What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!
   <br><br>
   - Creating a new functional test suite with similar setup procedures and instance variables as the existing CreateProductFunctionalTest.java could introduce redundancy, reduce code cleanliness, and make maintenance more difficult. Duplicating setup logic, such as initializing baseUrl and configuring WebDriver, across multiple test classes violates the DRY (Don't Repeat Yourself) principle, increasing the risk of inconsistencies if changes are required in the future. While the new test suite would follow a familiar structure, excessive duplication can clutter the codebase, making it harder to read, manage, and scale. Maintaining multiple test classes with nearly identical configurations can also reduce overall code clarity, making it more challenging to implement future modifications efficiently.
   <br><br>
   - Suggested Improvements:
     1. Extract Common Setup into a Base Class
     2. Encapsulate Web Element Interactions in Helper Methods
     3. Use Constants for URLs and Element Identifiers
     4. Improve Assertions for Better Debugging
     5. Reduce Explicit Waits with Utility Methods

# Module 2 - CI/CD & DevOps

## Reflection

1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
   <br><br>
    During the exercise, I fixed several code quality issues, including removing unused imports . My strategy for fixing them involved using a linter to identify formatting inconsistencies, refactoring redundant code to improve readability, and optimizing loops to enhance performance. Additionally, I ensured that all functions followed best practices for modularity and maintainability.
    <br><br>
2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!




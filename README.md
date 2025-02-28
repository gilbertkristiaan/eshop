## Gilbert Kristian - Adpro A - 2306274951

- [Module 1](#Module-1)
- [Module 2](#Module-2)
- [Module 3](#Module-3)

---
# Module-1

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
   - 100% code coverage doesn't guarantee that your code is free of bugs or errors. Code coverage only ensures that all lines of code have been executed during testing, but it does verify that the code behaves correctly in all scenarios. Tests may still miss edge cases, logical errors, or unexpected interactions between components. Also, coverage metrics don't account for the quality or completeness of assertions, which means a test could execute code without actually validating its correctness. Therefore, reaching 100% coverage is helpful, but to guarantee reliability, it needs be paired with strong test and real-world validation.
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

# Module-2

## Reflection: CI/CD and DevOps

1. List the code quality issue(s) that you fixed during the exercise and explain your strategy on fixing them.
   <br><br>
   During the exercise, I fixed several code quality issues, including removing unused imports, making all the method names follow Java naming convention, and adding comments into empty method body. My strategy involved using a static code analyzer to detect and remove unnecessary imports which helped improve code readability and maintainability. Also, I standardized function naming by following the camelCase convention to ensure consistency across the codebase. I also reviewed the code for redundant and changed few code logic to improve the performance.    <br><br>
2. Look at your CI/CD workflows (GitHub)/pipelines (GitLab). Do you think the current implementation has met the definition of Continuous Integration and Continuous Deployment? Explain the reasons (minimum 3 sentences)!
   <br><br>
   The current CI/CD implementation aligns with the definition of Continuous Integration (CI) and Continuous Deployment (CD). Through utilizing GitHub Actions for workflow automation inside .github/workflows, CI is effectively implemented as workflows are automatically triggered on each push or pull request to a branch. Continuous Deployment (CD) is achieved through Koyeb, which ensures that the project is automatically deployed whenever there is a push or pull request. Koyeb also integrates seamlessly with GitHub, enabling a smooth deployment process.

## Code Coverage Screenshot
![image](https://github.com/user-attachments/assets/cd39a79a-5149-49df-941c-b1f9c648ad27)

# Module-3

## Reflection : Maintainability & OO Principles

1) Explain what principles you apply to your project!
    <br>
    ## **1) Single Responsibility Principle (SRP)**
    I created three different classes for `Controller`. `HomePageController` has the responsibility to perform mapping with endpoints `/`, `ProductController` has the responsibility to perform mapping with endpoints `/product`, and `CarController` has the responsibility to perform mapping with endpoints `/car`. I also removed UUID generation from constructor in Product class and moved ID generation into the ProductRepository

    ## **2) Open/Closed Principle (OCP)**
    I've implemented `BaseRepository<T, ID>` as a generic interface for repository operations, ensuring that CarRepository and ProductRepository follow the Open/Closed Principle (OCP). This allows for easy extension of repository functionality without modifying existing code, promoting better maintainability and scalability.

    ## **3) Liskov Substitution Principle (LSP)**
    `CarController` was designed as a subclass of `ProductController`, despite having different responsibilities. This inheritance structure was problematic because `CarController` didn't truly extend the behavior of `ProductController`, making substitution unreliable. I removed the inheritance relationship and restructured `CarController` as an independent class in its own file, ensuring more clean separation of concerns and better application to object-oriented principles.

    ## **4) Interface Segregation Principle (ISP)**
    The service interfaces (ProductService, CarService) follow ISP well by providing specific methods for each entity. These classes operate independently without unnecessary dependencies and already implemented CRUD (Create, Read, Update, Delete).

    ## **5) Dependency Inversion Principle (DIP)**
    `CarController` has a direct dependency on CarServiceImpl, which is not ideal since CarController should rely on the CarService interface instead. So, I updated the data type of the `carServiceImpl` variable in `CarController` to CarService, ensuring better abstraction and flexibility.
<br><br>
2) Explain the advantages of applying SOLID principles to your project with examples.
   <br><br>
   1. **Improved Maintainability**:
       - Each class has a single responsibility, making the code easier to understand and modify.
       - Example: Changes in how cars are stored, only affect `CarRepository`, not `CarServiceImpl` or `CarController`.
   
   2. **Enhanced Testability**:
       - Dependencies on interfaces can be easily mocked for unit testing.
       - Example: `CarServiceImpl` can be tested with a mock `CarRepository` without needing real data storage.

   3. **Easier Extension**:
       - New functionality can be added without modifying existing code.
       - Example: If a new entity, such as `Bike` is introduced, a corresponding `BikeRepository` can be created by implementing `BaseRepository<Bike, String>`, ensuring consistency without modifying `CarRepository` or `ProductRepository`.

   4. **Better Code Organization**:
       - Clear separation of concerns leads to more organized codebase.
       - Example: Car-related functionality is separated from Product-related functionality.
<br><br>
3) Explain the disadvantages of not applying SOLID principles to your project with examples.
   <br><br>
   1. **Rigidity**:
       - Without interfaces, modifying repository implementations would require changes across multiple classes.
        - Example: If `CarRepository` and `ProductRepository` had no common interface, adding a new repository method would require changes in both classes separately, leading to code duplication and higher maintenance effort.

   2. **Fragility**:
       - Unstructured dependencies cause unexpected side effects when making changes.
       - Example: In non-SOLID code, changing how `CarRepository` works could break `CarService` in unexpected ways

   3. **Reduced Testability**:
       - Hard dependencies on concrete classes make unit testing difficult.
       - Example: Without `BaseRepository<T, ID>`, `CarServiceImpl` would require an actual `CarRepository` instance in tests, making unit testing slower and dependent on real data.

   4. **Code Duplication**:
       - Without proper abstractions, similar code may be duplicated.
       - Example: If repository interfaces are not used, CRUD operations in `CarRepository` and `ProductRepository` would need to be written separately, leading to redundant code.

   5. **Maintenance Challenges**:
       - Monolithic classes with multiple responsibilities are harder to understand and maintain
       - Example: If `CarRepository` also handled business logic, it would be harder to modify without breaking other parts of the application. By following SOLID principles, business logic remains in services, while repositories only handle data access.
    
    


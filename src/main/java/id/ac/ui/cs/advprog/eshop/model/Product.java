package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

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
    }
}

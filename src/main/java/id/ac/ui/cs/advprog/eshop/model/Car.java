package id.ac.ui.cs.advprog.eshop.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Min;


@Getter @Setter
public class Car {
    private String carId;

    @NotBlank(message = "Nama mobil tidak boleh kosong")
    private String carName;

    @NotBlank(message = "Warna mobil tidak boleh kosong")
    private String carColor;

    @Min(value = 1, message = "Kuantitas mobil tidak boleh kurang dari 1!")
    private int carQuantity;
}

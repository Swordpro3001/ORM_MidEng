package com.example.orm.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="product_id")  // Explicitly map to the correct column name
    private String productID;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_quantity")
    @NotNull(message = "Product quantity cannot be null")
    private int productQuantity;

    @Column(name = "product_unit")
    @Size(min = 1, max = 55)
    private String productUnit;
}
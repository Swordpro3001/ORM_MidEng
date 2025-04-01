package com.example.orm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    private String productID;

    private String productName;
    private String productCategory;
    private int productQuantity;
    private String productUnit;
}
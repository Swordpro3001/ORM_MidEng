package com.example.orm.dto;
import com.example.orm.model.Product;
import lombok.Data;

import java.util.List;

@Data
public class WarehouseWithProductsDTO {
    private String warehouseName;
    private String warehouseAddress;
    private String warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;
    private List<Product> products;
}

package com.example.orm.controller;


import com.example.orm.dto.WarehouseWithProductIdsDTO;
import com.example.orm.dto.WarehouseWithProductsDTO;
import com.example.orm.model.DataWarehouse;
import com.example.orm.service.DataWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.orm.model.Product;
@RestController
@RequestMapping("/warehouses")
@RequiredArgsConstructor
public class DataWarehouseController {

    private final DataWarehouseService service;

    @GetMapping
    public List<DataWarehouse> getAllWarehouses() {
        return service.getAllWarehouses();
    }

    @GetMapping("/{id}")
    public DataWarehouse getWarehouseById(@PathVariable Long id) {
        return service.getWarehouseById(id);
    }

    @PutMapping("/{id}")
    public DataWarehouse updateWarehouse(@PathVariable Long id, @RequestBody DataWarehouse warehouse) {
        return service.updateWarehouse(id, warehouse);
    }

    @PostMapping("/with-products")
    public DataWarehouse createWarehouseWithExistingProducts(@RequestBody WarehouseWithProductIdsDTO request) {
        return service.createWarehouseWithExistingProducts(request);
    }
    
    @PostMapping("/with-new-products")
    public DataWarehouse createWarehouseWithNewProducts(@RequestBody WarehouseWithProductsDTO request) {
        return service.createWarehouseWithNewProducts(request);
    }


    @PostMapping("/{warehouseId}/add-products")
    public DataWarehouse addProductsToWarehouse(@PathVariable Long warehouseId, @RequestBody List<String> productIds) {
        return service.addProductsToWarehouse(warehouseId, productIds);
    }

    // Extended DataWarehouseController.java - Add this endpoint
    @GetMapping("/{warehouseId}/products/{productId}")
    public Product getProductFromWarehouse(@PathVariable Long warehouseId, @PathVariable String productId) {
        return service.getProductFromWarehouse(warehouseId, productId);
    }
}
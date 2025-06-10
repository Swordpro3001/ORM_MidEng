package com.example.orm.controller;

import com.example.orm.dto.WarehouseWithProductIdsDTO;
import com.example.orm.dto.WarehouseWithProductsDTO;
import com.example.orm.model.DataWarehouse;
import com.example.orm.model.Product;
import com.example.orm.service.DataWarehouseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*") // Für Frontend-Testing
public class DataWarehouseController {

    private final DataWarehouseService service;

    @GetMapping
    public ResponseEntity<List<DataWarehouse>> getAllWarehouses() {
        log.info("GET /api/warehouses - Getting all warehouses");
        try {
            List<DataWarehouse> warehouses = service.getAllWarehouses();
            log.info("Found {} warehouses", warehouses.size());
            return ResponseEntity.ok(warehouses);
        } catch (Exception e) {
            log.error("Error getting all warehouses: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataWarehouse> getWarehouseById(@PathVariable Long id) {
        log.info("GET /api/warehouses/{} - Getting warehouse by ID", id);
        try {
            DataWarehouse warehouse = service.getWarehouseById(id);
            return ResponseEntity.ok(warehouse);
        } catch (RuntimeException e) {
            log.error("Warehouse with ID {} not found", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting warehouse by ID: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<DataWarehouse> createWarehouse(@RequestBody DataWarehouse warehouse) {
        log.info("POST /api/warehouses - Creating new warehouse: {}", warehouse.getWarehouseName());
        try {
            DataWarehouse savedWarehouse = service.createWarehouse(warehouse);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedWarehouse);
        } catch (Exception e) {
            log.error("Error creating warehouse: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataWarehouse> updateWarehouse(@PathVariable Long id, @RequestBody DataWarehouse warehouse) {
        log.info("PUT /api/warehouses/{} - Updating warehouse", id);
        try {
            DataWarehouse updatedWarehouse = service.updateWarehouse(id, warehouse);
            return ResponseEntity.ok(updatedWarehouse);
        } catch (RuntimeException e) {
            log.error("Warehouse with ID {} not found for update", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating warehouse: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWarehouse(@PathVariable Long id) {
        log.info("DELETE /api/warehouses/{} - Deleting warehouse", id);
        try {
            service.deleteWarehouse(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Warehouse with ID {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting warehouse: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/with-products")
    public ResponseEntity<DataWarehouse> createWarehouseWithExistingProducts(@RequestBody WarehouseWithProductIdsDTO request) {
        log.info("POST /api/warehouses/with-products - Creating warehouse with existing products");
        try {
            DataWarehouse warehouse = service.createWarehouseWithExistingProducts(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(warehouse);
        } catch (Exception e) {
            log.error("Error creating warehouse with products: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/with-new-products")
    public ResponseEntity<DataWarehouse> createWarehouseWithNewProducts(@RequestBody WarehouseWithProductsDTO request) {
        log.info("POST /api/warehouses/with-new-products - Creating warehouse with new products");
        try {
            DataWarehouse warehouse = service.createWarehouseWithNewProducts(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(warehouse);
        } catch (Exception e) {
            log.error("Error creating warehouse with new products: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/{warehouseId}/add-products")
    public ResponseEntity<DataWarehouse> addProductsToWarehouse(@PathVariable Long warehouseId, @RequestBody List<String> productIds) {
        log.info("POST /api/warehouses/{}/add-products - Adding products to warehouse", warehouseId);
        try {
            DataWarehouse warehouse = service.addProductsToWarehouse(warehouseId, productIds);
            return ResponseEntity.ok(warehouse);
        } catch (Exception e) {
            log.error("Error adding products to warehouse: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Neue Endpunkte
    @GetMapping("/{warehouseId}/products/{productId}")
    public ResponseEntity<Product> getProductFromWarehouse(@PathVariable Long warehouseId, @PathVariable String productId) {
        log.info("GET /api/warehouses/{}/products/{} - Getting specific product from warehouse", warehouseId, productId);
        try {
            Product product = service.getProductFromWarehouse(warehouseId, productId);
            return ResponseEntity.ok(product);
        } catch (RuntimeException e) {
            log.error("Product {} not found in warehouse {}", productId, warehouseId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error getting product from warehouse: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{warehouseId}/products")
    public ResponseEntity<List<Product>> getProductsFromWarehouse(@PathVariable Long warehouseId) {
        log.info("GET /api/warehouses/{}/products - Getting all products from warehouse", warehouseId);
        try {
            List<Product> products = service.getProductsFromWarehouse(warehouseId);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Error getting products from warehouse: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

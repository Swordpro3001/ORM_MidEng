package com.example.orm.service;


import com.example.orm.dto.WarehouseWithProductIdsDTO;
import com.example.orm.dto.WarehouseWithProductsDTO;
import com.example.orm.model.DataWarehouse;
import com.example.orm.model.Product;
import com.example.orm.repository.DataWarehouseRepository;
import com.example.orm.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DataWarehouseService {

    private final DataWarehouseRepository repository;
    private final ProductRepository productRepository;

    public List<DataWarehouse> getAllWarehouses() {
        return repository.findAll();
    }

    public DataWarehouse getWarehouseById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));
    }

    public DataWarehouse updateWarehouse(Long id, DataWarehouse updatedWarehouse) {
        Optional<DataWarehouse> warehouseOpt = repository.findById(id);
        if (warehouseOpt.isPresent()) {
            DataWarehouse warehouse = warehouseOpt.get();
            warehouse.setWarehouseName(updatedWarehouse.getWarehouseName());
            warehouse.setWarehouseAddress(updatedWarehouse.getWarehouseAddress());
            warehouse.setWarehousePostalCode(updatedWarehouse.getWarehousePostalCode());
            warehouse.setWarehouseCity(updatedWarehouse.getWarehouseCity());
            warehouse.setWarehouseCountry(updatedWarehouse.getWarehouseCountry());
            return repository.save(warehouse);
        } else {
            throw new RuntimeException("Warehouse not found");
        }
    }

    public DataWarehouse createWarehouseWithExistingProducts(WarehouseWithProductIdsDTO request) {
        List<Product> products = productRepository.findAllById(request.getProductIds());
        DataWarehouse warehouse = new DataWarehouse();
        warehouse.setWarehouseName(request.getWarehouseName());
        warehouse.setWarehouseAddress(request.getWarehouseAddress());
        warehouse.setWarehousePostalCode(request.getWarehousePostalCode());
        warehouse.setWarehouseCity(request.getWarehouseCity());
        warehouse.setWarehouseCountry(request.getWarehouseCountry());
        warehouse.setProducts(products);
        return repository.save(warehouse);
    }

    public DataWarehouse createWarehouseWithNewProducts(WarehouseWithProductsDTO request) {
        List<Product> savedProducts = request.getProducts().stream()
                .map(productRepository::save)
                .collect(Collectors.toList());

        DataWarehouse warehouse = new DataWarehouse();
        warehouse.setWarehouseName(request.getWarehouseName());
        warehouse.setWarehouseAddress(request.getWarehouseAddress());
        warehouse.setWarehousePostalCode(request.getWarehousePostalCode());
        warehouse.setWarehouseCity(request.getWarehouseCity());
        warehouse.setWarehouseCountry(request.getWarehouseCountry());
        warehouse.setProducts(savedProducts);
        return repository.save(warehouse);
    }

    public DataWarehouse addProductsToWarehouse(Long warehouseId, List<String> productIds) {
        DataWarehouse warehouse = repository.findById(warehouseId)
                .orElseThrow(() -> new RuntimeException("Warehouse not found"));

        List<Product> products = productRepository.findAllById(productIds);
        warehouse.getProducts().addAll(products);

        return repository.save(warehouse);
    }
}
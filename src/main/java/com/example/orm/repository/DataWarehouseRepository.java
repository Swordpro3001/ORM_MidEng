package com.example.orm.repository;

import com.example.orm.model.DataWarehouse;
import com.example.orm.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DataWarehouseRepository extends JpaRepository<DataWarehouse, Long> {

    // Custom query to find a specific product in a specific warehouse
    @Query("SELECT p FROM DataWarehouse w JOIN w.products p WHERE w.warehouseID = :warehouseId AND p.productID = :productId")
    Optional<Product> findProductInWarehouse(@Param("warehouseId") Long warehouseId, @Param("productId") String productId);
}
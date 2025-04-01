package com.example.orm.repository;

import com.example.orm.model.DataWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataWarehouseRepository extends JpaRepository<DataWarehouse, Long> {
}

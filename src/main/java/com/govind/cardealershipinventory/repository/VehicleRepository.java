package com.govind.cardealershipinventory.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.govind.cardealershipinventory.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByMakeContainingIgnoreCase(String make);

    List<Vehicle> findByModelContainingIgnoreCase(String model);

    List<Vehicle> findByCategoryContainingIgnoreCase(String category);

    List<Vehicle> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
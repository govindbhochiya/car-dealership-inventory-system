package com.govind.cardealershipinventory.repository;

import java.math.BigDecimal;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.govind.cardealershipinventory.entity.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByMakeContainingIgnoreCase(String make);

    List<Vehicle> findByModelContainingIgnoreCase(String model);

    List<Vehicle> findByCategoryContainingIgnoreCase(String category);

    List<Vehicle> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

	@Nullable
	Object searchVehicles(Object object, String string, Object object2, Object object3, Object object4);
}
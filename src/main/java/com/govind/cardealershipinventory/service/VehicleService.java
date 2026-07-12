package com.govind.cardealershipinventory.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.govind.cardealershipinventory.entity.Vehicle;
import com.govind.cardealershipinventory.repository.VehicleRepository;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public Vehicle addVehicle(Vehicle vehicle) {

        validateMake(vehicle.getMake());
        validateModel(vehicle.getModel());
        validateCategory(vehicle.getCategory());
        validatePrice(vehicle.getPrice());
        validateQuantity(vehicle.getQuantity());
        return vehicleRepository.save(vehicle);
    }

    private void validateMake(String make) {
        if (make == null || make.trim().isEmpty()) {
            throw new RuntimeException("Make is required");
        }
    }

    private void validateModel(String model) {
        if (model == null || model.trim().isEmpty()) {
            throw new RuntimeException("Model is required");
        }
    }
    private void validateCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new RuntimeException("Category is required");
        }
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.signum() <= 0) {
            throw new RuntimeException("Price must be greater than zero");
        }
    }

    private void validateQuantity(Integer quantity) {
        if (quantity == null) {
            throw new RuntimeException("Quantity is required");
        }

        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
    }

	public List<Vehicle> getAllVehicles() {
		// TODO Auto-generated method stub
		return null;
	}
}
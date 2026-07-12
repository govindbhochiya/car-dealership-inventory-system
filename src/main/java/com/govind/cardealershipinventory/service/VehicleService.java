package com.govind.cardealershipinventory.service;

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
        if (vehicle.getCategory() == null || vehicle.getCategory().trim().isEmpty()) {
            throw new RuntimeException("Category is required");
        }

        if (vehicle.getPrice() == null || vehicle.getPrice().signum() <= 0) {
            throw new RuntimeException("Price must be greater than zero");
        }

        if (vehicle.getQuantity() == null) {
            throw new RuntimeException("Quantity is required");
        }

        if (vehicle.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
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
}
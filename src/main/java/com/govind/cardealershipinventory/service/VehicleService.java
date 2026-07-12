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
        return vehicleRepository.findAll();
    }

    public List<Vehicle> searchVehicles(
            String make,
            String model,
            String category,
            BigDecimal minPrice,
            BigDecimal maxPrice) {

        validatePriceRange(minPrice, maxPrice);

        return vehicleRepository.searchVehicles(
                make,
                model,
                category,
                minPrice,
                maxPrice);
    }

    private void validatePriceRange(BigDecimal minPrice, BigDecimal maxPrice) {

        if (minPrice != null
                && maxPrice != null
                && minPrice.compareTo(maxPrice) > 0) {

            throw new RuntimeException(
                    "Minimum price cannot be greater than maximum price");
        }
    }

    public Vehicle updateVehicle(Long id, Vehicle updatedVehicle) {

        Vehicle existingVehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        validateMake(updatedVehicle.getMake());
        validateModel(updatedVehicle.getModel());
        validateCategory(updatedVehicle.getCategory());
        validatePrice(updatedVehicle.getPrice());
        validateQuantity(updatedVehicle.getQuantity());
        existingVehicle.setMake(updatedVehicle.getMake());
        existingVehicle.setModel(updatedVehicle.getModel());
        existingVehicle.setCategory(updatedVehicle.getCategory());
        existingVehicle.setPrice(updatedVehicle.getPrice());
        existingVehicle.setQuantity(updatedVehicle.getQuantity());

        return vehicleRepository.save(existingVehicle);
    }
    //helper
    private void validateVehicleExists(Long id) {
        if (!vehicleRepository.existsById(id)) {
            throw new RuntimeException("Vehicle not found");
        }
    }

    public void deleteVehicle(Long id) {
        validateVehicleExists(id);
        vehicleRepository.deleteById(id);
    }

    public Vehicle purchaseVehicle(Long id) {

        Vehicle vehicle = getVehicleById(id);

        validateVehicleInStock(vehicle);

        vehicle.setQuantity(vehicle.getQuantity() - 1);

        return vehicleRepository.save(vehicle);
    }

    private Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    private void validateVehicleInStock(Vehicle vehicle) {
        if (vehicle.getQuantity() <= 0) {
            throw new RuntimeException("Vehicle is out of stock");
        }
    }

	public Vehicle restockVehicle(long l, int i) {
		// TODO Auto-generated method stub
		return null;
	}
}
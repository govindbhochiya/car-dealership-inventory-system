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

        if (vehicle.getMake() == null || vehicle.getMake().trim().isEmpty()) {
            throw new RuntimeException("Make is required");
        }

        if (vehicle.getModel() == null || vehicle.getModel().trim().isEmpty()) {
            throw new RuntimeException("Model is required");
        }

        return vehicleRepository.save(vehicle);
    }
}
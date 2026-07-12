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
        return null;
    }
}
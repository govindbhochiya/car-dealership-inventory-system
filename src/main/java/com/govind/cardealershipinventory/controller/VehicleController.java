package com.govind.cardealershipinventory.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.govind.cardealershipinventory.entity.Vehicle;
import com.govind.cardealershipinventory.service.VehicleService;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Vehicle> addVehicle(@RequestBody Vehicle vehicle) {

        Vehicle savedVehicle = vehicleService.addVehicle(vehicle);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedVehicle);
    }
}
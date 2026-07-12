package com.govind.cardealershipinventory.controller;

import java.math.BigDecimal;
import java.util.List;

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
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(vehicleService.getAllVehicles());
    }
    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>> searchVehicles(
            @RequestParam(required = false) String make,
            @RequestParam(required = false) String model,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice) {

        List<Vehicle> vehicles = vehicleService.searchVehicles(
                make,
                model,
                category,
                minPrice,
                maxPrice);

        return ResponseEntity.ok(vehicles);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(
            @PathVariable Long id,
            @RequestBody Vehicle updatedVehicle) {

        Vehicle vehicle = vehicleService.updateVehicle(id, updatedVehicle);

        return ResponseEntity.ok(vehicle);
    }
}
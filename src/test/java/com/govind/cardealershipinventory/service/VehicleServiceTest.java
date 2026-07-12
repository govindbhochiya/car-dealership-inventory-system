package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.govind.cardealershipinventory.entity.Vehicle;
import com.govind.cardealershipinventory.repository.VehicleRepository;

class VehicleServiceTest {

    VehicleRepository vehicleRepository = Mockito.mock(VehicleRepository.class);

    private final VehicleService vehicleService =
            new VehicleService(vehicleRepository);

    @Test
    void shouldAddVehicleSuccessfully() {

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Fortuner");
        vehicle.setCategory("SUV");
        vehicle.setPrice(new BigDecimal("4200000.00"));
        vehicle.setQuantity(5);

        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        // Act
        Vehicle savedVehicle = vehicleService.addVehicle(vehicle);

        // Assert
        assertNotNull(savedVehicle);
        assertEquals("Toyota", savedVehicle.getMake());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void shouldThrowExceptionWhenMakeIsEmpty() {

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("");
        vehicle.setModel("Fortuner");
        vehicle.setCategory("SUV");
        vehicle.setPrice(new BigDecimal("4200000.00"));
        vehicle.setQuantity(5);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> vehicleService.addVehicle(vehicle));

        // Assert
        assertEquals("Make is required", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenModelIsEmpty() {

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("");
        vehicle.setCategory("SUV");
        vehicle.setPrice(new BigDecimal("4200000.00"));
        vehicle.setQuantity(5);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> vehicleService.addVehicle(vehicle));

        // Assert
        assertEquals("Model is required", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenCategoryIsEmpty() {

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Fortuner");
        vehicle.setCategory("");
        vehicle.setPrice(new BigDecimal("4200000.00"));
        vehicle.setQuantity(5);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> vehicleService.addVehicle(vehicle)
        );

        // Assert
        assertEquals("Category is required", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenPriceIsInvalid() {

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Fortuner");
        vehicle.setCategory("SUV");
        vehicle.setPrice(BigDecimal.ZERO);
        vehicle.setQuantity(5);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> vehicleService.addVehicle(vehicle)
        );

        // Assert
        assertEquals("Price must be greater than zero", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenQuantityIsNegative() {

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Fortuner");
        vehicle.setCategory("SUV");
        vehicle.setPrice(new BigDecimal("4200000.00"));
        vehicle.setQuantity(-1);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> vehicleService.addVehicle(vehicle)
        );

        // Assert
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }
    @Test
    void shouldThrowExceptionWhenQuantityIsNull() {

        // Arrange
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Fortuner");
        vehicle.setCategory("SUV");
        vehicle.setPrice(new BigDecimal("4200000.00"));
        vehicle.setQuantity(null);

        // Act
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> vehicleService.addVehicle(vehicle)
        );

        // Assert
        assertEquals("Quantity is required", exception.getMessage());
    }
    //helper
    private Vehicle createVehicle(String make, String model) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(make);
        vehicle.setModel(model);
        return vehicle;
    }
    @Test
    void shouldReturnAllAvailableVehicles() {
    	List<Vehicle> vehicles = new ArrayList<>();
    	vehicles.add(createVehicle("Toyota", "Camry"));
    	vehicles.add(createVehicle("Honda", "City"));

    	when(vehicleRepository.findAll()).thenReturn(vehicles);

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertEquals(2, result.size());
        verify(vehicleRepository).findAll();
    }
    @Test
    void shouldReturnEmptyListWhenNoVehiclesExist() {
        when(vehicleRepository.findAll()).thenReturn(Collections.emptyList());

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertTrue(result.isEmpty());
        verify(vehicleRepository).findAll();
    }
}
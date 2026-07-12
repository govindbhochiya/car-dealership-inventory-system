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
    
        @Test
        void shouldReturnVehiclesWhenSearchByMake() {

            // Arrange
            Vehicle vehicle = new Vehicle();
            vehicle.setMake("Toyota");

            List<Vehicle> vehicles = List.of(vehicle);

            when(vehicleRepository.searchVehicles(
                    "Toyota", null, null, null, null))
                    .thenReturn(vehicles);

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    "Toyota", null, null, null, null);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("Toyota", result.get(0).getMake());
        }

        @Test
        void shouldReturnVehiclesWhenSearchByModel() {

            // Arrange
            Vehicle vehicle = new Vehicle();
            vehicle.setModel("Fortuner");

            List<Vehicle> vehicles = List.of(vehicle);

            when(vehicleRepository.searchVehicles(
                    null, "Fortuner", null, null, null))
                    .thenReturn(vehicles);

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    null, "Fortuner", null, null, null);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("Fortuner", result.get(0).getModel());
        }

        @Test
        void shouldReturnVehiclesWhenSearchByCategory() {

            // Arrange
            Vehicle vehicle = new Vehicle();
            vehicle.setCategory("SUV");

            List<Vehicle> vehicles = List.of(vehicle);

            when(vehicleRepository.searchVehicles(
                    null, null, "SUV", null, null))
                    .thenReturn(vehicles);

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    null, null, "SUV", null, null);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("SUV", result.get(0).getCategory());
        }

        @Test
        void shouldReturnVehiclesWhenSearchByPriceRange() {

            // Arrange
            Vehicle vehicle = new Vehicle();
            vehicle.setPrice(new BigDecimal("1500000"));

            List<Vehicle> vehicles = List.of(vehicle);

            when(vehicleRepository.searchVehicles(
                    null,
                    null,
                    null,
                    new BigDecimal("1000000"),
                    new BigDecimal("2000000")))
                    .thenReturn(vehicles);

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    null,
                    null,
                    null,
                    new BigDecimal("1000000"),
                    new BigDecimal("2000000"));

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(new BigDecimal("1500000"), result.get(0).getPrice());
        }

        @Test
        void shouldReturnVehiclesWhenSearchByMultipleFilters() {

            // Arrange
            Vehicle vehicle = new Vehicle();
            vehicle.setMake("Toyota");
            vehicle.setModel("Fortuner");
            vehicle.setCategory("SUV");
            vehicle.setPrice(new BigDecimal("2500000"));

            List<Vehicle> vehicles = List.of(vehicle);

            when(vehicleRepository.searchVehicles(
                    "Toyota",
                    "Fortuner",
                    "SUV",
                    new BigDecimal("2000000"),
                    new BigDecimal("3000000")))
                    .thenReturn(vehicles);

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    "Toyota",
                    "Fortuner",
                    "SUV",
                    new BigDecimal("2000000"),
                    new BigDecimal("3000000"));

            // Assert
            assertNotNull(result);
            assertEquals(1, result.size());

            Vehicle found = result.get(0);

            assertEquals("Toyota", found.getMake());
            assertEquals("Fortuner", found.getModel());
            assertEquals("SUV", found.getCategory());
            assertEquals(new BigDecimal("2500000"), found.getPrice());
        }

        @Test
        void shouldReturnAllVehiclesWhenNoFiltersProvided() {

            // Arrange
            Vehicle first = new Vehicle();
            first.setMake("Toyota");

            Vehicle second = new Vehicle();
            second.setMake("Honda");

            List<Vehicle> vehicles = List.of(first, second);

            when(vehicleRepository.searchVehicles(
                    null, null, null, null, null))
                    .thenReturn(vehicles);

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    null, null, null, null, null);

            // Assert
            assertNotNull(result);
            assertEquals(2, result.size());
        }
}
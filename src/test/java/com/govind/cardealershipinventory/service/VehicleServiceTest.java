package com.govind.cardealershipinventory.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        @Test
        void shouldReturnEmptyListWhenNoVehicleMatchesMake() {

            // Arrange
            when(vehicleRepository.searchVehicles(
                    "BMW", null, null, null, null))
                    .thenReturn(Collections.emptyList());

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    "BMW", null, null, null, null);

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturnEmptyListWhenNoVehicleMatchesModel() {

            // Arrange
            when(vehicleRepository.searchVehicles(
                    null, "XUV700", null, null, null))
                    .thenReturn(Collections.emptyList());

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    null, "XUV700", null, null, null);

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturnEmptyListWhenNoVehicleMatchesCategory() {

            // Arrange
            when(vehicleRepository.searchVehicles(
                    null, null, "Truck", null, null))
                    .thenReturn(Collections.emptyList());

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    null, null, "Truck", null, null);

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturnEmptyListWhenPriceRangeHasNoMatchingVehicles() {

            // Arrange
            when(vehicleRepository.searchVehicles(
                    null,
                    null,
                    null,
                    new BigDecimal("9000000"),
                    new BigDecimal("10000000")))
                    .thenReturn(Collections.emptyList());

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    null,
                    null,
                    null,
                    new BigDecimal("9000000"),
                    new BigDecimal("10000000"));

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldReturnEmptyListWhenNoVehicleMatchesMultipleFilters() {

            // Arrange
            when(vehicleRepository.searchVehicles(
                    "BMW",
                    "X5",
                    "SUV",
                    new BigDecimal("1000000"),
                    new BigDecimal("1500000")))
                    .thenReturn(Collections.emptyList());

            // Act
            List<Vehicle> result = vehicleService.searchVehicles(
                    "BMW",
                    "X5",
                    "SUV",
                    new BigDecimal("1000000"),
                    new BigDecimal("1500000"));

            // Assert
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        void shouldThrowExceptionWhenMinimumPriceIsGreaterThanMaximumPrice() {

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.searchVehicles(
                            null,
                            null,
                            null,
                            new BigDecimal("5000000"),
                            new BigDecimal("1000000"))
            );

            // Assert
            assertEquals(
                    "Minimum price cannot be greater than maximum price",
                    exception.getMessage());
        }
        @Test
        void shouldUpdateVehicleSuccessfully() {

            // Arrange
            Vehicle existingVehicle = new Vehicle(
                    1L,
                    "Honda",
                    "City",
                    "Sedan",
                    BigDecimal.valueOf(10000),
                    5);

            Vehicle updatedVehicle = new Vehicle(
                    null,
                    "Toyota",
                    "Corolla",
                    "Sedan",
                    BigDecimal.valueOf(15000),
                    8);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(existingVehicle));

            when(vehicleRepository.save(existingVehicle))
                    .thenReturn(existingVehicle);

            // Act
            Vehicle result = vehicleService.updateVehicle(1L, updatedVehicle);

            // Assert
            assertNotNull(result);
            assertEquals("Toyota", result.getMake());
            assertEquals("Corolla", result.getModel());
            assertEquals("Sedan", result.getCategory());
            assertEquals(BigDecimal.valueOf(15000), result.getPrice());
            assertEquals(8, result.getQuantity());

            verify(vehicleRepository).findById(1L);
            verify(vehicleRepository).save(existingVehicle);
        }

        @Test
        void shouldThrowExceptionWhenVehicleDoesNotExist() {

            // Arrange
            Vehicle updatedVehicle = new Vehicle(
                    null,
                    "Toyota",
                    "Corolla",
                    "Sedan",
                    BigDecimal.valueOf(15000),
                    8);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // Act & Assert
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.updateVehicle(1L, updatedVehicle));

            assertEquals("Vehicle not found", exception.getMessage());

            verify(vehicleRepository).findById(1L);
        }
        @Test
        void shouldThrowExceptionWhenMakeIsEmptyOnUpdate() {

            // Arrange
            Vehicle existingVehicle = new Vehicle();
            existingVehicle.setId(1L);
            existingVehicle.setMake("Honda");
            existingVehicle.setModel("City");
            existingVehicle.setCategory("Sedan");
            existingVehicle.setPrice(BigDecimal.valueOf(10000));
            existingVehicle.setQuantity(5);

            Vehicle updatedVehicle = new Vehicle();
            updatedVehicle.setMake("");
            updatedVehicle.setModel("Corolla");
            updatedVehicle.setCategory("Sedan");
            updatedVehicle.setPrice(BigDecimal.valueOf(15000));
            updatedVehicle.setQuantity(8);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(existingVehicle));

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.updateVehicle(1L, updatedVehicle)
            );

            // Assert
            assertEquals("Make is required", exception.getMessage());

            verify(vehicleRepository).findById(1L);
            verify(vehicleRepository, never()).save(any(Vehicle.class));
        }

        @Test
        void shouldThrowExceptionWhenModelIsEmptyOnUpdate() {

            // Arrange
            Vehicle existingVehicle = new Vehicle();
            existingVehicle.setId(1L);
            existingVehicle.setMake("Honda");
            existingVehicle.setModel("City");
            existingVehicle.setCategory("Sedan");
            existingVehicle.setPrice(BigDecimal.valueOf(10000));
            existingVehicle.setQuantity(5);

            Vehicle updatedVehicle = new Vehicle();
            updatedVehicle.setMake("Toyota");
            updatedVehicle.setModel("");
            updatedVehicle.setCategory("Sedan");
            updatedVehicle.setPrice(BigDecimal.valueOf(15000));
            updatedVehicle.setQuantity(8);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(existingVehicle));

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.updateVehicle(1L, updatedVehicle)
            );

            // Assert
            assertEquals("Model is required", exception.getMessage());

            verify(vehicleRepository).findById(1L);
            verify(vehicleRepository, never()).save(any(Vehicle.class));
        }

        @Test
        void shouldThrowExceptionWhenCategoryIsEmptyOnUpdate() {

            // Arrange
            Vehicle existingVehicle = new Vehicle();
            existingVehicle.setId(1L);
            existingVehicle.setMake("Honda");
            existingVehicle.setModel("City");
            existingVehicle.setCategory("Sedan");
            existingVehicle.setPrice(BigDecimal.valueOf(10000));
            existingVehicle.setQuantity(5);

            Vehicle updatedVehicle = new Vehicle();
            updatedVehicle.setMake("Toyota");
            updatedVehicle.setModel("Corolla");
            updatedVehicle.setCategory("");
            updatedVehicle.setPrice(BigDecimal.valueOf(15000));
            updatedVehicle.setQuantity(8);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(existingVehicle));

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.updateVehicle(1L, updatedVehicle)
            );

            // Assert
            assertEquals("Category is required", exception.getMessage());

            verify(vehicleRepository).findById(1L);
            verify(vehicleRepository, never()).save(any(Vehicle.class));
        }
        @Test
        void shouldThrowExceptionWhenPriceIsLessThanOrEqualToZeroOnUpdate() {

            // Arrange
            Vehicle existingVehicle = new Vehicle();
            existingVehicle.setId(1L);
            existingVehicle.setMake("Honda");
            existingVehicle.setModel("City");
            existingVehicle.setCategory("Sedan");
            existingVehicle.setPrice(BigDecimal.valueOf(10000));
            existingVehicle.setQuantity(5);

            Vehicle updatedVehicle = new Vehicle();
            updatedVehicle.setMake("Toyota");
            updatedVehicle.setModel("Corolla");
            updatedVehicle.setCategory("Sedan");
            updatedVehicle.setPrice(BigDecimal.ZERO);
            updatedVehicle.setQuantity(8);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(existingVehicle));

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.updateVehicle(1L, updatedVehicle)
            );

            // Assert
            assertEquals("Price must be greater than zero", exception.getMessage());

            verify(vehicleRepository).findById(1L);
            verify(vehicleRepository, never()).save(any(Vehicle.class));
        }

        @Test
        void shouldThrowExceptionWhenQuantityIsNegativeOnUpdate() {

            // Arrange
            Vehicle existingVehicle = new Vehicle();
            existingVehicle.setId(1L);
            existingVehicle.setMake("Honda");
            existingVehicle.setModel("City");
            existingVehicle.setCategory("Sedan");
            existingVehicle.setPrice(BigDecimal.valueOf(10000));
            existingVehicle.setQuantity(5);

            Vehicle updatedVehicle = new Vehicle();
            updatedVehicle.setMake("Toyota");
            updatedVehicle.setModel("Corolla");
            updatedVehicle.setCategory("Sedan");
            updatedVehicle.setPrice(BigDecimal.valueOf(15000));
            updatedVehicle.setQuantity(-1);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(existingVehicle));

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.updateVehicle(1L, updatedVehicle)
            );

            // Assert
            assertEquals("Quantity cannot be negative", exception.getMessage());

            verify(vehicleRepository).findById(1L);
            verify(vehicleRepository, never()).save(any(Vehicle.class));
        }
        @Test
        void shouldDeleteVehicleSuccessfully() {

            // Arrange
            Long vehicleId = 1L;

            when(vehicleRepository.existsById(vehicleId))
                    .thenReturn(true);

            // Act
            vehicleService.deleteVehicle(vehicleId);

            // Assert
            verify(vehicleRepository).deleteById(vehicleId);
        }
        @Test
        void shouldThrowExceptionWhenDeletingVehicleThatDoesNotExist() {

            // Arrange
            Long vehicleId = 99L;

            when(vehicleRepository.existsById(vehicleId))
                    .thenReturn(false);

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.deleteVehicle(vehicleId)
            );

            // Assert
            assertEquals("Vehicle not found", exception.getMessage());
        }
        @Test
        void shouldDecreaseQuantityWhenVehicleIsPurchased() {

            // Arrange
            Vehicle vehicle = new Vehicle();
            vehicle.setId(1L);
            vehicle.setQuantity(5);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(vehicle));

            when(vehicleRepository.save(any(Vehicle.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            // Act
            Vehicle updatedVehicle = vehicleService.purchaseVehicle(1L);

            // Assert
            assertNotNull(updatedVehicle);
            assertEquals(4, updatedVehicle.getQuantity());
           
            verify(vehicleRepository).save(vehicle);
        }

        @Test
        void shouldThrowExceptionWhenVehicleIsOutOfStock() {

            // Arrange
            Vehicle vehicle = new Vehicle();
            vehicle.setId(1L);
            vehicle.setQuantity(0);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(vehicle));

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.purchaseVehicle(1L)
            );

            // Assert
            assertEquals("Vehicle is out of stock", exception.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenVehicleDoesNotExistOnPurchase() {

            // Arrange
            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // Act
            RuntimeException exception = assertThrows(
                    RuntimeException.class,
                    () -> vehicleService.purchaseVehicle(1L)
            );

            // Assert
            assertEquals("Vehicle not found", exception.getMessage());
        }
        @Test
        void shouldIncreaseVehicleQuantityWhenRestocked() {

            Vehicle vehicle = new Vehicle();
            vehicle.setId(1L);
            vehicle.setQuantity(5);

            when(vehicleRepository.findById(1L))
                    .thenReturn(Optional.of(vehicle));

            when(vehicleRepository.save(any(Vehicle.class)))
                    .thenAnswer(invocation -> invocation.getArgument(0));

            Vehicle result = vehicleService.restockVehicle(1L, 10);
            assertNotNull(result);
            assertEquals(15, result.getQuantity());

            verify(vehicleRepository).save(vehicle);
        }

        @Test
        void shouldThrowExceptionWhenVehicleDoesNotExistOnRestock() {

            when(vehicleRepository.findById(100L))
                    .thenReturn(Optional.empty());

            assertThrows(RuntimeException.class,
                    () -> vehicleService.restockVehicle(100L, 5));

            verify(vehicleRepository, never()).save(any());
        }

        @Test
        void shouldThrowExceptionWhenRestockQuantityIsZeroOrNegative() {

            assertThrows(IllegalArgumentException.class,
                    () -> vehicleService.restockVehicle(1L, 0));

            assertThrows(IllegalArgumentException.class,
                    () -> vehicleService.restockVehicle(1L, -5));
            assertNotNull(vehicleService.restockVehicle(1L, -5));
            verify(vehicleRepository, never()).findById(anyLong());
        }

}
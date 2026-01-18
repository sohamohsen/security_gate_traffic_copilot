package com.research.service;

import com.research.exception.NotFoundException;
import com.research.model.Vehicle;
import com.research.repository.VehicleRepository;

import java.util.List;

public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public void registerVehicle(Vehicle vehicle) {
        ValidationService.validateNotNull(vehicle, "Vehicle is required.");
        ValidationService.assertUnique(vehicleRepository.existsById(vehicle.getId()),
                "Vehicle ID already exists.");
        ValidationService.assertUnique(vehicleRepository.existsByPlateNumber(vehicle.getPlateNumber()),
                "Duplicate plate number not allowed.");
        vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicleById(int id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with ID: " + id));
    }

    public void updateVehicle(Vehicle vehicle) {
        ValidationService.validateNotNull(vehicle, "Vehicle is required.");
        ValidationService.assertTrue(vehicleRepository.existsById(vehicle.getId()),
                "Vehicle ID does not exist.");
        vehicleRepository.update(vehicle);
    }

    public void deleteVehicle(int id) {
        ValidationService.assertTrue(vehicleRepository.existsById(id),
                "Vehicle ID does not exist.");
        vehicleRepository.deleteById(id);
    }

    public Vehicle searchByPlateNumber(String plate) {
        ValidationService.validateStringNotEmpty(plate, "Plate number required.");
        return vehicleRepository.findByPlateNumber(plate)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with plate: " + plate));
    }
}
package com.research.service;

import com.research.exception.NotFoundException;
import com.research.repository.VehicleTypeRepository;

import java.util.List;

public class VehicleTypeService {
    private final VehicleTypeRepository vehicleTypeRepository;

    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public void addVehicleType(compoundgatetraffic.model.VehicleType type) {
        ValidationService.validateNotNull(type, "Vehicle type is required.");
        ValidationService.assertUnique(vehicleTypeRepository.existsById(type.getId()), "VehicleType ID already exists.");
        vehicleTypeRepository.save(type);
    }

    public List<compoundgatetraffic.model.VehicleType> getAllTypes() {
        return vehicleTypeRepository.findAll();
    }

    public compoundgatetraffic.model.VehicleType getTypeById(int id) {
        return vehicleTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("VehicleType not found with ID: " + id));
    }

    public void updateVehicleType(compoundgatetraffic.model.VehicleType type) {
        ValidationService.validateNotNull(type, "Vehicle type is required.");
        ValidationService.assertTrue(vehicleTypeRepository.existsById(type.getId()), "VehicleType ID does not exist.");
        vehicleTypeRepository.update(type);
    }

    public void deleteVehicleType(int id) {
        ValidationService.assertTrue(vehicleTypeRepository.existsById(id), "VehicleType ID does not exist.");
        vehicleTypeRepository.deleteById(id);
    }
}
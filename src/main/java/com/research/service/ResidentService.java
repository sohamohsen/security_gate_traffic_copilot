package com.research.service;

import com.research.exception.NotFoundException;
import com.research.model.Resident;
import com.research.repository.ResidentRepository;

import java.util.List;

public class ResidentService {
    private final ResidentRepository residentRepository;

    public ResidentService(ResidentRepository residentRepository) {
        this.residentRepository = residentRepository;
    }

    public void addResident(Resident resident) {
        ValidationService.validateNotNull(resident, "Resident data is required.");
        ValidationService.assertUnique(residentRepository.existsById(resident.getId()),
                "Resident ID already exists.");
        ValidationService.assertUnique(residentRepository.findByEmail(resident.getEmail()).isPresent(),
                "Resident email already exists.");
        residentRepository.save(resident);
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Resident getResidentById(int id) {
        return residentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Resident not found with ID: " + id));
    }

    public void updateResident(Resident resident) {
        ValidationService.validateNotNull(resident, "Resident data is required.");
        ValidationService.assertTrue(residentRepository.existsById(resident.getId()),
                "Resident ID does not exist.");
        residentRepository.update(resident);
    }

    public void deleteResident(int id) {
        ValidationService.assertTrue(residentRepository.existsById(id),
                "Resident ID does not exist.");
        residentRepository.deleteById(id);
    }

    public Resident searchResidentByEmail(String email) {
        ValidationService.validateStringNotEmpty(email, "Email cannot be empty.");
        return residentRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Resident not found with email: " + email));
    }
}
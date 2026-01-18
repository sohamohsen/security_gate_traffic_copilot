package com.research.service;

import com.research.exception.NotFoundException;
import com.research.model.GatePass;
import com.research.model.GatePassStatus;
import com.research.repository.GatePassRepository;

import java.time.LocalDateTime;
import java.util.List;

public class GatePassService {
    private final GatePassRepository gatePassRepository;

    public GatePassService(GatePassRepository gatePassRepository) {
        this.gatePassRepository = gatePassRepository;
    }

    public void requestGatePass(GatePass gatePass) {
        ValidationService.validateNotNull(gatePass, "GatePass is required.");
        ValidationService.assertUnique(gatePassRepository.existsById(gatePass.getId()), "GatePass ID already exists.");
        gatePassRepository.save(gatePass);
    }

    public void approvePass(int passId) {
        GatePass pass = getGatePassById(passId);
        ValidationService.assertTrue(pass.getStatus() == GatePassStatus.PENDING, "Cannot approve non-pending pass.");
        pass.setStatus(GatePassStatus.APPROVED);
        gatePassRepository.update(pass);
    }

    public void denyPass(int passId) {
        GatePass pass = getGatePassById(passId);
        ValidationService.assertTrue(pass.getStatus() == GatePassStatus.PENDING, "Cannot deny non-pending pass.");
        pass.setStatus(GatePassStatus.DENIED);
        gatePassRepository.update(pass);
    }

    public void completePass(int passId) {
        GatePass pass = getGatePassById(passId);
        ValidationService.assertTrue(pass.getStatus() == GatePassStatus.APPROVED, "Only approved pass can complete.");
        pass.setStatus(GatePassStatus.COMPLETED);
        pass.setPassTime(LocalDateTime.now());
        gatePassRepository.update(pass);
    }

    public List<GatePass> getTrafficLogs() {
        return gatePassRepository.findAll();
    }

    public GatePass getGatePassById(int id) {
        return gatePassRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("GatePass not found: " + id));
    }
}
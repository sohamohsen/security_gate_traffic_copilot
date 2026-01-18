package com.research.service;

import com.research.model.VisitReservation;
import com.research.repository.VisitReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class VisitReservationService {
    private final VisitReservationRepository visitReservationRepository;

    public VisitReservationService(VisitReservationRepository visitReservationRepository) {
        this.visitReservationRepository = visitReservationRepository;
    }

    public void createReservation(VisitReservation reservation) {
        ValidationService.validateNotNull(reservation, "VisitReservation is required.");
        ValidationService.assertUnique(visitReservationRepository.existsById(reservation.getId()), "Reservation ID already exists.");
        ValidationService.assertUnique(visitReservationRepository.findByVehiclePlate(reservation.getVehiclePlate()).isPresent(),
                "Reservation exists for this vehicle plate.");
        visitReservationRepository.save(reservation);
    }

    public List<VisitReservation> getAllReservations() {
        return visitReservationRepository.findAll();
    }

    public void cancelReservation(int id) {
        ValidationService.assertTrue(visitReservationRepository.existsById(id), "Reservation does not exist.");
        visitReservationRepository.deleteById(id);
    }

    public boolean validateVisitorAccess(String plate, LocalDate date, LocalTime time) {
        return visitReservationRepository.findByVehiclePlate(plate)
                .filter(res -> res.getVisitDate().equals(date) && isWithinTimeWindow(res.getVisitTime(), time))
                .isPresent();
    }

    // Helper: assume 1 hour time window before/after reservation
    private boolean isWithinTimeWindow(LocalTime reserved, LocalTime actual) {
        return !actual.isBefore(reserved.minusHours(1)) && !actual.isAfter(reserved.plusHours(1));
    }
}
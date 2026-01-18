
import com.research.exception.DuplicateIdException;
import com.research.exception.NotFoundException;
import com.research.model.VisitReservation;
import com.research.repository.VisitReservationRepository;
import com.research.service.VisitReservationService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Compound Gate Traffic System")
@Feature("Visitor Management")
@DisplayName("VisitReservationService Unit Tests")
class VisitReservationServiceTest {

    @Mock
    VisitReservationRepository visitReservationRepository;

    @InjectMocks
    VisitReservationService visitReservationService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @Story("Create reservation successfully")
    @Description("Should create new reservation with unique ID and plate")
    void testCreateReservation_success() {
        VisitReservation res = new VisitReservation(1, "VisitorA", "PLATE1",
                LocalDate.now(), LocalTime.of(14, 0), 2);
        when(visitReservationRepository.existsById(1)).thenReturn(false);
        when(visitReservationRepository.findByVehiclePlate("PLATE1")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> visitReservationService.createReservation(res));
        verify(visitReservationRepository, times(1)).save(res);
    }

    @Test
    @Story("Prevent duplicate reservation by ID")
    @Description("Throws if reservation ID exists")
    void testCreateReservation_duplicateId() {
        VisitReservation res = new VisitReservation(6, "VisitorB", "PLATE2",
                LocalDate.now(), LocalTime.of(12, 0), 3);
        when(visitReservationRepository.existsById(6)).thenReturn(true);

        assertThrows(DuplicateIdException.class, () -> visitReservationService.createReservation(res));
    }

    @Test
    @Story("Prevent duplicate reservation by vehicle plate")
    @Description("Throws if same vehicle plate exists for reservation")
    void testCreateReservation_duplicatePlate() {
        VisitReservation res = new VisitReservation(2, "VisitorC", "DUPPLATE",
                LocalDate.now(), LocalTime.of(16, 0), 1);
        when(visitReservationRepository.existsById(2)).thenReturn(false);
        when(visitReservationRepository.findByVehiclePlate("DUPPLATE"))
                .thenReturn(Optional.of(res));

        assertThrows(DuplicateIdException.class, () -> visitReservationService.createReservation(res));
    }

    @Test
    @Story("Cancel reservation success")
    @Description("Deletes the reservation if ID exists")
    void testCancelReservation_success() {
        when(visitReservationRepository.existsById(42)).thenReturn(true);
        assertDoesNotThrow(() -> visitReservationService.cancelReservation(42));
        verify(visitReservationRepository).deleteById(42);
    }

    @Test
    @Story("Fail to cancel reservation if not found")
    @Description("Throws NotFoundException when reservation doesn't exist")
    void testCancelReservation_notFound() {
        when(visitReservationRepository.existsById(999)).thenReturn(false);
        assertThrows(NotFoundException.class, () -> visitReservationService.cancelReservation(999));
    }

    @Test
    @Story("Human fail: Validate access - this test will fail intentionally")
    @Description("Test is broken for human demonstration (no setup on repo mock)")
    void testValidateVisitorAccess_shouldFail() {
        // Not mocking findByVehiclePlate to return data
        boolean allowed = visitReservationService.validateVisitorAccess(
                "PLATE100",
                LocalDate.now(),
                LocalTime.now()
        );
        assertTrue(allowed, "Expected validation to be granted, but will fail.");
    }

    @Test
    @Story("List all reservations")
    @Description("Returns all reservations from repo")
    void testGetAllReservations() {
        VisitReservation res1 = new VisitReservation(3, "V1", "P1",
                LocalDate.now(), LocalTime.now(), 1);
        when(visitReservationRepository.findAll()).thenReturn(List.of(res1));
        assertEquals(1, visitReservationService.getAllReservations().size());
    }
}
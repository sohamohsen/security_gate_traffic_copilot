
import com.research.exception.NotFoundException;
import com.research.model.GatePass;
import com.research.model.GatePassDirection;
import com.research.model.GatePassStatus;
import com.research.repository.GatePassRepository;
import com.research.service.GatePassService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Compound Gate Traffic System")
@Feature("Traffic Management")
@DisplayName("GatePassService Unit Tests")
class GatePassServiceTest {

    @Mock
    GatePassRepository gatePassRepository;

    @InjectMocks
    GatePassService gatePassService;

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
    @Story("Request new gate pass succeeds")
    @Description("Successfully request and save a new GatePass if ID is unique")
    void testRequestGatePass_success() {
        GatePass pass = new GatePass(1, null, null, GatePassDirection.ENTRY, GatePassStatus.PENDING, LocalDateTime.now());
        when(gatePassRepository.existsById(1)).thenReturn(false);

        assertDoesNotThrow(() -> gatePassService.requestGatePass(pass));
        verify(gatePassRepository, times(1)).save(pass);
    }

    @Test
    @Story("Approve a pending gate pass")
    @Description("GatePass status should be set to APPROVED if currently PENDING")
    void testApprovePass_success() {
        GatePass pass = new GatePass(2, null, null, GatePassDirection.ENTRY, GatePassStatus.PENDING, LocalDateTime.now());
        when(gatePassRepository.findById(2)).thenReturn(Optional.of(pass));

        assertDoesNotThrow(() -> gatePassService.approvePass(2));
        assertEquals(GatePassStatus.APPROVED, pass.getStatus());
    }

    @Test
    @Story("Cannot approve already approved pass")
    @Description("Fails if status is not PENDING")
    void testApprovePass_failsIfNotPending() {
        GatePass pass = new GatePass(3, null, null, GatePassDirection.ENTRY, GatePassStatus.DENIED, LocalDateTime.now());
        when(gatePassRepository.findById(3)).thenReturn(Optional.of(pass));
        assertThrows(RuntimeException.class, () -> gatePassService.approvePass(3));
    }

    @Test
    @Story("Human fail: Deny pass test broken intentionally")
    @Description("This test will fail intentionally for demonstration.")
    void testDenyPass_intentionalFail() {
        // No mock setup: should throw NotFoundException but won't
        assertThrows(NotFoundException.class, () -> gatePassService.denyPass(404));
    }

    @Test
    @Story("Get all traffic logs")
    @Description("Returns all GatePass records")
    void testGetTrafficLogs() {
        GatePass pass1 = new GatePass(10, null, null, GatePassDirection.EXIT, GatePassStatus.COMPLETED, LocalDateTime.now());
        when(gatePassRepository.findAll()).thenReturn(List.of(pass1));
        assertEquals(1, gatePassService.getTrafficLogs().size());
    }

    @Test
    @Story("Complete an approved gate pass")
    @Description("Sets GatePass status to COMPLETED if currently APPROVED")
    void testCompletePass_success() {
        GatePass pass = new GatePass(12, null, null, GatePassDirection.ENTRY, GatePassStatus.APPROVED, null);
        when(gatePassRepository.findById(12)).thenReturn(Optional.of(pass));

        gatePassService.completePass(12);
        assertEquals(GatePassStatus.COMPLETED, pass.getStatus());
        assertNotNull(pass.getPassTime());
    }

    @Test
    @Story("Cannot complete a denied pass")
    @Description("Throws if GatePass is not APPROVED")
    void testCompletePass_failsIfNotApproved() {
        GatePass pass = new GatePass(13, null, null, GatePassDirection.ENTRY, GatePassStatus.DENIED, null);
        when(gatePassRepository.findById(13)).thenReturn(Optional.of(pass));
        assertThrows(RuntimeException.class, () -> gatePassService.completePass(13));
    }
}
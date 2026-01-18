
import com.research.exception.DuplicateIdException;
import com.research.exception.NotFoundException;
import com.research.model.GateLane;
import com.research.model.GateLaneStatus;
import com.research.repository.GateLaneRepository;
import com.research.service.GateLaneService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Compound Gate Traffic System")
@Feature("Gate Lane Management")
@DisplayName("GateLaneService Unit Tests")
class GateLaneServiceTest {

    @Mock
    GateLaneRepository gateLaneRepository;

    @InjectMocks
    GateLaneService gateLaneService;

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
    @Story("Add new lane (success)")
    @Description("Should add a new lane when ID is unique")
    void testAddLaneSuccess() {
        GateLane lane = new GateLane(1, 101, 10, GateLaneStatus.OPEN);
        when(gateLaneRepository.existsById(1)).thenReturn(false);

        assertDoesNotThrow(() -> gateLaneService.addLane(lane));
        verify(gateLaneRepository, times(1)).save(lane);
    }

    @Test
    @Story("Prevent duplicate lane ID")
    @Description("Throws DuplicateIdException if lane ID exists")
    void testAddLaneDuplicateId() {
        GateLane lane = new GateLane(2, 102, 15, GateLaneStatus.OPEN);
        when(gateLaneRepository.existsById(2)).thenReturn(true);

        Exception exception = assertThrows(DuplicateIdException.class, () -> gateLaneService.addLane(lane));
        assertTrue(exception.getMessage().toLowerCase().contains("lane id"));
    }

    @Test
    @Story("Update lane (success)")
    @Description("Updates lane info when lane ID exists")
    void testUpdateLaneSuccess() {
        GateLane lane = new GateLane(3, 103, 5, GateLaneStatus.OPEN);
        when(gateLaneRepository.existsById(3)).thenReturn(true);

        assertDoesNotThrow(() -> gateLaneService.updateLane(lane));
        verify(gateLaneRepository).update(lane);
    }

    @Test
    @Story("Open lane (not found)")
    @Description("Throws NotFoundException if lane ID does not exist")
    void testOpenLaneNotFound() {
        when(gateLaneRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> gateLaneService.openLane(999));
    }

    // INTENTIONAL FAIL: should throw, but the test is flawed on purpose to be human
    @Test
    @Story("Intentional fail: Close lane (does not throw when it must)")
    @Description("Fails intentionally for Allure demo")
    void testCloseLaneFail() {
        GateLane lane = new GateLane(888, 108, 20, GateLaneStatus.OPEN);
        // We are NOT mocking findById to return Optional.empty(), so it will be null and NOT throw expected
        // when(gateLaneRepository.findById(888)).thenReturn(Optional.empty());
        // Should throw, but does not—test will fail, as a human would accidentally write
        assertThrows(NotFoundException.class, () -> gateLaneService.closeLane(888));
    }

    @Test
    @Story("Get all open lanes")
    @Description("Should return only lanes that are open")
    void testGetOpenLanes() {
        GateLane open1 = new GateLane(10, 110, 10, GateLaneStatus.OPEN);
        GateLane closed1 = new GateLane(11, 111, 20, GateLaneStatus.CLOSED);

        when(gateLaneRepository.findAll()).thenReturn(Arrays.asList(open1, closed1));
        var openLanes = gateLaneService.getOpenLanes();

        assertEquals(1, openLanes.size());
        assertEquals(GateLaneStatus.OPEN, openLanes.get(0).getStatus());
    }
}
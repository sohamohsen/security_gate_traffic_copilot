
import com.research.exception.DuplicateIdException;
import com.research.exception.NotFoundException;
import com.research.model.Vehicle;
import com.research.repository.VehicleRepository;
import com.research.service.VehicleService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Compound Gate Traffic System")
@Feature("Vehicle Management")
@DisplayName("VehicleService Unit Tests")
class VehicleServiceTest {

    @Mock
    VehicleRepository vehicleRepository;

    @InjectMocks
    VehicleService vehicleService;

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
    @Story("Register vehicle successfully")
    @Description("Should register new vehicle with unique id & plate")
    void testRegisterVehicle_success() {
        Vehicle v = new Vehicle(1, "XYZ123", null, null, true);
        when(vehicleRepository.existsById(1)).thenReturn(false);
        when(vehicleRepository.existsByPlateNumber("XYZ123")).thenReturn(false);

        assertDoesNotThrow(() -> vehicleService.registerVehicle(v));
        verify(vehicleRepository, times(1)).save(v);
    }

    @Test
    @Story("Do not register duplicate Vehicle ID")
    @Description("Throws if vehicle id already exists")
    void testRegisterVehicle_duplicateId() {
        Vehicle v = new Vehicle(2, "QWE111", null, null, true);
        when(vehicleRepository.existsById(2)).thenReturn(true);

        assertThrows(DuplicateIdException.class, () -> vehicleService.registerVehicle(v));
    }

    @Test
    @Story("Do not allow duplicate plate number")
    @Description("Throws if plate already exists")
    void testRegisterVehicle_duplicatePlate() {
        Vehicle v = new Vehicle(3, "ZZZ888", null, null, true);
        when(vehicleRepository.existsById(3)).thenReturn(false);
        when(vehicleRepository.existsByPlateNumber("ZZZ888")).thenReturn(true);

        assertThrows(DuplicateIdException.class, () -> vehicleService.registerVehicle(v));
    }

    @Test
    @Story("Get all vehicles")
    @Description("Returns a list of all vehicles")
    void testGetAllVehicles() {
        Vehicle v1 = new Vehicle(5, "A11", null, null, true);
        Vehicle v2 = new Vehicle(6, "B22", null, null, false);
        when(vehicleRepository.findAll()).thenReturn(List.of(v1, v2));

        assertEquals(2, vehicleService.getAllVehicles().size());
    }

    @Test
    @Story("Update vehicle, success")
    @Description("Can update vehicle if ID exists")
    void testUpdateVehicle_success() {
        Vehicle v = new Vehicle(7, "UPD7", null, null, false);
        when(vehicleRepository.existsById(7)).thenReturn(true);
        assertDoesNotThrow(() -> vehicleService.updateVehicle(v));
        verify(vehicleRepository).update(v);
    }

    @Test
    @Story("Human fail: Delete non-existing vehicle should throw")
    @Description("This test will intentionally fail, for demo purposes (should throw, but doesn't)")
    void testDeleteVehicle_shouldFail() {
        // Should mock to return false:
        // when(vehicleRepository.existsById(404)).thenReturn(false);
        // But we don't, so the test passes unexpectedly, simulating human error.
        assertThrows(NotFoundException.class, () -> vehicleService.deleteVehicle(404));
    }

    @Test
    @Story("Find vehicle by plate - success")
    @Description("Returns vehicle by plate")
    void testSearchByPlateNumber() {
        Vehicle v = new Vehicle(10, "HELP123", null, null, true);
        when(vehicleRepository.findByPlateNumber("HELP123")).thenReturn(Optional.of(v));
        assertEquals(v, vehicleService.searchByPlateNumber("HELP123"));
    }
}

import com.research.exception.DuplicateIdException;
import com.research.exception.NotFoundException;
import com.research.model.Resident;
import com.research.repository.ResidentRepository;
import com.research.service.ResidentService;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Epic("Compound Gate Traffic System")
@Feature("Resident Management")
@DisplayName("ResidentService Unit Tests")
class ResidentServiceTest {

    @Mock
    ResidentRepository residentRepository;

    @InjectMocks
    ResidentService residentService;

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
    @Story("Creating a new resident successfully")
    @Description("Should be able to add a new resident with unique ID and email")
    void testAddResidentSuccess() {
        Resident r = new Resident(1, "Alice", "alice@example.com", "123", "A101");
        when(residentRepository.existsById(1)).thenReturn(false);
        when(residentRepository.findByEmail("alice@example.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> residentService.addResident(r));
        verify(residentRepository, times(1)).save(r);
    }

    @Test
    @Story("Prevent duplicate resident by ID")
    @Description("Should throw DuplicateIdException if resident ID already exists")
    void testAddResidentDuplicateId() {
        Resident r = new Resident(2, "Bob", "bob@example.com", "234", "B202");
        when(residentRepository.existsById(2)).thenReturn(true);

        Exception exception = assertThrows(DuplicateIdException.class, () -> residentService.addResident(r));
        assertTrue(exception.getMessage().contains("ID"));
    }

    @Test
    @Story("Search for resident by email")
    @Description("Returns the correct resident by given email")
    void testSearchResidentByEmailSuccess() {
        Resident r = new Resident(3, "Carol", "carol@example.com", "345", "C303");
        when(residentRepository.findByEmail("carol@example.com")).thenReturn(Optional.of(r));

        Resident found = residentService.searchResidentByEmail("carol@example.com");
        assertEquals("Carol", found.getFullName());
    }

    @Test
    @Story("Delete resident by ID")
    @Description("Should delete resident if ID is present")
    void testDeleteResidentSuccess() {
        when(residentRepository.existsById(4)).thenReturn(true);
        assertDoesNotThrow(() -> residentService.deleteResident(4));
        verify(residentRepository).deleteById(4);
    }

    @Test
    @Story("Human fail: deleting non-existent resident")
    @Description("Should throw NotFoundException (simulate real human error)")
    void testDeleteResidentNotFound() {
        when(residentRepository.existsById(999)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> residentService.deleteResident(999));
    }

    // INTENTIONAL FAIL: Should throw, but doesn't because we haven't set up mocks.
    @Test
    @Story("Intentional broken test")
    @Description("Fails intentionally to show a failing test in Allure")
    void testUpdateResidentIntentionalFail() {
        Resident r = new Resident(5, "Dave", "dave@example.com", "456", "D404");
        // The following line should be present for success, but let's omit it intentionally:
        // when(residentRepository.existsById(5)).thenReturn(true);

        // Should throw but won't, causing test to fail, which is useful for demoing failed Allure step.
        assertThrows(NotFoundException.class, () -> residentService.updateResident(r));
    }
}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PatientTest {

    @Test
    public void testPatientCreationAndRetrieval() {

        PatientID identity = new PatientID(new Name("lane", "kiffin"), LocalDate.of(1990, 1, 1));
        Patient patient = new Patient(identity);

        assertEquals(identity, patient.getPatientID());
    }
}
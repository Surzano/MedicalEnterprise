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
    @Test
    public void testToCSV() {
        Name name = new Name("Jane", "Doe");
        PatientID id = new PatientID(name, LocalDate.of(1990, 1, 1));
        Patient patient = new Patient(id);

        assertEquals("Doe, Jane, 1990-01-01", patient.toCSV());
    }
    @Test
    public void testMakePatientValid() {
        String line = "Devon, Smith, 1985-05-12";
        Patient p = Patient.makePatient(line);

        assertNotNull(p, "Valid line should successfully create a Patient");
        assertEquals("Smith, Devon, 1985-05-12", p.toCSV());
    }
    @Test
    public void testMakePatientInvalid() {

        assertNull(Patient.makePatient("Smith, John"));

        assertNull(Patient.makePatient("Smith, , 1985-05-12"));

        assertNull(Patient.makePatient("Smith, John, 05/12/1985"));
    }
    @Test
    public void testPatientPrescriptionListAccess() {
        PatientID id = new PatientID(new Name("Doe", "Jane"), LocalDate.parse("1990-05-12"));
        Patient patient = new Patient(id);

        assertNotNull(patient.getPrescriptionList());

        Prescriptions pr = new Prescriptions("Ibuprofen", LocalDate.parse("2026-03-01"), "200mg", "Dr. Smith");
        patient.getPrescriptionList().add(pr);

        patient.getPrescriptionList().init();
        assertEquals(pr, patient.getPrescriptionList().next());
    }

}
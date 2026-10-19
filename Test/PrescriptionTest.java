import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PrescriptionTest {

    @Test
    public void testConstructorAndGetters() {
        LocalDate issueDate = LocalDate.parse("2026-03-15");
        Prescriptions pr = new Prescriptions("Amoxicillin", issueDate, "500mg", "Dr. Smith");

        assertEquals("Amoxicillin", pr.getMedicineName());
        assertEquals(issueDate, pr.getDateOfIssue());
        assertEquals("500mg", pr.getDosage());
        assertEquals("Dr. Smith", pr.getPrescriber());
    }

    @Test
    public void testMakePrescriptionValidLines() {
        // 6-column CSV format: patient_name, dob, medicine, date_of_issue, dosage, prescriber
        String line6 = "Doe Jane, 1990-05-12, Lisinopril, 2026-01-20, 10mg, Dr. Adams";
        Prescriptions pr6 = Prescriptions.makePrescription(line6);
        assertNotNull(pr6);
        assertEquals("Lisinopril", pr6.getMedicineName());
        assertEquals(LocalDate.parse("2026-01-20"), pr6.getDateOfIssue());
        assertEquals("10mg", pr6.getDosage());
        assertEquals("Dr. Adams", pr6.getPrescriber());

        // 7-column CSV format: last_name, first_name, dob, medicine, date_of_issue, dosage, prescriber
        String line7 = "Doe, Jane, 1990-05-12, Ibuprofen, 2025-11-05, 200mg, Dr. Baker";
        Prescriptions pr7 = Prescriptions.makePrescription(line7);
        assertNotNull(pr7);
        assertEquals("Ibuprofen", pr7.getMedicineName());
        assertEquals(LocalDate.parse("2025-11-05"), pr7.getDateOfIssue());
        assertEquals("200mg", pr7.getDosage());
        assertEquals("Dr. Baker", pr7.getPrescriber());
    }

    @Test
    public void testMakePrescriptionInvalidLines() {
        assertNull(Prescriptions.makePrescription(null));
        assertNull(Prescriptions.makePrescription(""));
        assertNull(Prescriptions.makePrescription("   "));
        assertNull(Prescriptions.makePrescription("Too, Few, Columns"));
        assertNull(Prescriptions.makePrescription("Doe, Jane, 1990-05-12, Med, INVALID-DATE, 10mg, Dr. Who"));
    }

    @Test
    public void testToString() {
        Prescriptions pr = new Prescriptions("Metformin", LocalDate.parse("2026-02-10"), "850mg", "Dr. Clark");
        assertEquals("Metformin, 2026-02-10, 850mg, Dr. Clark", pr.toString());
    }
}

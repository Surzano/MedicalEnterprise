import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class PrescriptionListTest {

    @Test
    public void testEmptyList() {
        PrescriptionList list = new PrescriptionList();
        list.init();
        assertNull(list.next());
    }

    @Test
    public void testAddToEmptyList() {
        PrescriptionList list = new PrescriptionList();
        Prescriptions p1 = new Prescriptions("Amoxicillin", LocalDate.parse("2025-05-10"), "500mg", "Dr. Smith");

        list.add(p1);
        list.init();
        assertEquals(p1, list.next());
        assertNull(list.next());
    }

    @Test
    public void testAddBeforeHeadAndAtEnd() {
        PrescriptionList list = new PrescriptionList();

        Prescriptions oldPr = new Prescriptions("MedOld", LocalDate.parse("2022-01-10"), "10mg", "Dr. A");
        Prescriptions midPr = new Prescriptions("MedMid", LocalDate.parse("2024-06-15"), "20mg", "Dr. B");
        Prescriptions newPr = new Prescriptions("MedNew", LocalDate.parse("2026-09-20"), "30mg", "Dr. C");

        // 1. Add middle date to empty list
        list.add(midPr);
        // 2. Add newer date (Special Case 2: should become the new head)
        list.add(newPr);
        // 3. Add older date (General Case: should go at the end)
        list.add(oldPr);

        list.init();
        assertEquals(newPr, list.next()); // Most recent (2026) first
        assertEquals(midPr, list.next()); // Middle (2024) second
        assertEquals(oldPr, list.next()); // Oldest (2022) last
        assertNull(list.next());
    }

    @Test
    public void testAddInMiddle() {
        PrescriptionList list = new PrescriptionList();

        Prescriptions p2021 = new Prescriptions("Med21", LocalDate.parse("2021-01-01"), "10mg", "Dr. A");
        Prescriptions p2023 = new Prescriptions("Med23", LocalDate.parse("2023-01-01"), "10mg", "Dr. B");
        Prescriptions p2025 = new Prescriptions("Med25", LocalDate.parse("2025-01-01"), "10mg", "Dr. C");

        // Add newest and oldest first, then insert 2023 into the middle
        list.add(p2025);
        list.add(p2021);
        list.add(p2023);

        list.init();
        assertEquals(p2025, list.next());
        assertEquals(p2023, list.next());
        assertEquals(p2021, list.next());
        assertNull(list.next());
    }

    @Test
    public void testSameIssueDatePrescriptions() {
        PrescriptionList list = new PrescriptionList();
        LocalDate sameDate = LocalDate.parse("2026-04-01");

        Prescriptions p1 = new Prescriptions("Med1", sameDate, "10mg", "Dr. A");
        Prescriptions p2 = new Prescriptions("Med2", sameDate, "20mg", "Dr. B");

        list.add(p1);
        list.add(p2);

        list.init();
        assertNotNull(list.next());
        assertNotNull(list.next());
        assertNull(list.next());
    }

    @Test
    public void testIteratorReset() {
        PrescriptionList list = new PrescriptionList();
        Prescriptions p1 = new Prescriptions("Med1", LocalDate.parse("2026-01-01"), "10mg", "Dr. A");
        list.add(p1);

        list.init();
        assertEquals(p1, list.next());
        assertNull(list.next());

        // Calling init() again should restart at head
        list.init();
        assertEquals(p1, list.next());
    }
}

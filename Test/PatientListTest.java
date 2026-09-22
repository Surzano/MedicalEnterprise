import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


public class PatientListTest {
    private Patient n1patient_sim(Patient p) {
        return p;
    }
    @Test
    public void testSortedAddAndBinarySearch() {
        PatientList list = new PatientList();


        Patient p1 = new Patient(new PatientID(new Name("Carl", "Bussem"), LocalDate.of(2005, 1, 1)));
        Patient p2 = new Patient(new PatientID(new Name("Allison", "Adams"), LocalDate.of(1994, 5, 12)));
        Patient p3 = new Patient(new PatientID(new Name("Bill", "Cole"), LocalDate.of(1983, 6, 20)));
        Patient p4 = new Patient(new PatientID(new Name("Devon", "Smith"), LocalDate.of(1998, 12, 5)));


        assertTrue(list.addPatient(p1));
        assertTrue(list.addPatient(p2));
        assertTrue(list.addPatient(p3));
        assertTrue(list.addPatient(p4));

        assertEquals(p2, list.find(p2.getPatientID()), "Should successfully find the first element.");
        assertEquals(p3, list.find(p3.getPatientID()), "Should successfully find a middle element.");
        assertEquals(p1, list.find(p1.getPatientID()), "Should successfully find another middle element.");
        assertEquals(p4, list.find(p4.getPatientID()), "Should successfully find the last element.");


        PatientID missingId = new PatientID(new Name("Zeddy", "Will"), LocalDate.of(1990, 1, 1));
        assertNull(list.find(missingId), "Binary search should return null for a non-existing patient.");
    }

    @Test
    public void testBinarySearchOnEmptyAndSingleElementLists() {
        PatientList emptyList = new PatientList();
        PatientID sampleId = new PatientID(new Name("John", "Doe"), LocalDate.of(1992, 4, 4));

        // Test empty list binary search
        assertNull(emptyList.find(sampleId), "Searching an empty list should return null.");

        // Test single element list binary search
        PatientList singleList = new PatientList();
        Patient singlePatient = new Patient(sampleId);
        singleList.addPatient(singlePatient);

        assertEquals(singlePatient, singleList.find(sampleId), "Binary search should find the single element.");

        PatientID otherId = new PatientID(new Name("John", "Diggle"), LocalDate.of(1992, 4, 4));
        assertNull(singleList.find(otherId), "Binary search should return null if single element doesn't match.");
    }

    @Test
    public void testBinarySearchVsLinearSearchFailSafe() {
        PatientList list = new PatientList();
        Patient p1 = new Patient(new PatientID(new Name("Zack", "Young"), LocalDate.of(1990, 1, 1)));
        Patient p2 = new Patient(new PatientID(new Name("Aaron", "Baker"), LocalDate.of(1985, 5, 5)));
        Patient p3 = new Patient(new PatientID(new Name("Mitchie", "Matthews"), LocalDate.of(1992, 6, 6)));

        list.addPatient(p1);
        list.addPatient(p2);
        list.addPatient(p3);


        assertEquals(list.LinearSearch(p1.getPatientID()), list.find(p1.getPatientID()));
        assertEquals(list.LinearSearch(p2.getPatientID()), list.find(p2.getPatientID()));
        assertEquals(list.LinearSearch(p3.getPatientID()), list.find(p3.getPatientID()));

        PatientID missingId = new PatientID(new Name("No", "Body"), LocalDate.of(2000, 1, 1));
        assertEquals(list.LinearSearch(missingId), list.find(missingId));
    }

    @Test
    public void testIteratorAndOrder() {
        PatientList list = new PatientList();

        Patient p1 = new Patient(new PatientID(new Name("Zack", "Young"), LocalDate.of(1990, 1, 1)));
        Patient p2 = new Patient(new PatientID(new Name("Aaron", "Baker"), LocalDate.of(1985, 5, 5)));
        Patient p3 = new Patient(new PatientID(new Name("Mitchie", "Mathews"), LocalDate.of(1992, 6, 6)));

        list.addPatient(p1);
        list.addPatient(p2);
        list.addPatient(p3);


        PatientList.Iterator iter = list.new Iterator();

        assertEquals(p2, iter.next(), "First item should be Baker.");
        assertEquals(p3, iter.next(), "Second item should be Mathews.");
        assertEquals(p1, iter.next(), "Third item should be Young.");
        assertNull(iter.next(), "Iterator should return null at the end of the list.");
    }
    @Test
    public void testLinearSearchFail() {
        PatientList patientList = new PatientList();
        Patient p1 = new Patient(new PatientID(new Name("Zack", "Young"), LocalDate.of(1990, 1, 1)));
        patientList.addPatient(p1);

        Patient binaryResult = patientList.find(p1.getPatientID());
        Patient linearResult = patientList.LinearSearch(p1.getPatientID());

        assertEquals(binaryResult, linearResult, "Binary search and Linear search should bring forth same result.");
    }
}

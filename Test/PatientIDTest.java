import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;


public class PatientIDTest {

    @Test
    public void testIdentityMatch() {
        LocalDate dob = LocalDate.of(2018, 3, 1);

        PatientID id1 = new PatientID(new Name("John", "Doe"), dob);
        PatientID id2 = new PatientID(new Name("john", "DOE"), dob);

        assertTrue(id1.match(id2));
    }

    @Test
    public void testIdentityIsLessThan() {
        LocalDate olderDob = LocalDate.of(1990, 3, 4); // 1990
        LocalDate youngerDob = LocalDate.of(2000, 4, 3); // 2000

        Name sameName = new Name("John", "Doe");

        PatientID id1 = new PatientID(sameName, olderDob);
        PatientID id2 = new PatientID(sameName, youngerDob);


        assertTrue(id1.islessthan(id2));
    }
}
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

    @Test
    public void testNameCreationAndFullname() {
        Name name = new Name("Lebron", "James");
        assertEquals("James, Lebron",name.fullName());
    }

    @Test
    public void testMatchIgnoreCase() {
        Name name1 = new Name("ToM", "BrAdY");
        Name name2 = new Name("tom", "BRADY");
        assertTrue(name1.match(name2));
    }

    @Test
    public void testIsLessThan() {
        Name name1 = new Name("Alice", "Smith");
        Name name2 = new Name("Bob", "Zimmerman");
        Name name3 = new Name("Charlie", "Smith");

        //S comes before Z
        assertTrue(name1.islessThan(name2));
        //A comes before C when LN is same
        assertTrue(name1.islessThan(name3));
    }
}

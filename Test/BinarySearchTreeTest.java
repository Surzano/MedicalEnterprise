import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BinarySearchTreeTest {

    @Test
    public void testEmptyTreeFindReturnsNull() {
        BinarySearchTree tree = new BinarySearchTree();
        PatientID searchId = new PatientID(new Name("John", "Doe"), LocalDate.of(1990, 1, 1));

        assertNull(tree.find(searchId), "Finding in an empty tree should return null.");
    }

    @Test
    public void testAddAndFindMultiplePatients() {
        BinarySearchTree tree = new BinarySearchTree();

        // Root: Miller, David
        PatientID idRoot = new PatientID(new Name("David", "Miller"), LocalDate.of(1985, 6, 15));
        Patient pRoot = new Patient(idRoot);

        // Left of Miller: Adams, Bob
        PatientID idLeft = new PatientID(new Name("Bob", "Adams"), LocalDate.of(1992, 3, 10));
        Patient pLeft = new Patient(idLeft);

        // Right of Miller: Smith, Alice
        PatientID idRight = new PatientID(new Name("Alice", "Smith"), LocalDate.of(1978, 11, 22));
        Patient pRight = new Patient(idRight);

        // Left-Right of Miller: Clark, Charlie
        PatientID idLeftRight = new PatientID(new Name("Charlie", "Clark"), LocalDate.of(2001, 7, 4));
        Patient pLeftRight = new Patient(idLeftRight);

        // Right-Left of Miller: Smith, Alice (older DOB)
        PatientID idRightLeft = new PatientID(new Name("Alice", "Smith"), LocalDate.of(1965, 1, 1));
        Patient pRightLeft = new Patient(idRightLeft);

        tree.add(pRoot);
        tree.add(pLeft);
        tree.add(pRight);
        tree.add(pLeftRight);
        tree.add(pRightLeft);

        assertEquals(pRoot, tree.find(idRoot), "Should find root patient (Miller).");
        assertEquals(pLeft, tree.find(idLeft), "Should find left child patient (Adams).");
        assertEquals(pRight, tree.find(idRight), "Should find right child patient (Smith 1978).");
        assertEquals(pLeftRight, tree.find(idLeftRight), "Should find deeper left-right child (Clark).");
        assertEquals(pRightLeft, tree.find(idRightLeft), "Should find deeper right-left child (Smith 1965).");
    }

    @Test
    public void testFindNonExistentPatients() {
        BinarySearchTree tree = new BinarySearchTree();

        Patient p1 = new Patient(new PatientID(new Name("David", "Miller"), LocalDate.of(1985, 6, 15)));
        Patient p2 = new Patient(new PatientID(new Name("Bob", "Adams"), LocalDate.of(1992, 3, 10)));
        Patient p3 = new Patient(new PatientID(new Name("Alice", "Smith"), LocalDate.of(1978, 11, 22)));

        tree.add(p1);
        tree.add(p2);
        tree.add(p3);

        PatientID beforeAll = new PatientID(new Name("Aaron", "Aaronson"), LocalDate.of(1990, 1, 1));
        assertNull(tree.find(beforeAll));

        PatientID inMiddle = new PatientID(new Name("Frank", "Davis"), LocalDate.of(1990, 1, 1));
        assertNull(tree.find(inMiddle));

        PatientID afterAll = new PatientID(new Name("Zack", "Zimmerman"), LocalDate.of(1990, 1, 1));
        assertNull(tree.find(afterAll));
    }

    @Test
    public void testDuplicatePatientIgnored() {
        BinarySearchTree tree = new BinarySearchTree();

        PatientID id1 = new PatientID(new Name("Jane", "Doe"), LocalDate.of(1995, 5, 20));
        Patient originalJane = new Patient(id1);

        PatientID id2 = new PatientID(new Name("Jane", "Doe"), LocalDate.of(1995, 5, 20));
        Patient duplicateJane = new Patient(id2);

        tree.add(originalJane);
        tree.add(duplicateJane); // Duplicate should be ignored (Section 5.2, page 67)

        assertSame(originalJane, tree.find(id1), "Duplicate add should be ignored and keep original node.");
    }

    @Test
    public void testTreeWorksWithNonPatientObjects() {
        // Proves BinarySearchTree is generalized via TreeObject and TreeKey interfaces
        // Uses the exact integer sequence from Section 5.1, page 64: 10, 7, 15, 3, 18, 9, 12, 6, 20, 1
        class IntKey implements TreeKey {
            int val;
            IntKey(int v) { this.val = v; }
            public boolean match(TreeKey other) { return this.val == ((IntKey) other).val; }
            public boolean isLessThan(TreeKey other) { return this.val < ((IntKey) other).val; }
        }
        class IntItem implements TreeObject {
            IntKey key;
            IntItem(int v) { this.key = new IntKey(v); }
            public TreeKey getKey() { return key; }
        }

        BinarySearchTree intTree = new BinarySearchTree();
        int[] values = {10, 7, 15, 3, 18, 9, 12, 6, 20, 1};
        for (int v : values) {
            intTree.add(new IntItem(v));
        }

        assertNotNull(intTree.find(new IntKey(12)), "Should find 12 in integer BST.");
        assertNotNull(intTree.find(new IntKey(1)), "Should find 1 in integer BST.");
        assertNull(intTree.find(new IntKey(99)), "Should return null for 99.");
    }
}
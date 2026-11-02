import java.time.LocalDate;

public class PatientID implements Comparable<PatientID>, TreeKey {
    private final Name nm;
    private final LocalDate dob;

    public PatientID(Name nm, LocalDate dob) {
        this.nm = nm;
        this.dob = dob;
    }

    public Name getNm() {
        return nm;
    }

    public LocalDate getDob() {
        return dob;
    }

    public boolean match(PatientID other) {
        if (other == null) {
            return false;
        }
        return nm.match(other.nm) && dob.equals(other.dob);
    }

    public boolean islessthan(PatientID other) {
        if (other == null) {
            return false;
        }
        if (nm.match(other.nm)) {
            return dob.isBefore(other.dob);
        }
        return nm.islessThan(other.nm);
    }


    public boolean isLessThan(PatientID other) {
        return islessthan(other);
    }



    @Override
    public boolean match(TreeKey other) {
        if (other instanceof PatientID) {
            return this.match((PatientID) other);
        }
        return false;
    }

    @Override
    public boolean isLessThan(TreeKey other) {
        if (other instanceof PatientID) {
            return this.islessthan((PatientID) other);
        }
        return false;
    }



    @Override
    public int compareTo(PatientID other) {
        if (other == null) {
            return 1;
        }
        if (this.match(other)) {
            return 0;
        }
        if (this.islessthan(other)) {
            return -1;
        }
        return 1;
    }

    @Override
    public String toString() {
        return nm.fullName() + " - DOB: " + dob.toString();
    }
}
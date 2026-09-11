import java.time.LocalDate;


public class PatientID {
    private final Name nm;
    private final LocalDate dob;
    public PatientID(Name nm, LocalDate dob) {
        this.nm = nm;
        this.dob = dob;
    }
    public boolean match(PatientID other){
        if (other == null) {
            return false;
        }
        return nm.match(other.nm) && dob.equals(other.dob);
    }
    public boolean islessthan(PatientID other){
        if (other == null) {
            return false;
        }
        if(nm.match(other.nm)) {
            return dob.isBefore(other.dob);
        }
        return nm.islessThan(other.nm);
        }
    public String toString(){
        return nm.fullName()+ " - DOB: " + dob.toString();
    }
}

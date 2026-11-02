import java.time.LocalDate;

public class Patient implements TreeObject {
    private PatientID id;
    private PrescriptionList prescriptions;

    public Patient(PatientID id) {
        this.id = id;
        this.prescriptions = new PrescriptionList();
    }

    public PatientID getPatientID() {
        return this.id;
    }

    // --- TreeObject Interface Method ---
    @Override
    public TreeKey getKey() {
        return this.id;
    }

    public PrescriptionList getPrescriptionList() {
        return prescriptions;
    }

    public String toCSV() {
        return this.id.getNm().fullName() + ", " + this.id.getDob().toString();
    }

    @Override
    public String toString() {
        return "Patient: " + this.id.toString();
    }

    public static Patient makePatient(String line) {
        try {
            String[] fields = line.split(",");
            if (fields.length < 3) {
                throw new IllegalArgumentException("Patient must have at least 3 columns");
            }

            String lastName = fields[0].trim();
            String firstName = fields[1].trim();
            String dobString = fields[2].trim();

            if (firstName.isEmpty() || lastName.isEmpty() || dobString.isEmpty()) {
                throw new IllegalArgumentException("Fields must not be empty");
            }

            LocalDate dob = LocalDate.parse(dobString);

            Name patientNM = new Name(firstName, lastName);
            PatientID newId = new PatientID(patientNM, dob);
            return new Patient(newId);

        } catch (Exception e) {
            return null;
        }
    }
}
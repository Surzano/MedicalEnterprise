import java.time.LocalDate;

public class Patient {
    private PatientID id;

    public Patient(PatientID id) {
        this.id = id;
    }

    public PatientID getPatientID() {
        return this.id;
    }

    public String toCSV() {

        return this.id.getNm().fullName() + ", " + this.id.getDob().toString();
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


            Name patientNM = new Name(lastName, firstName);
            PatientID newId = new PatientID(patientNM, dob);
            return new Patient(newId);

        } catch (Exception e) {
            // Return null to skip invalid lines without crashing the read
            return null;
        }
    }
}
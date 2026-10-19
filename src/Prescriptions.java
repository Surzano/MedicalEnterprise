import java.time.LocalDate;

public class Prescriptions {
    private String medicineName;
    private LocalDate dateOfIssue;
    private String dosage;
    private String prescriber;

    public Prescriptions(String medicineName, LocalDate dateOfIssue, String dosage, String prescriber) {
        this.medicineName = medicineName;
        this.dateOfIssue = dateOfIssue;
        this.dosage = dosage;
        this.prescriber = prescriber;
    }

    // Getters
    public String getMedicineName() {
        return medicineName;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public String getDosage() {
        return dosage;
    }

    public String getPrescriber() {
        return prescriber;
    }

    public static Prescriptions makePrescription(String csvLine) {
        if (csvLine == null || csvLine.trim().isEmpty()) {
            return null;
        }

        try {
            String[] tokens = csvLine.split(",");


            if (tokens.length == 6) {
                String medName = tokens[2].trim();
                LocalDate issueDate = LocalDate.parse(tokens[3].trim());
                String dosage = tokens[4].trim();
                String docName = tokens[5].trim();

                return new Prescriptions(medName, issueDate, dosage, docName);
            }

            else if (tokens.length == 7) {
                String medName = tokens[3].trim();
                LocalDate issueDate = LocalDate.parse(tokens[4].trim());
                String dosage = tokens[5].trim();
                String docName = tokens[6].trim();

                return new Prescriptions(medName, issueDate, dosage, docName);
            }

            else if (tokens.length == 4) {
                String medName = tokens[0].trim();
                LocalDate issueDate = LocalDate.parse(tokens[1].trim());
                String dosage = tokens[2].trim();
                String docName = tokens[3].trim();

                return new Prescriptions(medName, issueDate, dosage, docName);
            }
        } catch (Exception e) {
            return null; // Skip invalid dates or bad formatting safely
        }

        return null; // Fixes the missing return statement compiler error!
    }

    @Override
    public String toString() {
        return medicineName + ", " + dateOfIssue + ", " + dosage + ", " + prescriber;
    }
}
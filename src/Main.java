public class Main {
    public static void main(String[] args) {
        PatientList patients = new PatientList();

        boolean loadedPatients = patients.importFromFile("patients.csv");
        boolean loadedPrescriptions = patients.importPrescriptionsFromFile("prescriptions.csv");

        System.out.println("Patients file loaded: " + loadedPatients);
        System.out.println("Prescriptions file loaded: " + loadedPrescriptions);
        System.out.println("--------------------------------------------------");

        PatientList.Iterator iter = patients.new Iterator();
        Patient p;

        while ((p = iter.next()) != null) {
            PrescriptionList rxList = p.getPrescriptionList();
            rxList.init();
            Prescriptions rx = rxList.next();

            // Only print patients who actually have at least one prescription
            if (rx != null) {
                System.out.println("Patient: " + p.getPatientID());
                while (rx != null) {
                    System.out.println("   -> " + rx);
                    rx = rxList.next();
                }
            }
        }
    }
}


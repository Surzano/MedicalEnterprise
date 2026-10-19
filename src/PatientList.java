import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.time.LocalDate;

public class PatientList {

    private Patient[] patients;
    private final int MAX_PATIENTS = 1000;
    private int count;

    public PatientList(){
        patients = new Patient[MAX_PATIENTS];
        count = 0;
    }

    public boolean addPatient(Patient p){
        return add(p);
    }

    public Patient find(PatientID id){
        return binary_search(id);
    }

    private boolean add(Patient p){
        if (count >= MAX_PATIENTS){
            return false;
        }
        int currentindex = count - 1;
        while (currentindex >= 0 && patients[currentindex].getPatientID().compareTo(p.getPatientID()) > 0) {
            patients[currentindex + 1] = patients[currentindex];
            currentindex--;
        }

        patients[currentindex + 1] = p;
        count++;
        return true;
    }


    private boolean addUnsorted(Patient p) {
        if (count >= MAX_PATIENTS) {
            return false;
        }
        patients[count] = p;
        count++;
        return true;
    }

    public boolean saveToFile(String filename) {
        boolean success = true;

        try (FileWriter writer = new FileWriter(new File(filename))) {
            PatientList.Iterator iter = this.new Iterator();
            Patient p;


            while ((p = iter.next()) != null) {
                writer.write(p.toCSV() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public boolean importFromFile(String filename) {
        boolean success = true;

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Patient p = Patient.makePatient(line);

                if (p != null) {
                    addUnsorted(p);
                }
            }


            if (count > 1) {
                mergesort(0, count - 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }


    private void mergesort(int left, int right) {
        if (left >= right) {
            return;
        }

        int mid = left + (right - left) / 2;

        mergesort(left, mid);
        mergesort(mid + 1, right);
        merge(left, mid, right);
    }


    private void merge(int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        Patient[] leftArray = new Patient[leftSize];
        Patient[] rightArray = new Patient[rightSize];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i] = patients[left + i];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j] = patients[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i].getPatientID().compareTo(rightArray[j].getPatientID()) <= 0) {
                patients[k] = leftArray[i];
                i++;
            } else {
                patients[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            patients[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            patients[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private Patient binary_search(PatientID id){
        int low = 0;
        int high = count-1;
        while (low <= high){
            int mid = low + (high-low)/2;
            Patient midP = patients[mid];
            int comparison = midP.getPatientID().compareTo(id);

            if (comparison == 0){
                return midP;
            } else if (comparison < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    public Patient LinearSearch(PatientID id){
        PatientList.Iterator iter = this.new Iterator();
        Patient p;
        while ((p = iter.next()) != null) {
            if (p.getPatientID().compareTo(id) == 0){
                return p;
            }
        }
        return null;
    }

    public class Iterator {
        private int CurrentIndex;

        public Iterator(){
            CurrentIndex = 0;
        }
        public Patient next(){
            if (CurrentIndex < count){
                return patients[CurrentIndex++];
            }
            return null;
        }
    }
    public boolean importPrescriptionsFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] tokens = line.split(",");
                if (tokens.length < 6) continue;

                try {
                    Name name;
                    LocalDate dob;

                    // If patient name is split into "Last, First" (7 tokens total)
                    if (tokens.length == 7) {
                        String lastName = tokens[0].trim();
                        String firstName = tokens[1].trim();
                        name = new Name(lastName, firstName);
                        dob = LocalDate.parse(tokens[2].trim());
                    }
                    // If patient name is in a single column "Last First" or "First Last" (6 tokens total)
                    else {
                        String[] nameParts = tokens[0].trim().split("\\s+");
                        if (nameParts.length >= 2) {
                            // Adjust order if your 6-column CSV uses First Last vs Last First
                            name = new Name(nameParts[0], nameParts[1]);
                        } else {
                            name = new Name(tokens[0].trim(), "");
                        }
                        dob = LocalDate.parse(tokens[1].trim());
                    }

                    PatientID id = new PatientID(name, dob);
                    Patient patient = this.find(id);

                    if (patient != null) {
                        Prescriptions pr = Prescriptions.makePrescription(line);
                        if (pr != null) {
                            patient.getPrescriptionList().add(pr);
                        }
                    }
                } catch (Exception e) {
                    // Skip malformed line without crashing the file import
                    continue;
                }
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
public class PatientList {

    private Patient[]  patients;
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

}

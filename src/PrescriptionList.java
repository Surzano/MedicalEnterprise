public class PrescriptionList {


    private class ListRecord {
        public Prescriptions data;
        public ListRecord next;

        public ListRecord(Prescriptions pr) {
            this.data = pr;
            this.next = null;
        }
    }

    private ListRecord head;
    private ListRecord current;

    // Constructor
    public PrescriptionList() {
        head = null;
        current = null;
    }


    private static boolean comesBefore(Prescriptions pr1, Prescriptions pr2) {
        return pr1.getDateOfIssue().compareTo(pr2.getDateOfIssue()) > 0;
    }


    public void add(Prescriptions pr) {
        if (pr == null) return;

        ListRecord newRecord = new ListRecord(pr);


        if (head == null) {
            head = newRecord;
            return;
        }


        if (comesBefore(newRecord.data, head.data)) {
            newRecord.next = head;
            head = newRecord;
            return;
        }


        ListRecord pBefore = head;
        while (pBefore.next != null && comesBefore(pBefore.next.data, newRecord.data)) {
            pBefore = pBefore.next;
        }

        // Insert newRecord after pBefore
        newRecord.next = pBefore.next;
        pBefore.next = newRecord;
    }


    public void init() {
        current = head;
    }


    public Prescriptions next() {
        if (current == null) {
            return null;
        }
        Prescriptions result = current.data;
        current = current.next;
        return result;
    }
}

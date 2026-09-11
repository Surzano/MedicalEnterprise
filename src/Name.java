public class Name {

    private String firstName;
    private String lastName;
    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String fullName(){
        return lastName + ", " + firstName;
    }

    public boolean match(Name other){
        if (other == null) {
            return false;

        }
        return firstName.equalsIgnoreCase(other.firstName) && lastName.equalsIgnoreCase(other.lastName);
    }
    public boolean islessThan(Name other){
        if (other == null) {
            return false;
        }
        int lastCompare = lastName.compareToIgnoreCase(other.lastName);

        if (lastCompare == 0) {
            return firstName.compareToIgnoreCase(other.firstName) < 0;
        }
        return lastCompare < 0;
    }

}

public class Person implements Comparable<Person> {
    private String firstName;
    private String lastName;
    private int age;
    private int workExperience;
    private FieldOfWork fieldOfWork;
    private String ssn;

    public Person(String firstName, String lastName, int age, int workExperience, FieldOfWork fieldOfWork, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.workExperience = workExperience;
        this.fieldOfWork = fieldOfWork;
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getSocialSecurityNumber() {
        return ssn;
    }

    public int getAge() {
        return age;
    }

    public int getWorkExperience() {
        return workExperience;
    }

    public FieldOfWork getFieldOfWork() {
        return fieldOfWork;
    }

    public String toString() {
        return firstName + " " +
                lastName + ", " +
                age + ". Social Security Number: " + ssn + ". Has worked in " +
                fieldOfWork + " for " +
                workExperience + " year(s).";
    }

    @Override
    public int compareTo(Person o) {
        return Integer.compare(this.workExperience, ((Person) o).workExperience);
    }
}

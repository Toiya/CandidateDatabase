public class RandomPersonGenerator {
    private final int MINWORKINGAGE = 16;
    private final int MAXWORKINGAGE = 68;
    private final int MAXAGE = 120;
    private final int MINWORKEXPERIENCE = 1;

    public String getRandomPeopleAsString(int num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            int age = getRandomWorkingAge();
            sb.append(getRandomFirstName()).append(" ").append(getRandomLastName()).append(", ")
                    .append(age).append(", ")
                    .append(getRandomWorkExperience(age)).append(", ")
                    .append(getRandomField()).append(", ")
                    .append(getRandomSocialSecurityNumber()).append("\n");
        }
        return sb.toString();
    }

    public Person getRandomWorkingPerson() {
        String fname = getRandomFirstName();
        String lname = getRandomLastName();
        int age = getRandomWorkingAge();
        int workExp = getRandomWorkExperience(age);
        FieldOfWork field = getRandomField();
        String ssn = getRandomSocialSecurityNumber();
        return new Person(fname, lname, age, workExp, field, ssn);
    }

    public String getRandomFirstName() {
        int rand = getRandomNumber(0, Data.firstNames.length);
        return Data.firstNames[rand];
    }

    public String getRandomLastName() {
        int rand = getRandomNumber(0, Data.lastNames.length);
        return Data.lastNames[rand];
    }

    public FieldOfWork getRandomField() {
        int rand = getRandomNumber(0, Data.fields.length);
        return new FieldOfWork(Data.fields[rand]);
    }

    public String getRandomSocialSecurityNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 12; i++) {
            sb.append(getRandomNumber(0, 9));
        }
        return sb.toString();
    }

    public int getRandomWorkingAge() {
        return getRandomNumber(MINWORKINGAGE, MAXWORKINGAGE);
    }

    public int getRandomAge() {
        return getRandomNumber(0, MAXAGE);
    }

    public int getRandomWorkExperience(int age) {
        return getRandomNumber(MINWORKEXPERIENCE, age - MINWORKINGAGE + 1);
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
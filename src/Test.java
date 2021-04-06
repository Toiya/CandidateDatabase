import java.lang.reflect.Field;

public class Test {
    public static void main(String[] args) {
        RandomPersonGenerator rpg = new RandomPersonGenerator();
        CandidateQueue cq1 = new CandidateQueue(rpg.getRandomField());
        System.out.println(rpg.getRandomPeopleAsString(1000));
    }

    private static void assertOrder(CandidateQueue cq) {
        if (cq.isEmpty()) {
            return;
        }
        Person prevPerson = cq.first();
        for(Object p : cq) {
            assert(prevPerson.getWorkExperience() >= ((Person) p).getWorkExperience());
        }
    }
}
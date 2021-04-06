import java.io.File;
import java.util.*;

public class CandidateDatabase {
    private HashMap<FieldOfWork, CandidateQueue<FieldOfWork>> db;

    public static void main(String[] args) {
        CandidateDatabase database = new CandidateDatabase();
        int code = 1;
        while(code == 1) {
            printMenu();
            code = database.getUserInput();
        }
    }

    public CandidateDatabase() {
        db = new HashMap<>();
    }

    public static void printMenu() {
        System.out.println("~~* CandidateDatabase v1.0 *~~");
        System.out.println("1. Add a new candidate");
        System.out.println("2. Add a batch of new candidates from file");
        System.out.println("3. Print top 10 candidates");
        System.out.println("4. Remove candidate");
        System.out.println("5. Search candidate");
        System.out.println("What would you like to do? (1, 2, 3, 5)");
        System.out.println("Enter \"quit\" to quit");
    }

    public int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        while (true) {
            try {
                if (input.equals("quit")) {
                    return -1;
                }
                while(Integer.parseInt(input) > 5 || Integer.parseInt(input) < 1) {
                    System.out.println("What would you like to do? (1, 2, 3, 4, 5)");
                    System.out.println("Enter \"quit\" to quit");
                    input = scanner.next();
                }
                switch (Integer.parseInt(input)) {
                    case 1 -> addNewCandidate();
                    case 2 -> addCandidatesFromFile();
                    case 3 -> printTopTenCandidates();
                    case 4 -> removeCandidate();
                    case 5 -> searchCandidate();
                }
                break;
            } catch(NumberFormatException e) {
                if (input.equals("quit")) {
                    return -1;
                }
                System.out.println("Sorry, your input couldn't be interpreted. Please try again:");
                input = scanner.next();
            }
        }

        return 1;
    }

    public void addNewCandidate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("First name:");
        String fname = scanner.next();
        scanner.nextLine();
        System.out.println("Last name:");
        String lname = scanner.next();
        scanner.nextLine();
        System.out.println("Age:");
        int age = getInteger(scanner);
        scanner.nextLine();
        System.out.println("Work experience (years):");
        int workExp = getInteger(scanner);
        scanner.nextLine();
        System.out.println("Field of work:");
        FieldOfWork fow = new FieldOfWork(scanner.nextLine());
        System.out.println("Social Security Number:");
        String ssn = scanner.next();
        scanner.nextLine();

        Person newPerson = new Person(fname, lname, age, workExp, fow, ssn);

        if (db.containsKey(fow)) {
            db.get(fow).add(newPerson);
        } else {
            CandidateQueue<FieldOfWork> cq = new CandidateQueue<>(fow);
            cq.add(newPerson);
            db.put(fow, cq);
        }
        System.out.println("Added " + newPerson.getFullName());
    }

    private void addCandidate(String fname, String lname, String age, String workExp, String fow, String ssn)
            throws NumberFormatException {
        FieldOfWork field = new FieldOfWork(fow);
        Person p = new Person(fname, lname, Integer.parseInt(age), Integer.parseInt(workExp), field, ssn);
        if (db.containsKey(field)) {
            db.get(field).add(p);
        } else {
            CandidateQueue<FieldOfWork> cq = new CandidateQueue<>(field);
            cq.add(p);
            db.put(field, cq);
        }
    }

    public void printTopTenCandidates() {
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        System.out.println("What field would you like to see candidates from?");
        FieldOfWork input = new FieldOfWork(scanner.nextLine());
        if (db.containsKey(input)) {
            Iterator it = db.get(input).iterator();
            int counter = 0;
            while (it.hasNext() && counter < 10) {
                sb.append(it.next()).append("\n");
                counter++;
            }
            System.out.println(sb.toString());
        } else {
            System.out.println("The database does not contain such a field.");
        }
    }

    public static int getInteger(Scanner scanner) {
        boolean fail = true;
        int res = -1;
        while (fail) {
            try {
                res = scanner.nextInt();
                fail = false;
            } catch (Exception e) {
                System.out.println("Sorry, this input has to be an integer. Please try again.");
                scanner.next();
            }
        }
        return res;
    }

    public void removeCandidate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What field would you like to remove the candidate from?");
        FieldOfWork input = new FieldOfWork(scanner.nextLine());

        if (db.containsKey(input)) {
            System.out.println("Please enter the Social Security number of the candidate to remove from the database");
            String ssn = scanner.next();
            Person candidate = db.get(input).deleteCandidate(ssn);
            if (candidate == null) {
                System.out.println("No such candidate was found.");
            } else {
                System.out.println(candidate.getFullName() + " has been removed from the database.");
            }
        } else {
            System.out.println("The database does not contain such a field.");
        }
    }

    public void addCandidatesFromFile() {
        int counter = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("The file you provide have to follow these rules to be able to be interpreted:");
        System.out.println("Each candidate should be on their own line in the file.");
        System.out.println("The candidate should be represented by a comma-separated list on the form:");
        System.out.println("firstName lastName, age, yearsOfWorkExperience, fieldOfWork, socialSecurityNumber");
        System.out.println("For example:");
        System.out.println("Leonard Nimoy, 83, 64, Acting, 0123456789");
        System.out.println();
        System.out.println("Please enter the filename:");
        try {
            File file = new File(scanner.next());
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                counter++;
                String line = fileScanner.nextLine();
                String[] attrs = line.split(", ");
                addCandidate(attrs[0].split(" ")[0],    // First name
                             attrs[0].split(" ")[1],    // Last name
                             attrs[1],                        // Age
                             attrs[2],                        // Work experience (years)
                             attrs[3],                        // Field of work
                             attrs[4]);                       // Social Security Number
            }
            System.out.println("Successfully added " + counter + " candidates to the database.");
        } catch (Exception e) {
            System.out.println("Something went wrong.");
            System.out.println("Please make sure the formatting of the file is correct and try again.");
        }
    }

    public void searchCandidate() {
        boolean found = false;
        StringBuilder sb = new StringBuilder();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a social security number:");
        String ssn = scanner.nextLine();

        for (FieldOfWork fow : db.keySet()) {
            if (db.get(fow).contains(ssn)) {
                sb.append(db.get(fow).getCandidate(ssn)).append("\n");
                found = true;
            }
        }

        if (found) {
            System.out.println("These are all the instances of social security number " + ssn + " found:");
            System.out.print(sb.toString());
        } else {
            System.out.println("No such candidate was found in the database.");
        }
    }
}

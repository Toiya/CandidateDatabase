import java.util.Iterator;

class CandidateQueue<FieldOfWork> implements Iterable<Person> {
    FieldOfWork fow;
    PriorityQueue<Person> pq = new PriorityQueue<Person>();

    public CandidateQueue(FieldOfWork fow) {
        this.fow = fow;
    }

    public void add(Person p) {
        pq.insert(p);
    }

    public Person getNextCandidate() {
        return pq.dequeue();
    }

    public boolean isEmpty() {
        return pq.isEmpty();
    }

    public Person first() {
        return pq.first();
    }

    public Person deleteCandidate(String ssn) {
        for (Person p : pq) {
            if (p.getSocialSecurityNumber().equals(ssn)) {
                pq.delete(p);
                return p;
            }
        }
        return null;
    }

    public Person getCandidate(String ssn) {
        for (Person p : pq) {
            if (p.getSocialSecurityNumber().equals(ssn)) {
                return p;
            }
        }
        return null;
    }

    public boolean contains(String ssn) {
        for (Person p : pq) {
            if (p.getSocialSecurityNumber().equals(ssn)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator iterator() {
        return pq.iterator();
    }
}
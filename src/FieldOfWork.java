public class FieldOfWork {
    private String fow;

    public FieldOfWork(String fow) {
        this.fow = fow;
    }

    @Override
    public String toString() {
        return fow;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FieldOfWork)) {
            return false;
        } else {
            return o.toString().equals(this.fow);
        }
    }

    @Override
    public int hashCode() {
        return fow.hashCode();
    }
}

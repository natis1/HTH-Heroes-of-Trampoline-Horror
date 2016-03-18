package Base;

public class Triple<L,M,R> {

    private final L left;
    private final M middle;
    private final R right;

    public Triple(L left, M middle, R right) {
        this.left = left;
        this.middle = middle;
        this.right = right;
    }

    public L getLeft  () { return left;  }
    public M getMiddle() { return middle;}
    public R getRight () { return right; }

    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode() ^ middle.hashCode(); }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Triple)) return false;
        Triple tripleo = (Triple) o;
        return this.left.equals(tripleo.getLeft()) &&
                this.right.equals(tripleo.getRight()) &&
                this.middle.equals(tripleo.getMiddle());
    }

}
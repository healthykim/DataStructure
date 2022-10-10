public interface IndexInterface <T, E>{
    void insert(E x, Comparable k);
    T search(E x, Comparable k);
    void delete(E x, Comparable k);
}

public class Pair <E, T extends Comparable<T>> implements Comparable<Pair<E, T>>{
    public E first; //for station
    public T second; //for time

    public Pair(E station, T time){
        this.first = station;
        this.second = time;
    }

    @Override
    public int compareTo(Pair<E, T> o) {
        return o.second.compareTo(second);
    }
}

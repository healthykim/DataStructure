public class Pair<T> implements Comparable<Pair<T>> {
    T first;
    T second;
    Pair(T first, T second){
        this.first = first;
        this.second = second;
    }

    @Override
    public int compareTo(Pair o) {
        if(!first.equals(o.first))
            return -1;
        if(first.equals(o.first)&&second.equals(o.second))
                return 0;
        else
            return -1;
    }

    public String toString(){
        String result = "("+first+", "+second+")";
        return result;
    }

    public String getElementString(){
        return first.toString();
    }
}
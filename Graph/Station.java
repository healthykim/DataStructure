import java.util.ArrayList;
import java.util.PriorityQueue;

public class Station implements Comparable<Station>{
    public ArrayList<Pair<Station, Integer>> adjStation;
    public final String name;
    public final String id;
    public final String line;
    public int distance = Integer.MAX_VALUE;
    public Station prev;


    public Station(String stationId, String stationName, String stationLine){
        adjStation = new ArrayList<>();
        name = stationName;
        id = stationId;
        line = stationLine;
    }

    public boolean isEmpty(){
        return adjStation.isEmpty();
    }

    public int sameId(String id){
        return this.id.compareTo(id);
    }

    @Override
    public int compareTo(Station o) {
        if(o.distance==distance)
            return this.id.compareTo(o.id);
        else if(distance<o.distance)
            return -1;
        else
            return 1;
    }
    public String toString(){
        return name;
    }

}

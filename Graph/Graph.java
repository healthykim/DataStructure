import java.util.*;

public class Graph {
    public HashMap<String, Station> stations; //key is id
    public HashMap<String, LinkedList<String>> stationNames; //key is name. linkedlist is id list. for 환승역 체크

    public Graph(){
        stations = new HashMap<>();
        stationNames = new HashMap<>();
    }

    public void addStation(String id, String name, String line){
        stations.put(id, new Station(id, name, line));

        //이름이 같은 station(환승역)의 객체들 사이 에지 처리
        LinkedList<String> idList; //이름이 같은 환승역들의 모임
        if((idList=stationNames.get(name))!=null){
            Station newStation = stations.get(id);
            Pair<Station, Integer> pair = new Pair<>(newStation, 5);
            for(String stationId : idList){
                if(!stationId.equals(id)) {
                    Station station = stations.get(stationId);
                    if(station!=null&&newStation!=null) {
                        station.adjStation.add(new Pair<>(newStation, 5));
                        newStation.adjStation.add(new Pair<>(station, 5));
                    }
                }
            }
            stationNames.get(name).add(id);
        }
        else {
            idList = new LinkedList<>();
            idList.add(id);
            stationNames.put(name, idList);
        }
    }

    public void addEdge(String id1, String id2, int time){
        Station station1, station2;
        station1 = stations.get(id1);
        station2 = stations.get(id2);
        if(station1!=null&&station2!=null) {
            station1.adjStation.add(new Pair<>(station2, time));
        }
    }

    public void Dijkstra(String startName, String endName){
        LinkedList<String> startIds = stationNames.get(startName); //start vertices
        LinkedList<String> endIds = stationNames.get(endName); // end vertices

        PriorityQueue<Station> vertexQueue = new PriorityQueue<>();
        for(String id : startIds){
            stations.get(id).distance = 0;
            vertexQueue.add(stations.get(id));
        }

        HashSet<Station> visited = new HashSet<>(); // check visited
        int endCount = 0; // to determine whether every end vertices is relaxed
        Station endStation = null; //end station
        while(!vertexQueue.isEmpty()&&endCount!=endIds.size()){
            Station closest = vertexQueue.poll();
            visited.add(closest);
            if(closest.name.equals(endName)) {
                if(endStation==null||endStation.distance>closest.distance){
                    endStation = closest;
                }
                endCount++;
            }
            else {
                for (Pair<Station, Integer> pair : closest.adjStation) {
                    Station adjStation = pair.first;
                    if (!visited.contains(adjStation)) {
                        if ((closest.distance + pair.second) < adjStation.distance) {
                            adjStation.distance = closest.distance + pair.second;
                            adjStation.prev = closest;
                            vertexQueue.add(adjStation);
                        }
                    }
                }
            }
        }

        StringBuilder path = new StringBuilder();
        path = new StringBuilder(endStation.name);
        Station middle = endStation.prev;
        while(!middle.name.equals(startName)){
            if(middle.prev.name.equals(middle.name)){
                path.insert(0, "[" + middle.name + "] ");
                middle = middle.prev.prev;
            }
            else {
                path.insert(0, middle.name + " ");
                middle = middle.prev;
            }
        }
        path.insert(0, startName+" ");
        System.out.println(path);
        System.out.println(endStation.distance);

        /*initialize*/
        for (String id : stations.keySet()) {
            if(stations.get(id).distance!=Integer.MAX_VALUE)
                stations.get(id).distance = Integer.MAX_VALUE;
        }

    }
}

//time
//1. initialize -> distance를 station에 저장하지 말고 아이디별 해쉬 맵으로 저장. 해쉬 맵에 없으면 relaxation 안된 걸로 : |V|
//2.


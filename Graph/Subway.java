import java.io.*;
import java.util.StringTokenizer;

public class Subway {

    static File dataFile; //이거 괜찮은 건지?
    static Graph graph;
    public static void main(String[] args)
    {
        BufferedReader consolReader = new BufferedReader(new InputStreamReader(System.in));

        try
        {
            String datapath = args[0];
            dataFile = new File(datapath);
            BufferedReader dataReader = new BufferedReader(new FileReader(dataFile));
            graph = new Graph();
            String line;
            boolean isEdge = false;
            while ((line = dataReader.readLine())!=null){
                if(line.isBlank()||line.isEmpty()){
                    isEdge = true;
                    continue;
                }
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                String[] tokens= new String[3];
                tokens[0] = tokenizer.nextToken();
                tokens[1] = tokenizer.nextToken();
                tokens[2] = tokenizer.nextToken();

                if(!isEdge) { // add stations(vertice)
                    graph.addStation(tokens[0], tokens[1], tokens[2]);
                }
                else{ // add edges
                    graph.addEdge(tokens[0], tokens[1], Integer.parseInt(tokens[2]));
                }
            }
            dataReader.close();
        }
        catch (IOException e){
            System.err.println("error at datafile");
        }

        while (true)
        {
            try
            {
                String input = consolReader.readLine();
                if (input.compareTo("QUIT") == 0)
                    break;

                command(input);
            }
            catch (IOException e)
            {
                System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
            }
        }
        try {
            consolReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void command(String input){
        String[] points = input.split("\\s");
        String startStation = points[0];
        String endStation = points[1];
        graph.Dijkstra(startStation, endStation);
    }
}

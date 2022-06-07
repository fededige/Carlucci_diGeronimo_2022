package dijkstrausage;

import grafo.Grafo;
import grafo.GrafoException;
import heapminimo.HeapMinimo;
import heapminimo.HeapMinimoException;
import dijkstra.Dijkstra;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.lang.Math;
import java.util.ArrayList;


public class DijkstraUsage{
    private static final Charset ENCODING = StandardCharsets.UTF_8;

    private static void loadMap(String filepath, Grafo<CityName, Double> maps) throws IOException, GrafoException{
        System.out.println("\nLoading data from file...\n");
        Path inputFilePath = Paths.get(filepath);
        try(BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)){
            String line = null;
            while((line = fileInputReader.readLine()) != null){
                String[] lineElements = line.split(",");
                CityName departure_city = new CityName(lineElements[0]);
                CityName arrival_city = new CityName(lineElements[1]);
                Double distance = Double.parseDouble(lineElements[2]);
                //System.out.println(lineElements[0] + " " + lineElements[1] + " " + lineElements[2]);
                maps.addNode(departure_city);
                maps.addEdge(departure_city, arrival_city, distance);
            }
        }
        System.out.println("\nData loaded\n");
    }

    private static void testFunction(String filepath) throws IOException, HeapMinimoException, GrafoException{
        Grafo<CityName, Double> maps = new Grafo<>(0);
        ArrayList<CityName> adj = new ArrayList<>();
        loadMap(filepath, maps);

        /*System.out.println("peso: " + maps.getLabel(new CityName("petraio"), new CityName("monteriggioni")));
        System.out.println(maps.containsEdge(new CityName("petraio"), new CityName("monteriggioni")));
        System.out.println(maps.containsEdge(new CityName("monteriggioni"), new CityName("petraio")));*/
        //System.out.println(maps.getAdj(new CityName("abbadia")));









        Dijkstra<CityName> dijkstra = new Dijkstra<>(maps);
        ArrayList<Vertex<CityName>> shortestpath = dijkstra.ShortestPath(new CityName("torino"));
        System.out.println(shortestpath);
    }

    public static void main(String[] args) throws IOException, HeapMinimoException, GrafoException, Exception {
        if(args.length < 1)
            throw new Exception("Usage: DijkstraUsage <file_name>");

        testFunction(args[0]);
    }
}
// java -jar ./build/Dijkstra.jar /home/federico/Documents/es4_dataset/graph_prova.csv


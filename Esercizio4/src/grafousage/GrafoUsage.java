package grafousage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import grafo.Grafo;
import grafo.GrafoException;
import java.lang.Math;
import java.util.ArrayList;

public class GrafoUsage{
    private static final Charset ENCODING = StandardCharsets.UTF_8;

    private static void loadMap(String filepath, Grafo<CityName, Distance> maps) throws IOException, GrafoException{
        System.out.println("\nLoading data from file...\n");
        Path inputFilePath = Paths.get(filepath);
        try(BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)){
            String line = null;
            while((line = fileInputReader.readLine()) != null){
                String[] lineElements = line.split(",");
                CityName departure_city = new CityName(lineElements[0]);
                CityName arrival_city = new CityName(lineElements[1]);
                Distance distance = new Distance(Double.parseDouble(lineElements[2]));
                //System.out.println(lineElements[0] + " " + lineElements[1] + " " + lineElements[2]);
                maps.addNode(departure_city);
                maps.addEdge(departure_city, arrival_city, distance);
            }
        }
        System.out.println("\nData loaded\n");
    }

    private static void testFunction(String filepath) throws IOException, GrafoException{
        Grafo<CityName, Distance> maps = new Grafo<>(0);
        loadMap(filepath, maps);
        maps.printMap(); //momentaneo
        System.out.println(maps.containsNode(new CityName("monteriggioni")));
        System.out.println(maps.containsEdge(new CityName("monteriggioni"), new CityName("petraio"), new Distance(4040.6818551679175)));
        maps.removeEdge(new CityName("petraio"), new CityName("monteriggioni"), new Distance(4040.6818551679175));
        maps.printMap(); //momentaneo
    }
    
    public static void main(String[] args) throws IOException, GrafoException, Exception{
        if(args.length < 1){
            throw new Exception("Usage: GrafoUsage <file_name>");
        }
        testFunction(args[0]);
    }
}


// java -jar ./build/Grafo.jar /home/federico/Documents/es4_dataset/graph_prova.csv
// java -jar ./build/Grafo_Test.jar

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
        ArrayList<CityName> adj = new ArrayList<>();
        loadMap(filepath, maps);
        //maps.printMap(); //momentaneo
        /*System.out.println(maps.containsNode(new CityName("monteriggioni")));
        System.out.println(maps.containsEdge(new CityName("monteriggioni"), new CityName("petraio"), new Distance(4040.6818551679175)));
        */
        //System.out.println("La mappa contiene l'arco tra Francavilla Fontana e Villa Castelli: " + maps.containsEdge(new CityName("francavilla fontana"), new CityName("villa castelli")));
        //System.out.println("La mappa contiene l'arco tra Torino e Lingotto: " + maps.containsEdge(new CityName("torino"), new CityName("lingotto")));
        //System.out.println("La mappa contiene l'arco tra Napoli e Pianura: " + maps.containsEdge(new CityName("napoli"), new CityName("pianura")));
        /*System.out.println("archi nella mappa: " + maps.getEdges());
        System.out.println("Nodi nel grafo: " + maps.getVertices());*/
        System.out.println("Numero archi: " + maps.numEdge());
        System.out.println("Numero di vertici: " + maps.numVertex());
        /*adj = maps.getAdj(new CityName("villa castelli"));
        System.out.println("adiacenti di villa castelli" + adj);*/
        //System.out.println("peso: " + maps.getLabel(new CityName("villa castelli"), new CityName("francavilla fontana")));
        //System.out.println("peso: " + maps.getLabel(new CityName("torino"), new CityName("lingotto")));
        //maps.removeVertex(new CityName("monteriggioni"));
        //maps.printMap(); //momentaneo
    }
    
    public static void main(String[] args) throws IOException, GrafoException, Exception{
        if(args.length < 1){
            throw new Exception("Usage: GrafoUsage <file_name>");
        }
        testFunction(args[0]);
    }
}


// java -jar ./build/Grafo.jar /home/federico/Documents/es4_dataset/graph_prova.csv
// java -jar ./build/GrafoTests.jar

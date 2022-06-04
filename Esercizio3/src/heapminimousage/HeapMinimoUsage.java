package heapminimousage;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import heapminimo.HeapMinimo;
import heapminimo.HeapMinimoException;
import java.lang.Math;
import java.util.ArrayList;


public class HeapMinimoUsage {
    private static final Charset ENCODING = StandardCharsets.UTF_8;

    private static void printHeap(HeapMinimo<Record> heapminimo) throws HeapMinimoException{
        Record currRec = null;
        int sizeHeap;

        System.out.println("\nORDERED ARRAY OF RECORDS\n");
        sizeHeap = heapminimo.size();

        for(int i = 0; i <= (int)(Math.log(sizeHeap) / Math.log(2)); i++){
            for(int j = 0; j < Math.pow(2,i) && j + Math.pow(2,i) <= sizeHeap; j++){
                currRec = heapminimo.getElement(j+(int)Math.pow(2,i)-1);
                System.out.print(" <"+currRec.getIntegerField()+"> ");
            }
            System.out.println();
        }
  } 

    private static void loadArray(String filepath, HeapMinimo<Record> heapminimo, ArrayList<Record> arrayelement)
        throws IOException, HeapMinimoException{
        System.out.println("\nLoading data from file...\n");

        Path inputFilePath = Paths.get(filepath);
        try(BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)){
            String line = null;
            while((line = fileInputReader.readLine()) != null){
                Record record1 = new Record(Integer.parseInt(line));
                arrayelement.add(record1);
                heapminimo.HeapInsert(record1);
            }
        }
        System.out.println("\nData loaded\n");
    }

    private static void testWithComparisonFunction(String filepath, Comparator<Record> comparator)
            throws IOException, HeapMinimoException{
        HeapMinimo<Record> heapminimo = new HeapMinimo<>(comparator);
        ArrayList<Record> arrayelement = new ArrayList<>();
        loadArray(filepath, heapminimo, arrayelement);
        printHeap(heapminimo);
                
        Record res, el;
        el = arrayelement.get(6);
        res = heapminimo.getRightChild(arrayelement.get(6));
        System.out.println("\nElemento destro di "+ el.getIntegerField() +": "+res.getIntegerField());

        res = heapminimo.extractMin();
        System.out.println("\n Minimo nell'heap: " + res.getIntegerField());
        printHeap(heapminimo);

        el = arrayelement.get(6);
        res = heapminimo.getRightChild(arrayelement.get(6));
        System.out.println("\nElemento destro di "+ el.getIntegerField() +": "+res.getIntegerField());

        Record tmp = new Record(0);
        heapminimo.subtractValue(arrayelement.get(0), tmp);
        System.out.println("modifica di "+ arrayelement.get(0).getIntegerField() +" in " + tmp.getIntegerField());
        arrayelement.set(0, tmp); //salvo il nuovo valore per eventuali riferimenti futuri
        printHeap(heapminimo);

        el = arrayelement.get(0);
        res = heapminimo.getRightChild(arrayelement.get(0));
        System.out.println("\nElemento destro di "+ el.getIntegerField() +": "+res.getIntegerField());

        res = heapminimo.extractMin();
        System.out.println("\n Minimo nell'heap: " + res.getIntegerField());
    }
  
    public static void main(String[] args) throws IOException, HeapMinimoException, Exception {
        if(args.length < 1)
            throw new Exception("Usage: HeapMinimoUsage <file_name>");

        testWithComparisonFunction(args[0],new RecordComparatorIntField());
    }

}
// java -jar ./build/HeapMinimo.jar /home/federico/Documents/es3_dataset/heap.txt
// java -jar ./build/HeapMinimo_Test.jar
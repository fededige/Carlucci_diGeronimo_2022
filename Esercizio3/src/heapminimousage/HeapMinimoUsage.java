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

    private static void loadArray(String filepath, HeapMinimo<Record> heapminimo)
        throws IOException, HeapMinimoException{
        System.out.println("\nLoading data from file...\n");

        Path inputFilePath = Paths.get(filepath);
        try(BufferedReader fileInputReader = Files.newBufferedReader(inputFilePath, ENCODING)){
            String line = null;
            while((line = fileInputReader.readLine()) != null){
                Record record1 = new Record(Integer.parseInt(line));
                heapminimo.HeapInsert(record1);
            }
        }
        System.out.println("\nData loaded\n");
    }

    private static void testWithComparisonFunction(String filepath, Comparator<Record> comparator)
            throws IOException, HeapMinimoException{
        HeapMinimo<Record> heapminimo = new HeapMinimo<>(comparator);
        loadArray(filepath, heapminimo);
        printHeap(heapminimo);
        Record res;
        res = heapminimo.getRightChild(new Record(6));
        System.out.println("\nElemento destro di "+6+": "+res.getIntegerField());
        res = heapminimo.getRightChild(new Record(12));
        System.out.println("\nElemento destro di "+12+": "+res.getIntegerField());
        res = heapminimo.getRightChild(new Record(18));
        System.out.println("\nElemento destro di "+18+": "+res.getIntegerField());
        res = heapminimo.getLeftChild(new Record(4));
        System.out.println("Elemento sinistro "+4+": "+res.getIntegerField());
        res = heapminimo.getLeftChild(new Record(12));
        System.out.println("Elemento sinistro "+12+": "+res.getIntegerField());
        res = heapminimo.getLeftChild(new Record(18));
        System.out.println("Elemento sinistro "+18+": "+res.getIntegerField());
        res = heapminimo.getElement(4);
        System.out.println("L'elemento in posizione " + 4 + "e' : " + res.getIntegerField());
        res = heapminimo.getParent(new Record(23));
        System.out.println("Il padre di " + 23 + ": " + res.getIntegerField());
        res = heapminimo.getParent(new Record(18));
        System.out.println("Il padre di " + 18 + ": " + res.getIntegerField());
        res = heapminimo.getParent(new Record(4));
        System.out.println("Il padre di " + 4 + ": " + res.getIntegerField());
        res = heapminimo.getMin();
        System.out.println("Il minimo: " + res.getIntegerField());
        /*heapminimo.printHash();*/
    }
  /*
            <4>0 
         /       \
       <22>1        <6>2 
      /    \        |   \
    <23>3   <45>4  <12>5  <18>6 
    /    \      \
  <30>7  <28>8   <78>9

  */
  
    public static void main(String[] args) throws IOException, HeapMinimoException, Exception {
        if(args.length < 1)
        throw new Exception("Usage: HeapMinimoUsage <file_name>");

        testWithComparisonFunction(args[0],new RecordComparatorIntField());
        /*testWithComparisonFunction(args[0],new RecordComparatorStringField());*/
    }

}
// java -jar ./build/HeapMinimo.jar /home/federico/Documents/es3_dataset/heap.txt
// java -jar ./build/HeapMinimo_Test.jar
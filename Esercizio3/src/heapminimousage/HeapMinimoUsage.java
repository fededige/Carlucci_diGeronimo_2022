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

    int k = 2;
    for(int i = 0;i < sizeHeap; i++){
      currRec = heapminimo.getElement(i);
      System.out.print(" <"+currRec.getIntegerField()+"> ");
      if(i == 0){
        System.out.println();
      }
      if(i == k){
        System.out.println();
        k += (int) Math.pow(2, i);
      } 
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
  }

  /**
   * @param args the command line arguments. It should contain only one argument
   * specifying the filepath of the data file
   */
  public static void main(String[] args) throws IOException, HeapMinimoException, Exception {
    if(args.length < 1)
      throw new Exception("Usage: HeapMinimoUsage <file_name>");

    testWithComparisonFunction(args[0],new RecordComparatorIntField());
    /*testWithComparisonFunction(args[0],new RecordComparatorStringField());*/
  }

}

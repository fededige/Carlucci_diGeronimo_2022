package grafo;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class Grafo_TestsRunner {

  public static void main(String[] args) {
    Result result = JUnitCore.runClasses(Grafo.class);
    for (Failure failure : result.getFailures()) {
      System.out.println(failure.toString());
    }
  
    System.out.println(result.wasSuccessful());
    
  }
  
}
 
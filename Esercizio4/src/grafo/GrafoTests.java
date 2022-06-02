package grafo;

import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class GrafoTests {

  private Integer i1,i2,i3;
  private String a, b, c;
  private Grafo<String, Integer> grafo;

  @Before
  public void createGrafo() throws GrafoException{
    i1 = -12;
    i2 = 0;
    i3 = 4;
    a = "A";
    b = "B";
    c = "C";
    grafo = new Grafo<>(0);
  }
 
  @Test
  public void testnumVertex_zeroEl() throws GrafoException{
    assertTrue(grafo.numVertex() == 0);
  }

  @Test
  public void testnumVertex_threeEl() throws GrafoException {
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addNode(c);
    assertTrue(grafo.numVertex() == 3);
  }

  @Test
  public void testnumEdge_zeroEl() throws GrafoException{
    assertTrue(grafo.numEdge() == 0);
  }

  @Test
  public void testnumEdge_threeEl() throws GrafoException{
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addNode(c);
    grafo.addEdge(a, b, i1);
    grafo.addEdge(b, c, i2);
    grafo.addEdge(a, c, i3);
    assertTrue(grafo.numEdge() == 3);
  }


  @Test
  public void testcontainsNode_oneEl() throws GrafoException{
    grafo.addNode(a);
    assertTrue(grafo.containsNode("A"));
  }

  @Test
  public void testcontainsEdge_oneEl() throws GrafoException{
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addEdge(a, b, i1);
    assertTrue(grafo.containsEdge("A", "B"));
  }

  @Test
  public void testremoveVertex_oneEl() throws GrafoException{
    grafo.addNode(a);
    grafo.removeVertex("A");
    assertTrue(grafo.containsNode("A") == false && grafo.numVertex() == 0);
  }

  @Test
  public void testremoveEdge_oneEl() throws GrafoException{
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addEdge(a, b, i1);
    grafo.removeEdge("A","B");
    assertTrue(grafo.containsEdge("A","B") == false && grafo.numEdge() == 0);
  }
  
  @Test
  public void testgetAdj_threeEl() throws GrafoException{
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addNode(c);
    grafo.addEdge(a, b, i1);
    grafo.addEdge(b, c, i2);
    grafo.addEdge(a, c, i3);
    ArrayList<String> res = grafo.getAdj("A");
    String[] arrExpected = res.toArray(new String[0]);
    String[] arrActual ={"B","C"};

    assertArrayEquals(arrExpected, arrActual);
  }

  @Test
  public void testgetLabel_threeEl() throws GrafoException{
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addNode(c);
    grafo.addEdge(a, b, i1);
    grafo.addEdge(b, c, i2);
    grafo.addEdge(a, c, i3);
  
    assertTrue(grafo.getLabel("A", "C") == i3);
  }
  
  @Test
  public void testgetVertices_threeEl() throws GrafoException{
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addNode(c);
    grafo.addEdge(a, b, i1);
    grafo.addEdge(b, c, i2);
    grafo.addEdge(a, c, i3);
    ArrayList<String> res = grafo.getVertices();
    String[] arrExpected = res.toArray(new String[0]);
    String[] arrActual ={"A","B","C"};

    assertArrayEquals(arrExpected, arrActual);
  }

  @Test
  public void testgetEdge_threeEl() throws GrafoException{
    grafo.addNode(a);
    grafo.addNode(b);
    grafo.addNode(c);
    grafo.addEdge(a, b, i1);
    grafo.addEdge(b, c, i2);
    grafo.addEdge(a, c, i3);
    ArrayList<ArrayList<String>> res = grafo.getEdges();
    String[][] arrExpected = new String[3][2];
    for(int i = 0; i < res.size(); i++){
      for(int j = 0; j < res.get(i).size(); j++){
        arrExpected[i][j] = res.get(i).get(j);
      }
    }
    String[][] arrActual ={{"A", "B"}, {"A", "C"}, {"B","C"}};

    assertEquals(arrExpected, arrActual);
  }
  
}


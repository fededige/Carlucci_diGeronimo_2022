package dijkstrausage;

import java.util.Comparator;

public class VertexComparatorWeightField implements Comparator<Vertex>{
  @Override
  public int compare(Vertex vertex1 , Vertex vertex2){
    return Double.valueOf(vertex1.getWeight()).compareTo(vertex2.getWeight());
  }
}

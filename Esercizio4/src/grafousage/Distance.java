package grafousage;

import java.util.Objects;

public class Distance {
  private double distance;
  
  
  public Distance(double distance){
    this.distance = distance;
  }
  
  public double getDistance(){
    return this.distance;
  }  
  
  @Override
  public boolean equals(Object dist){
    if(!(dist instanceof Distance))
      return false;
    return this.distance == ((Distance)dist).getDistance();
  }

  /*@Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.distance);
    return hash;
  }*/
}
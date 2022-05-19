package heapminimousage;

import java.util.Objects;

public class Record {
  private int integerField;
  
  
  public Record(int integerField){
    this.integerField = integerField;
  }
  
  public int getIntegerField(){
    return this.integerField;
  }  
  
  @Override
  public boolean equals(Object rec){
    if(!(rec instanceof Record))
      return false;
    return this.integerField == ((Record)rec).getIntegerField();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + this.integerField;
    return hash;
  }
}

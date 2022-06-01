package grafousage;

import java.util.Objects;

public class CityName {
  private String name;
  
  
  public CityName(String name){
    this.name = name;
  }
  
  public String getCity(){
    return this.name ;
  }  
  
  @Override
  public boolean equals(Object city){
    if(!(city instanceof CityName))
      return false;
    return this.name.equals(((CityName)city).getCity());
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + Objects.hashCode(this.name);
    return hash;
  }

  @Override
  public String toString() {
    return this.name;
  }
}
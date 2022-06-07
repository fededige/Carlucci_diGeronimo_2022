package dijkstrausage;

import java.util.Objects;

public class Vertex<K>{
    private Double weight;
    private K name;
    private K pi;

    public Vertex(Double weight, K name, K pi){
        this.weight = weight; // idea 0 nel caso non pesato
        this.name = name;
        this.pi = pi;
    }

    public K getName(){
        return this.name;
    }

    public Double getWeight(){
        return this.weight;
    }

    public K getPi(){
        return this.pi;
    }

    public void setPi(K newPi){
        this.pi = newPi;
    }
    
    public void setWeight(Double newWeight){
        this.weight= newWeight;
    }

    @Override
    public boolean equals(Object vertex){
        if(!(vertex instanceof Vertex))
            return false;
        return ((this.name).equals(((Vertex)vertex).getName()) && (this.weight).equals(((Vertex)vertex).getWeight()));
    } 
    
    @Override
  public String toString() {
    return this.pi + " " + this.name + " " + this.weight;
  }

}
package dijkstrausage;

public class Nodo<T>{
    T name;
    T pi;
    Double dist;

    public Nodo(T name, T pi, Double dist){
        this.name=name;
        this.pi=pi;
        this.dist=dist;
    }
    
    public T getName(){
        return this.name;
    }
    
    public T getPi(){
        return this.pi;
    }

    public Double getDistance(){
        return this.dist;
    }

    public void setDistance(Double newDist){
        this.dist = newDist;
    }

    public void setPi(T newPi){
        this.pi = newPi;
    }

    public void setName(T newName){
        this.name = newName;
    }
}
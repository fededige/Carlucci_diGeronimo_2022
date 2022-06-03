package dijkstra;

public class Node<T, K>{
    private T weight;
    private K name;
    private K pi;

    public Node(T weight, K name, K pi){
        this.weight = weight; // idea 0 nel caso non pesato
        this.name = name;
        this.pi = pi;
    }

    public K getName(){
        return this.name;
    }

    public T getWeight(){
        return this.weight;
    }

    public K getPi(){
        return this.Pi;
    }

    public void setPi(K newPi){
        this.Pi = newPi;
    }
    
    public void setWeight(T newWeight){
        this.weight= newWeight;
    }

    @Override
    public boolean equals(Object node){
        if(!(node instanceof Node))
            return false;
        return ((this.name).equals(((Node)node).getName()) && (this.weight).equals(((Node)node).getWeight()));
    }
}
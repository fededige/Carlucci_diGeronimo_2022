package grafo;

public class Node<T, K>{
    private T weight;
    private K name;

    public Node(T weight, K name){
        this.weight = weight;
        this.name = name;
    }

    public K getName(){
        return this.name;
    }

    public T getWeight(){
        return this.weight;
    }

    @Override
    public boolean equals(Object node){
        if(!(node instanceof Node))
            return false;
        return ((this.name).equals(((Node)node).getName()) && (this.weight).equals(((Node)node).getWeight()));
    }
}
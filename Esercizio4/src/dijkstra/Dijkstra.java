package dijkstra;

import grafo.Grafo;
import Esercizio3.src.heapminimo.HeapMinimo;

public class Dijkstra<K>{
    private Grafo<K, Integer> grafo = null;
    private HeapMinimo<Node<Integer, K>> MinPriorityQueue = null;
    private K source;
    public Dijkstra(Grafo<K, Integer> grafo, K source){
        this.grafo = grafo;
        this.source = source;
        this.MinPriorityQueue = new HeapMinimo<Node<>>();
    }
    
    private void insertMPQ(K source){
        if(grafo.containsNode(source) == false)
            //throw new ...
        ArrayList<K> vertices = grafo.getVertices();
        for(int i=0;i< vertices.size() ; i++){
            if(vertices.get(i).equals(source)){
                MinPriorityQueue.HeapInsert(new Node(0, vertices.get(i), null));
            }
            else{
                MinPriorityQueue.HeapInsert(new Node(Double.POSITIVE_INFINITY, vertices.get(i), null));
            }
        }
    }
    /*
    private Vertex  predecessore=null;
    private boolean visitato=false;
    private K distanza = 0;*/
    
}


package dijkstra;

import grafo.Grafo;
import Esercizio3.src.heapminimo.HeapMinimo;
import java.util.ArrayList;

public class Dijkstra<K>{
    private Grafo<K, Integer> grafo = null;
    private HeapMinimo<Node<Integer, K>> MinPriorityQueue = null;
    private ArrayList<Node<Integer, K> > vertices = null;
    private K source;

    public Dijkstra(Grafo<K, Integer> grafo, K source){
        this.grafo = grafo;
        this.source = source;
        this.MinPriorityQueue = new HeapMinimo<>(); //passare la compare
        this.vertices = new ArrayList<>();
    }
    
    private void insertMPQ(K source){
        /*if(grafo.containsNode(source) == false)
            //throw new ...*/
        ArrayList<K> tempVertices = grafo.getVertices();
        for(int i = 0;i < tempVertices.size(); i++){
            if((this.vertices).get(i).equals(source)){
                MinPriorityQueue.HeapInsert(new Node(0, tempVertices.get(i), null));
                (this.vertices).add(new Node(0, tempVertices.get(i), null));
            }
            else{
                MinPriorityQueue.HeapInsert(new Node(Double.POSITIVE_INFINITY, tempVertices.get(i), null));
                (this.vertices).add(new Node(Double.POSITIVE_INFINITY, tempVertices.get(i), null));
            }
        }
    }

    public ArrayList<Node<Integer, K>> ShortestPath(K source){
        ArrayList<Node<Integer, K>> adj = new ArrayList<>();
        Node<Integer, K> currentNode = new Node<>();
        Integer currentWeight = 0;
        int index=0;
        insertMPQ(source);
        while(MinPriorityQueue.isEmpty() == false){
            currentNode = MinPriorityQueue.extractMin();
            adj = grafo.getAdj(currentNode);
            currentWeight = currentNode.getWeight();
            for(int i = 0; i < adj.size(); i++){ //aggiugerte la equals in Node
                index = vertices.indexOf(adj.get(i));
                if(MinPriorityQueue.containsValue(adj.get(i)) && (currentWeight + grafo.getLabel(currentNode.getName(), adj.get(i).getName()) < vertices.get(index).getWeight())){
                    vertices.get(index).setWeight(currentWeight + grafo.getLabel(currentNode.getName(), adj.get(i).getName()));
                    vertices.get(index).setPi(currentNode.getName());
                    MinPriorityQueue.subtractValue(adj.get(i), new Node(vertices.get(index).getWeight(), adj.get(i).getName(), vertices.get(index).getPi()));
                }
            }
        }
        return vertices;
    }
    
    
}


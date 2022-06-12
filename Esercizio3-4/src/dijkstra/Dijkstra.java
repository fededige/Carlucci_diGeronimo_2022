package dijkstra;

import grafo.Grafo;
import grafo.GrafoException;
import heapminimo.HeapMinimo;
import heapminimo.HeapMinimoException;
import java.util.ArrayList;
import java.util.Comparator;
import dijkstrausage.Vertex;
import dijkstrausage.VertexComparatorWeightField;

public class Dijkstra<K>{
    private Grafo<K, Double> grafo = null;
    private HeapMinimo<Vertex<K>> MinPriorityQueue = null;
    private ArrayList<Vertex<K>> vertices = null;

    public Dijkstra(Grafo<K, Double> grafo) throws HeapMinimoException{
        this.grafo = grafo;
        this.MinPriorityQueue = new HeapMinimo<>(new VertexComparatorWeightField()); //passare la compare
        this.vertices = new ArrayList<>();
    }
    
    private void insertMPQ(K source) throws HeapMinimoException, DijkstraException{
        if(grafo.containsNode(source) == false)
            throw new DijkstraException("sourse not exists in graph");
        ArrayList<K> tempVertices = grafo.getVertices();
        
        for(int i = 0;i < tempVertices.size(); i++){
            if(tempVertices.get(i).equals(source)){
                MinPriorityQueue.HeapInsert(new Vertex(0.0, tempVertices.get(i), null));
                (this.vertices).add(new Vertex(0.0, tempVertices.get(i), null));
            }
            else{
                MinPriorityQueue.HeapInsert(new Vertex(Double.POSITIVE_INFINITY, tempVertices.get(i), null));
                (this.vertices).add(new Vertex(Double.POSITIVE_INFINITY, tempVertices.get(i), null));
            }
        }        
    }
   
    public ArrayList<Vertex<K>> ShortestPath(K source) throws HeapMinimoException, GrafoException, DijkstraException{
        ArrayList<K> adj = new ArrayList<>();
        Vertex<K> currentNode = null;
        Double currentWeight = null;
        int index=0,k=0,j=0;
        boolean flag=false;
        insertMPQ(source);
        while(MinPriorityQueue.isEmpty() == false){
            currentNode = MinPriorityQueue.extractMin();
            currentWeight = currentNode.getWeight();
            adj = grafo.getAdj(currentNode.getName());
            for(int i = 0; i < adj.size(); i++){ //aggiugerte la equals in Node
                flag=false;
                for(j = 0; j < MinPriorityQueue.size(); j++){
                    if(MinPriorityQueue.getElement(j).getName().equals(adj.get(i))){
                       flag = true;
                       break;
                    }
                }
                if(flag == true){
                    if((currentWeight + grafo.getLabel( currentNode.getName(), adj.get(i) )) < ((Double)MinPriorityQueue.getElement(j).getWeight())){
                        for(k = 0; k < vertices.size(); k++){
                            if(vertices.get(k).getName().equals(MinPriorityQueue.getElement(j).getName())){
                                vertices.get(k).setWeight(currentWeight + grafo.getLabel(currentNode.getName(), adj.get(i)));
                                vertices.get(k).setPi(currentNode.getName());
                                break;
                            }
                        }
                        MinPriorityQueue.subtractValue(MinPriorityQueue.getElement(j), new Vertex(vertices.get(k).getWeight(),vertices.get(k).getName(), vertices.get(k).getPi()));
                    }
                }
            }
        }
        return vertices;
    }            
}
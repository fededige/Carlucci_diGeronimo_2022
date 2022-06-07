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

    /*public T add(T x, T y){
        Double sum;
        sum = ((Number)x).doubleValue() + ((Number)y).doubleValue();
        return (T) sum;
    }*/
    
    private void insertMPQ(K source) throws HeapMinimoException {
        /*if(grafo.containsNode(source) == false)
            //throw new ...*/
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
   
    public ArrayList<Vertex<K>> ShortestPath(K source) throws HeapMinimoException, GrafoException{
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
            //System.out.println("gli adiacenti di " + currentNode.getName() + "sono: " + adj);
            for(int i = 0; i < adj.size(); i++){ //aggiugerte la equals in Node
                flag=false;
                for(j = 0; j < MinPriorityQueue.size(); j++){
                    //System.out.println(MinPriorityQueue.getElement(j).getName() + " == " + adj.get(i));
                    if(MinPriorityQueue.getElement(j).getName().equals(adj.get(i))){
                       flag = true;
                       break;
                    }
                }
                if(flag == true){
                //System.out.println("elemento nella mq " + MinPriorityQueue.getElement(j).getName());
                    /*System.out.println("peso" + currentWeight);
                    System.out.println("peso dell'arco: " + grafo.getLabel( currentNode.getName(), adj.get(i) ));
                    System.out.println("peso nella MQ: " + (MinPriorityQueue.getElement(j).getName()));*/
                    if((currentWeight + grafo.getLabel( currentNode.getName(), adj.get(i) )) < ((Double)MinPriorityQueue.getElement(j).getWeight())){
                        for(k = 0; k < vertices.size(); k++){
                            //System.out.println("vertices: " + vertices.get(k).getName() + " currentNode: " + MinPriorityQueue.getElement(j).getName());
                            if(vertices.get(k).getName().equals(MinPriorityQueue.getElement(j).getName())){
                                vertices.get(k).setWeight(currentWeight + grafo.getLabel(currentNode.getName(), adj.get(i)));
                                vertices.get(k).setPi(currentNode.getName());
                                //System.out.println("vertices: " + vertices.get(k).getName() + " currentNode: " + MinPriorityQueue.getElement(j).getName());
                                //System.out.println("vertices: " + vertices.get(k).getName() + " PI: " +vertices.get(k).getPi()+" peso: "+vertices.get(k).getWeight() );
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


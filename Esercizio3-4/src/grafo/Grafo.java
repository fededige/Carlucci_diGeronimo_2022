package grafo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

public class Grafo<K, T>{
    public ArrayList<LinkedList<Node<T, K>>> listaAdiacenza = null;
    private Hashtable<K, Integer> indecesMap;
    private int mode;//0 = diretto, 1 = indiretto

    public Grafo(int mode) throws GrafoException{
        if(mode != 0 && mode != 1)
            throw new GrafoException("mode deve essere compresa tra 0 e 1");

        (this.mode) = mode;
        listaAdiacenza = new ArrayList<>();
        indecesMap = new Hashtable<>();
    }
    //ritorna se il grafo è diretto o indiretto
    public int getMode(){
        return this.mode;
    }
    //aggiunta di un vertice
    public void addVertex(K nameFrom){
        int indexFrom;
        if((this.indecesMap).containsKey(nameFrom) == false){
            (this.listaAdiacenza).add(new LinkedList<>());
            indexFrom = listaAdiacenza.size() - 1;
            (this.listaAdiacenza).get(indexFrom).add(new Node(null, nameFrom));
            (this.indecesMap).put(nameFrom, indexFrom);
        } 
    }
    //aggiunta di un arco
    public void addEdge(K nameFrom, K nameTo, T weight) throws GrafoException{
        if((this.indecesMap).containsKey(nameFrom) == false){
            throw new GrafoException("vertice partenza inesistente");
        }
        if(containsVertex(nameTo) == false){
            addVertex(nameTo);
        }
        int indexFrom;
        indexFrom = indecesMap.get(nameFrom);
        listaAdiacenza.get(indexFrom).add(new Node(weight, nameTo));
        
        if(mode == 1 ){
            indexFrom = indecesMap.get(nameTo);
            listaAdiacenza.get(indexFrom).add(new Node(weight, nameFrom));
        }
    }
    //controlla se un vertice esiste 
    public boolean containsVertex(K vertex){
        return (this.indecesMap).containsKey(vertex);
    }
    //controlla se un arco esiste 
    public boolean containsEdge(K vertexFrom, K vertexTo) throws GrafoException{
        if (containsVertex(vertexFrom) == false)
            throw new GrafoException("Vertice partenza inesistente");
        if (containsVertex(vertexTo) == false)
            throw new GrafoException("Vertice arrivo inesistente");
        int indexFrom = (this.indecesMap).get(vertexFrom);
        boolean res =  false;
        for(int i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
            if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexTo)){
                res = true;
            }
        }
        return res;
    }
    //rimozione di un arco (se esiste)
    public void removeEdge(K vertexFrom, K vertexTo) throws GrafoException{
        if(containsEdge(vertexFrom, vertexTo) == false)
            throw new GrafoException("arco inesistente");
        int indexFrom = 0, i = 0;
        boolean flag = false;
        indexFrom = (this.indecesMap).get(vertexFrom);
        for(i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
            if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexTo)){
                break;
            }
        }
        (this.listaAdiacenza).get(indexFrom).remove(i);
        if(mode == 1){
           int indexTo = (this.indecesMap).get(vertexTo);
           for(i = 0; i < (this.listaAdiacenza).get(indexTo).size(); i++){
                if((this.listaAdiacenza).get(indexTo).get(i).getName().equals(vertexFrom)){
                    break;
                }
            }
            (this.listaAdiacenza).get(indexTo).remove(i);
        }
    }
    //rimuoviamo un vertice
    public void removeVertex(K vertex) throws GrafoException{
        if(containsVertex(vertex) == false)
            throw new GrafoException("vertice inesistente");
        int index = (this.indecesMap).get(vertex);
        K vertexTo = null;
        for(int i = 0; i < listaAdiacenza.size(); i++){
            vertexTo = listaAdiacenza.get(i).getFirst().getName();
            if(containsEdge(vertex,vertexTo))
                removeEdge(vertex,vertexTo );
        }
        (this.listaAdiacenza).remove(index);
        (this.indecesMap).remove(vertex);
    }
    //ritorna il numero di vertici
    public int numVertex(){
        return (this.listaAdiacenza).size();
    }
    //ritorna il numero di archi
    public int numEdge(){
        int countEdge=0;
        for(int i = 0; i < (this.listaAdiacenza).size(); i++){
            countEdge += (this.listaAdiacenza).get(i).size() - 1;
        }
        if(mode == 1)
            return countEdge/2;

        return countEdge;
    }
    //ritorna una lista di adiacenti
    public ArrayList<K> getAdj(K vertex) throws GrafoException{
        if(containsVertex(vertex) == false)
            throw new GrafoException("vertice inesistente");
        int index = (this.indecesMap).get(vertex);
        ArrayList<K> adj = new ArrayList<>();
        for(int i = 1; i < (this.listaAdiacenza).get(index).size(); i++){
            adj.add((this.listaAdiacenza).get(index).get(i).getName());
        }
        return adj;
    }
    //ritorna il peso di un arco
    public T getLabel(K vertexFrom, K vertexTo) throws GrafoException{
        if (containsEdge(vertexFrom,vertexTo) == false)
            throw new GrafoException("Arco inesistente");
        int i=0;
        int indexFrom = 0;
        T res = null;
        indexFrom = (this.indecesMap).get(vertexFrom);
        for(i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
            if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexTo)){
                res = (this.listaAdiacenza).get(indexFrom).get(i).getWeight();
            }
        }
        return res;
    }
    //Recupero vertici
    public ArrayList<K> getVertices(){
        ArrayList<K> vertices = new ArrayList<>();
        for(int i = 0; i < (this.listaAdiacenza).size(); i++){
            vertices.add((this.listaAdiacenza).get(i).get(0).getName());
        }
        return vertices;
    }
    //Recupero archi
    public ArrayList< ArrayList<K> > getEdges(){
        ArrayList< ArrayList<K> > vertices = new ArrayList<>();
        ArrayList<K> edge = null;
        for(int i = 0; i < (this.listaAdiacenza).size(); i++){
            K app = (this.listaAdiacenza).get(i).getFirst().getName();
            for(int j = 1; j < (this.listaAdiacenza).get(i).size(); j++){
                edge = new ArrayList<>();
                edge.add(app);
                edge.add((this.listaAdiacenza).get(i).get(j).getName());
                vertices.add(edge);
            }
        }
        return vertices;
    }
}
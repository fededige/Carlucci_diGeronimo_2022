package grafo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;

/*                   
 
Milano, Torino, 5125.64
Milano, Vercelli, 2233.5
Milano, Roma, 3000.89

[Milano,  null],      [Roma, null]
|                   |
[Torino, 5125.64]   [Milano, 3000.89]
|
[Vercelli, 2233.5]
|
[Roma, 3000.89]
*/

public class Grafo<K, T>{
    public ArrayList<LinkedList<Node<T, K>>> listaAdiacenza = null;
    private Hashtable<K, Integer> indecesMap;
    private int mode;//0 = diretto, 1 = indiretto

    public Grafo(int mode) throws GrafoException{
        if(mode == 0 || mode == 1)
            (this.mode) = mode;
        else throw new GrafoException("mode deve essere compresa tra 0 e 1");
        listaAdiacenza = new ArrayList<>();
        indecesMap = new Hashtable<>();
    }

    public int getMode(){
        return this.mode;
    }
    /*idea: accedere alle posizioni di listaAdiacenza con un hashmap per dedurre gli indici dai nomi delle città*/
    public void addNode(K nameFrom){
        int indexFrom;
        if((this.indecesMap).containsKey(nameFrom) == false){
            (this.listaAdiacenza).add(new LinkedList<>());
            indexFrom = listaAdiacenza.size() - 1;
            (this.listaAdiacenza).get(indexFrom).add(new Node(null, nameFrom));
            (this.indecesMap).put(nameFrom, indexFrom);
            System.out.println(indecesMap);
        }
    }

    public void addEdge(K nameFrom, K nameTo, T weight) throws GrafoException{
        int indexFrom;
        if((this.indecesMap).containsKey(nameFrom) == false){
            throw new GrafoException("Nodo partenza inesistente");
        }
        if(containsNode(nameTo) == false){
            addNode(nameTo);
        }
        indexFrom = indecesMap.get(nameFrom);
        listaAdiacenza.get(indexFrom).add(new Node(weight, nameTo));
    }

    public boolean containsNode(K vertex){
        return (this.indecesMap).containsKey(vertex);
    }

   /* public boolean containsEdge(K vertexFrom, K vertexTo, T weight) throws GrafoException{
        if (containsNode(vertexFrom) == false)
            throw new GrafoException("Nodo partenza inesistente");
        if (containsNode(vertexTo) == false)
            throw new GrafoException("Nodo arrivo inesistente");
        int indexFrom = (this.indecesMap).get(vertexFrom);
        boolean res =  (this.listaAdiacenza).get(indexFrom).contains(new Node(weight, vertexTo));
        if(mode == 0 && res == false){//grafo indiretto
            int indexTo = (this.indecesMap).get(vertexTo);
            res =  (this.listaAdiacenza).get(indexTo).contains(new Node(weight, vertexFrom));
        }
        return res;
    }*/

    public boolean containsEdge(K vertexFrom, K vertexTo) throws GrafoException{
        if (containsNode(vertexFrom) == false)
            throw new GrafoException("Nodo partenza inesistente");
        if (containsNode(vertexTo) == false)
            throw new GrafoException("Nodo arrivo inesistente");
        int indexFrom = (this.indecesMap).get(vertexFrom);
        boolean res =  false;
        for(int i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
            if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexTo)){
                res = true;
            }
        }
        if(mode == 0 && res == false){//grafo indiretto
            int indexTo = (this.indecesMap).get(vertexTo);
            for(int i = 0; i < (this.listaAdiacenza).get(indexTo).size(); i++){
                if((this.listaAdiacenza).get(indexTo).get(i).getName().equals(vertexFrom)){
                    res = true;
                }
            }
        }
        return res;
    }
    
    /* Controlliamo veramente se l'arco esiste in quel verso */
    /*public void removeEdge(K vertexFrom, K vertexTo, T weight) throws GrafoException{
        if(containsEdge(vertexFrom, vertexTo) == false)
            throw new GrafoException("arco inesistente");
        int indexFrom=0, indexEdge=0;
        boolean flag = false;
        indexFrom = (this.indecesMap).get(vertexFrom);
        if(mode == 0){
            if(!((this.listaAdiacenza).get(indexFrom).contains(new Node(weight,vertexTo)))){
                indexFrom = (this.indecesMap).get(vertexTo);
                indexEdge = (this.listaAdiacenza).get(indexFrom).indexOf(new Node(weight,vertexFrom));
                (this.listaAdiacenza).get(indexFrom).remove(indexEdge);
                flag = true; // ci serve per non entrare nell'if se abbiamo già rimosso l'arco
            }
        } 
        if(flag == false){
            indexEdge = (this.listaAdiacenza).get(indexFrom).indexOf(new Node(weight,vertexTo));
            (this.listaAdiacenza).get(indexFrom).remove(indexEdge);
        }
    }*/


    public void removeEdge(K vertexFrom, K vertexTo) throws GrafoException{
        if(containsEdge(vertexFrom, vertexTo) == false)
            throw new GrafoException("arco inesistente");
        int indexFrom=0, indexEdge=0,i=0;
        boolean flag = false;
        indexFrom = (this.indecesMap).get(vertexFrom);
        if(mode == 0){
            boolean exist = false;
            for(i = 0; i < (this.listaAdiacenza).get(indexFrom).size() && !exist; i++){
                exist = exist || (this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexTo);
            }
            if(!exist){
                indexFrom = (this.indecesMap).get(vertexTo);
                for(i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
                    if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexFrom)){
                        break;
                    }
                }
                (this.listaAdiacenza).get(indexFrom).remove(i);
                flag = true; // ci serve per non entrare nell'if se abbiamo già rimosso l'arco
            }
        } 
        if(flag == false){
            for(i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
                    if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexTo)){
                        break;
                    }
                }
            (this.listaAdiacenza).get(indexFrom).remove(i);
        }
    }
    // da completare
    public void removeVertex(K vertex) throws GrafoException{
        if(containsNode(vertex) == false)
            throw new GrafoException("vertice inesistente");
        int index = (this.indecesMap).get(vertex);
        (this.listaAdiacenza).remove(index);
        for(int i = 0; i < listaAdiacenza.size(); i++){

        }
        (this.indecesMap).remove(vertex);
    }

    public int numVertex(){
        return (this.listaAdiacenza).size();
    }

    public int numEdge(){
        int countEdge=0;
        for(int i=0; i< (this.listaAdiacenza).size(); i++){
            countEdge += (this.listaAdiacenza).get(i).size();
        }
        return countEdge;
    }

    public ArrayList<K> getAdj(K vertex) throws GrafoException{
        if(containsNode(vertex) == false)
            throw new GrafoException("vertice inesistente");
        int index = (this.indecesMap).get(vertex);
        ArrayList<K> adj = new ArrayList<>();
        for(int i = 0; i < (this.listaAdiacenza).get(index).size(); i++){
            adj.add((this.listaAdiacenza).get(index).get(i).getName());
        }
        System.out.println("adiacenti di monteriggioni" + adj);
        return adj;
    }
    //da completare
    public T getLabel(K vertexFrom, K vertexTo) throws GrafoException{
        if (containsEdge(vertexFrom,vertexTo) == false)
            throw new GrafoException("Arco inesistente");
        int i=0;
        int indexFrom = 0;
        T res = null;
        boolean flag=false;
        indexFrom = (this.indecesMap).get(vertexFrom);
        for(i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
            if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexTo)){
                res = (this.listaAdiacenza).get(indexFrom).get(i).getWeight();
                flag = true;
            }
        }
        if(!flag){
            indexFrom = (this.indecesMap).get(vertexTo);
            for(i = 0; i < (this.listaAdiacenza).get(indexFrom).size(); i++){
                if((this.listaAdiacenza).get(indexFrom).get(i).getName().equals(vertexFrom)){
                    res =  (this.listaAdiacenza).get(indexFrom).get(i).getWeight();
                }
            }
        }
        return res;
    }

    public void printMap(){
        for(int i = 0; i < listaAdiacenza.size(); i++){
            LinkedList<Node<T, K>> temp = (this.listaAdiacenza).get(i);
            for(int j = 0; j < temp.size(); j++){
                System.out.print(temp.get(j).getName() + " ");
                System.out.print(temp.get(j).getWeight() + " ");
            }
            System.out.println();
        }
    }
}
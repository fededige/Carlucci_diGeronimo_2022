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
    private int mode;

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
            (this.listaAdiacenza).get(indexFrom).add(new Node(nameFrom, null));
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
        listaAdiacenza.get(indexFrom).add(new Node(nameTo, weight));
    }

    public boolean containsNode(K vertex){
        return (this.indecesMap).containsKey(vertex);
    }

    public boolean containsEdge(K vertexFrom, K vertexTo, T weight) throws GrafoException{
        if (containsNode(vertexFrom) == false)
            throw new GrafoException("Nodo partenza inesistente");
        int indexFrom = (this.indecesMap).get(vertexFrom);
        /*System.out.println("Nodo di partenza: "+(this.listaAdiacenza).get(indexFrom));
        int indexTo = (this.indecesMap).get(vertexTo);
        System.out.println("Nodo Arrivo : "+(this.listaAdiacenza).get(indexTo));*/
        return (this.listaAdiacenza).get(indexFrom).contains(new Node(vertexTo, weight));
    }

    public void printMap(){
        for(int i=0; i< listaAdiacenza.size(); i++){
            LinkedList<Node<T, K>> temp = listaAdiacenza.get(i);
            for(int j = 0; j < temp.size(); j++){
                System.out.print(temp.get(j) + " ");
            }
            System.out.println();
        }
    }
}
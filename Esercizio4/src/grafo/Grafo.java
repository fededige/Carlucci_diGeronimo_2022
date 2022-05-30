package grafo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;

/*                   
 
Milano, Torino, 5125.64
Milano, Vercelli, 2233.5
Milano, Roma, 3000.89

[Milano],           [Roma]
|                   |
[Torino, 5125.64]   [Milano, 3000.89]
|
[Vercelli, 2233.5]
|
[Roma, 3000.89]
*/

public class Grafo<T,K>{
    //idea: creare un arraylist di arraylist
    private class Node<T, K>{
        T weight;
        K name;
        Node<T, K> next;

        public Node(T weight, K name){
            this.weight = weight; // idea 0 nel caso non pesato
            this.name = name;
        }
    }
    public ArrayList<LinkedList<Node<T, K>>> listaAdiacenza = null;
    private int mode;

    public Grafo(int mode) throws GrafoException{
        if(mode == 0 || mode == 1)
            (this.mode) = mode;
        else throw new GrafoException("mode deve essere compresa tra 0 e 1");
        listaAdiacenza = new ArrayList<>();
    }

    public int getMode(){
        return this.mode;
    }
    /*idea: accedere alle posizioni di listaAdiacenza con un hashmap per dedurre gli indici da i nomi delle città*/
    public addNode(T nameFrom, T nameTo, K weight){
        //if nameFrom esiste nell hashmap do
        //    indexFrom = hashmap.get(nameFrom);
        //else
        //    listaAdiacenza.add(nameFrom);
        //hashmap.add(nameFrom, listaAdiacenza.size() - 1);


        //listaAdiacenza[indexFrom].add(indexTo, weight);
                                        //nodo
    }
    
}
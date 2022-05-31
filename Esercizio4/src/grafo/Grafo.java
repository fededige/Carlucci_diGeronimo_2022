package grafo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.HashMap;

/*                   
 
Milano, Torino, 5125.64
Milano, Vercelli, 2233.5
Milano, Roma, 3000.89

[Milano,  -1],      [Roma, -1]
|                   |
[Torino, 5125.64]   [Milano, 3000.89]
|
[Vercelli, 2233.5]
|
[Roma, 3000.89]
*/

public class Grafo<K, T>{
    public ArrayList<LinkedList<Node<T, K>>> listaAdiacenza = null;
    private HashMap<K, Integer> indecesMap;
    private int mode;

    public Grafo(int mode) throws GrafoException{
        if(mode == 0 || mode == 1)
            (this.mode) = mode;
        else throw new GrafoException("mode deve essere compresa tra 0 e 1");
        listaAdiacenza = new ArrayList<>();
        indecesMap = new HashMap<>();
    }

    public int getMode(){
        return this.mode;
    }
    /*idea: accedere alle posizioni di listaAdiacenza con un hashmap per dedurre gli indici dai nomi delle città*/
    public void addNode(K nameFrom){
        int indexFrom;
        if((this.indecesMap).containsKey(nameFrom) == false){
            System.out.println(nameFrom + " false");
            (this.listaAdiacenza).add(new LinkedList<>());
            indexFrom = listaAdiacenza.size() - 1;
            (this.listaAdiacenza).get(indexFrom).add(new Node(nameFrom, null));
            (this.indecesMap).put(nameFrom, indexFrom);
            System.out.println(indecesMap);
        }
        else
            System.out.println("true");
    }

    public void addEdge(K nameFrom, K nameTo, T weight) throws GrafoException{
        int indexFrom;
        if((this.indecesMap).containsKey(nameFrom) == false){
            throw new GrafoException("Nodo partenza inesistente");
        }
        indexFrom = indecesMap.get(nameFrom);
        listaAdiacenza.get(indexFrom).add(new Node(nameTo, weight));
    }

    public void printMap(){
        for(int i=0; i< listaAdiacenza.size(); i++){
            Object[] temp = listaAdiacenza.get(i).toArray();
            System.out.print("nodo:" + temp[0] + ", ");
            for(int j = 1; j < temp.length; j++){
                System.out.print(temp[i] + ", ");
                System.out.println(temp[i]);
            }
        }
    }
}
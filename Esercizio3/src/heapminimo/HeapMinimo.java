package heapminimo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;

public class HeapMinimo<T>{
    private ArrayList<T> array = null;
    private IdentityHashMap<T, Integer> indicesMap = null;
    private Comparator<? super T> comparator = null;
    
    public HeapMinimo(Comparator<? super T> comparator) throws HeapMinimoException{
        if(comparator == null) throw new HeapMinimoException("HeapMinimo constructor: comparator parameter cannot be null");
        this.array = new ArrayList<>();
        this.indicesMap = new IdentityHashMap<>();
        this.comparator = comparator;
    }
    //controlla se l'array è vuoto
    public boolean isEmpty(){
        return (this.array).isEmpty();
    }
    //ritorna la size dell'array
    public int size(){
        return (this.array).size();
    }
    //inserimento nell'heap
    public void HeapInsert(T element) throws HeapMinimoException{
        if(element == null)
            throw new HeapMinimoException("insert Element: element parameter cannot be null");

        (this.array).add(element);

        int index = size() - 1;
                
        while(index > 0 && ((this.comparator).compare(getElement(index), getElement((getParentIndex(index)))) == -1)){
            T temp = getElement(index);
            (this.array).set(index, getElement((getParentIndex(index))));
            (this.indicesMap).put(getElement((getParentIndex(index))), index); //utilizzare put invece che replace
            (this.array).set(getParentIndex(index), temp);
            index = getParentIndex(index);
        }
        (this.indicesMap).put(element, index);
    }
    //ritorna l'indice del padre
    private int getParentIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+ index +"is out of the array bounds");
        return (index - 1) / 2;
    }
    //ritorna l'elemento padre di element
    public T getParent(T element) throws HeapMinimoException {
        int index = (this.indicesMap).get(element);
        return getElement(getParentIndex(index));
    }
    //ritorna l'indice del figlio sinisto
    public int getLeftIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index " + index + "is out of the array bounds");
        if((2 * index + 1) < size()){
            return 2 * index + 1;
        } 
        return index;
    }
    //ritorna l'indice del figlio destro
    public int getRightIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index " + index + "is out of the array bounds");
        if((2 * index + 2) < size()){
            return 2 * index + 2;
        } 
        return index;
    }
    //ritorna il figlio sinistro di element
    public T getLeftChild(T element) throws HeapMinimoException {
        if(containsValue(element) == false)
            throw new HeapMinimoException("Element not exists");
        int index = (this.indicesMap).get(element);
        return getElement(getLeftIndex(index));
    }
    //ritorna il figlio destro di element
    public T getRightChild(T element) throws HeapMinimoException {
        if(containsValue(element) == false )
            throw new HeapMinimoException("Element not exists");
        int index = (this.indicesMap).get(element);
        return getElement(getRightIndex(index));
    }
    //ritorna e rimuove l'elemento piu' piccolo nell'heap
    public T extractMin() throws HeapMinimoException {
        if(isEmpty())
            throw new HeapMinimoException("Heap is empty");
        if((this.array).size() == 1){
            T res = getElement(0);
            (this.array).remove(res);
            (this.indicesMap).remove(res);
            return res;
        }
        T res = getElement(0);
        int lastIndex = (this.array).size() - 1;
        (this.indicesMap).remove(getElement(0)); //rimuoviamo dalla Map il primo elemento
        (this.array).set(0, getElement(lastIndex)); //mettiamo l'ultimo elemento in prima posizione
        (this.indicesMap).put(getElement(0), 0);
        (this.array).remove(lastIndex);
        
        Heapify(0);
        
        return res;
    }

    private void Heapify(int index) throws HeapMinimoException {
        int indexR = 0, indexL = 0, m = 0;
        indexR = getRightIndex(index);
        indexL = getLeftIndex(index);
        if((this.comparator).compare(getElement(indexR), getElement(indexL)) == -1){
            if((this.comparator).compare(getElement(index), getElement(indexR)) == -1)
                m = index;
            else
                m = indexR;
        }
        else if((this.comparator).compare(getElement(index), getElement(indexL)) == -1)
            m = index;
        else
            m = indexL;

        if(m != index){
            T temp = getElement(index);
            (this.array).set(index, getElement(m));
            this.indicesMap.put(getElement(m),index);
            (this.array).set(m, temp);
            this.indicesMap.put(temp,m);
            Heapify(m);
        }
    } 
    //ritorna l'elemento in posizione index
    public T getElement(int index) throws HeapMinimoException {
        if(index < 0 || index >= size())
            throw new HeapMinimoException("Index " + index + "is out of the array bounds");
        return (this.array).get(index);
    }
    //diminuisce il valore di un elemento
    public void subtractValue(T oldValue, T newValue) throws HeapMinimoException {
        if((this.comparator).compare(oldValue, newValue) == -1)
            throw new HeapMinimoException("newValue cannot be greater than oldValue");
            
        int index = (this.indicesMap).get(oldValue);
        (this.array).set(index, newValue);
        (this.indicesMap).remove(oldValue);     //toglie dalla Map il vecchio elemento
        (this.indicesMap).put(newValue, index); //aggiunge alla Map il nuovo valore
        while (index > 0 && ((this.comparator).compare(getElement(getParentIndex(index)), getElement(index)) == 1)){
            T temp = getElement(getParentIndex(index));
            (this.array).set(getParentIndex(index), getElement(index));
            this.indicesMap.put(getElement(index),getParentIndex(index));
            (this.array).set(index, temp);
            this.indicesMap.put(temp, index);
            index = getParentIndex(index);
        }
    }
    //controlla se la mappa contiene il valore
    public boolean containsValue(T value){
        return (this.indicesMap).containsKey(value);
    }
}
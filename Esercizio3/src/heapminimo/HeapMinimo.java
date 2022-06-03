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


    public boolean isEmpty(){
        return (this.array).isEmpty();
    }

    public int size(){
        return (this.array).size();
    }
    
    public void HeapInsert(T element) throws HeapMinimoException{
        if(element == null)
            throw new HeapMinimoException("insert Element: element parameter cannot be null");

        (this.array).add(element);

        int index = size() - 1;
                
        while(index > 0 && ((this.comparator).compare(getElement(index), getElement((getParentIndex(index)))) == -1)){
            T temp = getElement(index);
            (this.array).set(index, getElement((getParentIndex(index))));
            (this.indicesMap).replace(getElement((getParentIndex(index))), index); //utilizzare put invece che replace
            (this.array).set(getParentIndex(index), temp);
            index = getParentIndex(index);
        }
        (this.indicesMap).put(element, index);
    }

    private int getParentIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+ index +"is out of the array bounds");
        return (index - 1) / 2;
    }

    public T getParent(T element) throws HeapMinimoException {
        int index = (this.indicesMap).get(element);
        return getElement(getParentIndex(index));
    }

    public int getLeftIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index " + index + "is out of the array bounds");
        if((2 * index + 1) < size()){
            return 2 * index + 1;
        } 
        return index;
    }

    public int getRightIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index " + index + "is out of the array bounds");
        if((2 * index + 2) < size()){
            return 2 * index + 2;
        } 
        return index;
    }

    public T getLeftChild(T element) throws HeapMinimoException {
        if(containsValue(element) == false)
            throw new HeapMinimoException("Element not exists");
        int index = (this.indicesMap).get(element);
        return getElement(getLeftIndex(index));
    }
   
    public T getRightChild(T element) throws HeapMinimoException {
        if(containsValue(element) == false )
            throw new HeapMinimoException("Element not exists");
        int index = (this.indicesMap).get(element);
        return getElement(getRightIndex(index));
    }

    public void extractMin() throws HeapMinimoException {
        if(isEmpty())
            throw new HeapMinimoException("Heap is empty");
        int lastIndex = (this.array).size() - 1;
        (this.indicesMap).remove(getElement(0)); //rimuoviamo dalla Map il primo elemento
        (this.array).set(0, getElement(lastIndex)); //mettiamo l'ultimo elemento in prima posizione
        (this.array).remove(lastIndex);
        
        Heapify(0);
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
    /*
    [][]
    [][]
    [][]
    [][]
    
    */

    public T getElement(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");
        return (this.array).get(index);
    }    
    
    
    public void subtractValue(T oldValue, T newValue) throws HeapMinimoException {
        if((this.comparator).compare(oldValue, newValue) == -1)
            throw new HeapMinimoException("newValue cannot be greater than oldValue");
            
        int index = (this.indicesMap).get(oldValue);
        (this.array).set(index, newValue);
        (this.indicesMap).remove(oldValue); //togliamo dalla Map il vecchio elemento
        (this.indicesMap).put(newValue, index); //aggiungiamo alla Map il nuovo valore
        while (index > 0 && ((this.comparator).compare(getElement(getParentIndex(index)), getElement(index)) == 1)){
            T temp = getElement(getParentIndex(index));
            (this.array).set(getParentIndex(index), getElement(index));
            this.indicesMap.put(getElement(index),getParentIndex(index));
            (this.array).set(index, temp);
            this.indicesMap.put(temp, index);
            index = getParentIndex(index);
        }
    }

    public void subtractValue(int index, T newValue) throws HeapMinimoException {
        if((this.comparator).compare(getElement(index), newValue) == -1)
            throw new HeapMinimoException("newValue cannot be greater than oldValue");
        (this.array).set(index, newValue);
        while (index > 0 && ((this.comparator).compare(getElement(getParentIndex(index)), getElement(index)) == 1)){
            T temp = getElement(getParentIndex(index));
            (this.array).set(getParentIndex(index), getElement(index));
            (this.array).set(index, temp);
            index = getParentIndex(index);
        }
    }
    
    public boolean containsValue(T value){
        return (this.indicesMap).containsKey(value);
    }
}

//https://docs.oracle.com/javase/7/docs/api/java/util/IdentityHashMap.html
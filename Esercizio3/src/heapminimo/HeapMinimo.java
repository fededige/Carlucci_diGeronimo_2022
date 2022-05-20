package heapminimo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HeapMinimo<T>{
    private ArrayList<T> array = null;
    private Comparator<? super T> comparator = null;
    private HashMap<T, Integer> indices = new HashMap<T, Integer>();
    
    public HeapMinimo(Comparator<? super T> comparator) throws HeapMinimoException{
        if(comparator == null) throw new HeapMinimoException("HeapMinimo constructor: comparator parameter cannot be null");
        this.array = new ArrayList<>();
        this.comparator = comparator;
    }

    /*public void printHash(){
        System.out.println(indices);
    }*/

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
        /*
        [zebra][3]
        [cane][1]
        [dado][2]
        [ali][0]
        
                [ali][cane][dado][zebra]
                index = 0
                temp = ali

            zebra 
            cane
            dado
            ali
         */
        
        while(index > 0 && ((this.comparator).compare((this.array).get(index), (this.array).get(getParentIndex(index))) == -1)){
            T temp = (this.array).get(index);
            (this.array).set(index, (this.array).get(getParentIndex(index)));
            indices.replace((this.array).get(getParentIndex(index)), index);
            (this.array).set(getParentIndex(index), temp);
            index = getParentIndex(index);
        }
        indices.put(element, index);
    }

    public int getParentIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");

        return index / 2;
    }

    public T getParent(T element) throws HeapMinimoException {
        if(element == null)
            throw new HeapMinimoException("Element cannot be null");
        return (this.array).get(ind);
    }

    public T getLeftChild(T element) throws HeapMinimoException {
        if(element == null)
            throw new HeapMinimoException(" Element cannot be null");
        int index = indices.get(element);
        if((2 * index + 1) <= size()){
            return (this.array).get(2 * index + 1);
        } 
        return (this.array).get(index);
    }

    public T getRightChild(T element) throws HeapMinimoException {
        if(element == null)
            throw new HeapMinimoException(" Element cannot be null");
        int index = indices.get(element);
        if((2 * index + 2) <= size()){
            return (this.array).get(2 * index + 2);
        } 
        return (this.array).get(index);
    }

    public T getMin() throws HeapMinimoException {
        if(isEmpty()){
            throw new HeapMinimoException("Heap is empty");
        }
        return (this.array).get(0);
    }

    public T getElement(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");
        return (this.array).get(index);
    }

    public void subtractValue(int index, T amount) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");
    }
}
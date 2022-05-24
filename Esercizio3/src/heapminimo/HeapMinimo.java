package heapminimo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HeapMinimo<T>{
    private ArrayList<T> array = null;
    private Comparator<? super T> comparator = null;
    
    public HeapMinimo(Comparator<? super T> comparator) throws HeapMinimoException{
        if(comparator == null) throw new HeapMinimoException("HeapMinimo constructor: comparator parameter cannot be null");
        this.array = new ArrayList<>();
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
            (this.array).set(getParentIndex(index), temp);
            index = getParentIndex(index);
        }
    }

    private int getParentIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");
        return (index - 1) / 2;
    }

    public T getParent(int index) throws HeapMinimoException {
        return (this.array).get(getParentIndex(index));
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

    public T getLeftChild(int index) throws HeapMinimoException {
        return (this.array).get(getLeftIndex(index));
    }

     public T getRightChild(int index) throws HeapMinimoException {
        return (this.array).get(getRightIndex(index));
    }

    public void extractMin() throws HeapMinimoException {
        if(isEmpty()){
            throw new HeapMinimoException("Heap is empty");
        }
        (this.array).set(0, getElement((this.array).size() - 1));
        (this.array).remove((this.array).size() - 1);
        
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
            (this.array).set(m, temp);
            Heapify(m);
        }
    } 

    public T getElement(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");
        return (this.array).get(index);
    }    
    
    
    public void subtractValue(int index, T newValue) throws HeapMinimoException {
        if((this.comparator).compare(getElement(index), newValue) == -1)
            throw new HeapMinimoException("newValue cannot be greater than oldValue");
        (this.array).set(index, newValue);
        while (index > 0 && ((this.comparator).compare(getParent(index), getElement(index)) == 1)){
            T temp = getParent(index);
            (this.array).set(getParentIndex(index), getElement(index));
            (this.array).set(index, temp);
            index = getParentIndex(index);
        }
    }
}
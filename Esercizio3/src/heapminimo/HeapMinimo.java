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
            (this.array).set(getParentIndex(index), temp);
            index = getParentIndex(index);
        }
    }

    private int getParentIndex(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");

        return (index - 1) / 2;
    }

    /*public T getParent(T element) throws HeapMinimoException {
        if(element == null)
            throw new HeapMinimoException("Element cannot be null");
        int index = indices.get(element);
        if(index == 0)
            throw new HeapMinimoException("Element is the first one");
        return (this.array).get(getParentIndex(index));
    }*/

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

    public void extractMin() throws HeapMinimoException {
        if(isEmpty()){
            throw new HeapMinimoException("Heap is empty");
        }
        
        (this.array).set(0, (this.array).get((this.array).size() - 1));
        (this.array).remove((this.array).size() - 1);
        
        Heapify(0);
    }
    private void Heapify(int index) throws HeapMinimoException {
        int indexR = 0, indexL = 0, m = 0;
        indexR = getRightIndex(index);
        indexL = getLeftIndex(index);
        if((this.comparator).compare((this.array).get(indexR), (this.array).get(indexL)) == -1){
            if((this.comparator).compare((this.array).get(index), (this.array).get(indexR)) == -1)
                m = index;
            else
                m = indexR;
        }
        else if((this.comparator).compare((this.array).get(index), (this.array).get(indexL)) == -1)
            m = index;
        else
            m = indexL;

        if(m != index){
            T temp = (this.array).get(index);
            (this.array).set(index, (this.array).get(m));
            (this.array).set(m, temp);
            Heapify(m);
        }
    }

    /*
    array[0] = 4;
    array[1] = 4;
    array[2] = 6;
    array[3] = 23;
    array[4] = 22;
    array[5] = 12;
    array[6] = 18;
    array[7] = 30;
    array[8] = 28;
    array[9] = 45; 



            <4>0 
         /       \
       <4>1        <6>2 
      /    \        |   \
    <23>3   <22>4  <12>5  <18>6 
    /    \      \
  <30>7  <28>8   <45>9
    */  

    public T getElement(int index) throws HeapMinimoException {
        if(index < 0 || index > size())
            throw new HeapMinimoException("Index "+index+"is out of the array bounds");
        return (this.array).get(index);
    }    
    
    
    public void subtractValue(T oldValue, T newValue) throws HeapMinimoException {
        if((this.comparator).compare(oldValue, newValue) == 1)
            throw new HeapMinimoException("newValue cannot be greater than oldValue");
    }
}
package heapminimo;

import java.util.Comparator;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class HeapMinimoTests {

  class IntegerComparator implements Comparator<Integer> {
    @Override
    public int compare(Integer i1, Integer i2) {
      return i1.compareTo(i2);
    }
  }


  private Integer i1,i2,i3;
  private HeapMinimo<Integer> heapminimo;

  @Before
  public void createHeapMinimo() throws HeapMinimoException{
    i1 = -12;
    i2 = 0;
    i3 = 4;
    heapminimo = new HeapMinimo<>(new IntegerComparator());
  }
 
  @Test
  public void testIsEmpty_zeroEl(){
    assertTrue(heapminimo.isEmpty());
  }

  @Test
  public void testIsEmpty_oneEl() throws Exception{
    heapminimo.HeapInsert(i1);
    assertFalse(heapminimo.isEmpty());
  }


  @Test
  public void testSize_zeroEl() throws Exception{
    assertEquals(0,heapminimo.size());
  }

  @Test
  public void testSize_oneEl() throws Exception{
    heapminimo.HeapInsert(i1);
    assertEquals(1,heapminimo.size());
  }

  @Test
  public void testSize_twoEl() throws Exception{
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i2);
    assertEquals(2,heapminimo.size());
  }

  @Test
  public void testAddGet_oneEl() throws Exception{
    heapminimo.HeapInsert(i1);
    assertTrue(i1==heapminimo.getElement(0));
  }


  @Test
  public void testHeap_threeEl() throws Exception{

    Integer[] arrExpected = {i1,i2,i3};

    heapminimo.HeapInsert(i2);
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i3);

    Integer[] arrActual = new Integer[3];

    for(int i=0;i<3;i++)
      arrActual[i] = heapminimo.getElement(i);

    assertArrayEquals(arrExpected,arrActual);
  }


  @Test
  public void testgetParent_threeEl() throws Exception{

    Integer[] arrExpected = {i1, i1};

    heapminimo.HeapInsert(i2);
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i3);

    Integer[] arrActual = new Integer[2];

    arrActual[0] = heapminimo.getParent(i2);
    arrActual[1] = heapminimo.getParent(i3);    

    assertArrayEquals(arrExpected,arrActual);
  }

  @Test
  public void testextractMin_threeEl() throws Exception{

    Integer[] arrExpected = {i2, i3};

    heapminimo.HeapInsert(i2);
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i3);
    Integer res = heapminimo.extractMin();

    Integer[] arrActual = new Integer[2];

    arrActual[0] = heapminimo.getElement(0);
    arrActual[1] = heapminimo.getElement(1);
    assertArrayEquals(arrExpected, arrActual);
  }

  @Test
  public void testextractMin_return_threeEl() throws Exception{

    Integer[] arrExpected = {i2, i3};

    heapminimo.HeapInsert(i2);
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i3);
    Integer res = heapminimo.extractMin();
    
    assertTrue(res==i1);
  }

   @Test
  public void testextractMin_oneEl() throws Exception{
    heapminimo.HeapInsert(i2);
    Integer res = heapminimo.extractMin();
    assertEquals(i2, res);
  }

  @Test
  public void testgetLeftChild_threeEl() throws Exception{

    heapminimo.HeapInsert(i2);
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i3);

    assertEquals(i2, heapminimo.getLeftChild(i1));
  }

  @Test
  public void testgetRightChild_threeEl() throws Exception{

    heapminimo.HeapInsert(i2);
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i3);

    assertEquals(i3, heapminimo.getRightChild(i1));
  }

  @Test
  public void testsubtractValue_threeEl() throws Exception{
    Integer newV = -15;
    Integer[] arrExpected = {newV, i2, i3};

    heapminimo.HeapInsert(i2);
    heapminimo.HeapInsert(i1);
    heapminimo.HeapInsert(i3);
    heapminimo.subtractValue(0, newV);

    Integer[] arrActual = new Integer[3];

    arrActual[0] = heapminimo.getElement(0);
    arrActual[1] = heapminimo.getElement(1);
    arrActual[2] = heapminimo.getElement(2);

    assertArrayEquals(arrExpected, arrActual);
  }
}


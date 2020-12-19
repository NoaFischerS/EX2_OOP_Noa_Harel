package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdgeListTest {
    EdgeList list1 = new EdgeList();
    EdgeList list2 = new EdgeList();

    @Test
    void addNi() {
        list1.addNi(new EdgeData(1,0,5));
        list1.addNi(new EdgeData(1,2,6));
        list2.addNi(new EdgeData(0,2,5));
        list2.addNi(new EdgeData(0,2,0.5));
        assertEquals(0.5,list2.getE(2).getWeight());
        assertEquals(5,list1.getE(0).getWeight());
    }



    @Test
    void getE() {
        list1.addNi(new EdgeData(1,0,5));
        list1.addNi(new EdgeData(1,2,6));
        list2.addNi(new EdgeData(0,2,0.5));
        assertEquals(0.5,list2.getE(2).getWeight());
        assertEquals(5,list1.getE(0).getWeight());
        assertEquals(null,list2.getE(7));
    }

    @Test
    void hasE() {
        list1.addNi(new EdgeData(1,0,5));
        list1.addNi(new EdgeData(1,2,6));
        list2.addNi(new EdgeData(0,2,0.5));
        assertTrue(list1.hasE(2));
        assertFalse(list1.hasE(3));
        assertFalse(list2.hasE(0));
        assertTrue(list2.hasE(2));
    }


    @Test
    void removeE() {
        list1.addNi(new EdgeData(1,0,5));
        list1.addNi(new EdgeData(1,2,6));
        list2.addNi(new EdgeData(0,2,0.5));
        list1.removeE(2);
        assertFalse(list1.hasE(2));
        list2.removeE(2);
        list2.removeE(5);
        list2.removeE(-15);
        assertFalse(list2.hasE(2));
    }

}
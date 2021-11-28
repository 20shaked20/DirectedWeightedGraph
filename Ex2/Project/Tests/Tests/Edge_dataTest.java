package Tests;

import Main.Edge_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Edge_dataTest {

    private Edge_data e1 = new Edge_data(5,9,40.5);
    private Edge_data e2 = new Edge_data(9,14,35.5);


    @Test
    void getSrc() {
        assertEquals(e1.getSrc(),5);
        assertEquals(e2.getSrc(),9);
    }

    @Test
    void getDest() {
        assertEquals(e1.getDest(),9);
        assertEquals(e2.getDest(),14);
    }

    @Test
    void getWeight() {
        assertEquals(e1.getWeight(),40.5);
        assertEquals(e2.getWeight(),35.5);
    }

    @Test
    void getInfo() {
        assertEquals(e1.getInfo(),"nadav");
        assertEquals(e2.getInfo(),"mutzi");
    }

    @Test
    void setInfo() {
        e1.setInfo("ronen");
        assertEquals(e1.getInfo(),"ronen");
    }

    @Test
    void getTag() {
        assertEquals(e1.getTag(),0);
        assertEquals(e2.getTag(),-1);
    }

    @Test
    void setTag() {
        e1.setTag(1);
        assertEquals(e1.getTag(),1);
    }

    @Test
    void compare_by_weight() {
        assertEquals(e1.compare_by_weight(e2),1); // returns 1 because e1 (40.5) > e2 (35.5)
    }

    @Test
    void testToString() {
    }

    @Test
    void is_equals() {
    }
}
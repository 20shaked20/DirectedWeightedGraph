package Tests;

import Main.Geo_Location;
import Main.Node_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Node_dataTest {

    private Geo_Location g1 = new Geo_Location(9,9,0);
    private Node_data n1 = new Node_data(99,g1,"nadav",1);

    private Geo_Location g2 = new Geo_Location(6,6,0);
    private Node_data n2 = new Node_data(66,g2,"mutzi",-1);



    @Test
    void getKey() {
        assertEquals(n1.getKey(),99);
        assertEquals(n2.getKey(),66);
    }

    @Test
    void getLocation() {
        assert(g1.is_equal(new Geo_Location(9.0,9.0,0.0)));
        assertFalse(g1.is_equal(new Geo_Location(6.0,9.0,0.0)));
    }

    @Test
    void setLocation() {
    }

    @Test
    void getWeight() {
    }

    @Test
    void setWeight() {
    }

    @Test
    void getInfo() {
    }

    @Test
    void setInfo() {
    }

    @Test
    void getTag() {
    }

    @Test
    void setTag() {
    }

    @Test
    void testToString() {
    }

    @Test
    void is_equals() {
    }
}
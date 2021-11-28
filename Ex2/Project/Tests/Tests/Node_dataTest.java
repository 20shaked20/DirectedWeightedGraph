package Tests;

import Main.Geo_Location;
import Main.Node_data;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class Node_dataTest {

    private Geo_Location g1 = new Geo_Location(9, 9, 0);
    private Node_data n1 = new Node_data(99, g1);

    private Geo_Location g2 = new Geo_Location(6, 6, 0);
    private Node_data n2 = new Node_data(66, g2);


    @Test
    void getKey() {
        assertEquals(n1.getKey(), 99);
        assertEquals(n2.getKey(), 66);
    }

    @Test
    void getLocation() {
        assert (g1.is_equal(new Geo_Location(9.0, 9.0, 0.0)));
        assertFalse(g1.is_equal(new Geo_Location(6.0, 9.0, 0.0)));
    }

    @Test
    void setLocation() {
    }

    @Test
    void getInfo() {
        assertEquals(n1.getInfo(), "nadav");
        assertEquals(n2.getInfo(), "mutzi");
    }

    @Test
    void setInfo() {
        n1.setInfo("ronny");
        assertEquals(n1.getInfo(), "ronny");
    }

    @Test
    void getTag() {
        assertEquals(n1.getTag(), 1);
        assertEquals(n2.getTag(), -1);
    }

    @Test
    void setTag() {
        n1.setTag(0);
        assertEquals(n1.getTag(), 0);
    }

    @Test
    void testToString() {
        assertEquals(n1.toString(), "(" + 99 + ")");

    }

    @Test
    void is_equals() {

    }

}
package Tests;

import Main.Geo_Location;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Geo_LocationTest {

    private Geo_Location g1 = new Geo_Location(9, 9, 0);
    private Geo_Location g2 = new Geo_Location(6, 6, 0);

    @Test
    void x() {
        assertEquals(g1.x(),9);
        assertEquals(g2.x(),6);
    }

    @Test
    void y() {
        assertEquals(g1.y(),9);
        assertEquals(g2.y(),6);
    }

    @Test
    void z() {
        assertEquals(g1.z(),0);
        assertEquals(g2.z(),0);
    }

    @Test
    void testToString() {
    }

    @Test
    void distance() {
        assertEquals(g1.distance(g1),0);
        assertEquals(g2.distance(g2),0);
    }
}
package api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocationTest {

    @Test
    void x() {
        geo_location g=new GeoLocation(0,0,0);
        assertEquals(0,g.x());
        g=new GeoLocation(0.5,0,-1);
        assertNotEquals(0,g.x());
        assertEquals(0.5,g.x());
        g=new GeoLocation(-2.34543,500,-1);
        assertNotEquals(0.5,g.x());
        assertEquals(-2.34543,g.x());

    }

    @Test
    void y() {
        geo_location g=new GeoLocation(0,0,0);
        assertEquals(0,g.x());
        g=new GeoLocation(0.5,0,-1);
        assertNotEquals(0,g.x());
        assertEquals(0.5,g.x());
        g=new GeoLocation(-2.34543,500,-1);
        assertNotEquals(0.5,g.x());
        assertEquals(-2.34543,g.x());
    }

    @Test
    void z() {
        geo_location g=new GeoLocation(0,0,0);
        assertEquals(0,g.z());
        g=new GeoLocation(0.5,0,-1);
        assertNotEquals(0,g.z());
        assertEquals(-1,g.z());
        g=new GeoLocation(-2.34543,500,17.43);
        assertNotEquals(0.5,g.z());
        assertEquals(17.43,g.z());
    }

    @Test
    void distance() {
        geo_location p1=new GeoLocation(0,0,0);
        geo_location p2=new GeoLocation(0,0,1);
        assertEquals(1,p1.distance(p2));
        geo_location p3= new GeoLocation(3,3,3);
        assertEquals(5.196152422706632,p3.distance(p1));
        assertEquals(5.196152422706632,p1.distance(p3));
        geo_location p4= new GeoLocation(0.5,14,57);
        assertEquals(55.1656596081294,p3.distance(p4));
    }

    @Test
    void testToString() {
        geo_location p1=new GeoLocation(0,0,0);
        geo_location p2=new GeoLocation(0,0.5,1);
        assertEquals("0.0,0.0,0.0",p1.toString());
        assertEquals("0.0,0.5,1.0",p2.toString());

    }
}
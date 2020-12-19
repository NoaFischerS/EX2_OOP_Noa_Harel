package api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {
    node_data n0=new NodeData(0,1,1,1);
    node_data n1= new NodeData(1,0.5,-1,10);

    @Test
    void getKey() {
        assertEquals(0,n0.getKey());
        assertEquals(1,n1.getKey());

    }

    @Test
    void getLocation() {
        assertEquals(1,n0.getLocation().x());
        geo_location g0=new GeoLocation(1,1,2);
        assertNotEquals(g0,n0.getLocation()); // comper with pointer
        assertEquals(-1,n1.getLocation().y());

    }

    @Test
    void setLocation() {
        assertEquals(1,n0.getLocation().y());
        n0.setLocation(new GeoLocation(0,0,1));
        assertEquals(0,n0.getLocation().y());
        geo_location g0=new GeoLocation(0,0,1);
        assertNotEquals(g0,n0.getLocation());//compare by pointer
        n0.setLocation(g0);
        assertEquals(1,n0.getLocation().z());

    }


    @Test
    void getInfo() {
        assertEquals("x",n0.getInfo());
    }

    @Test
    void setInfo() {
        n0.setInfo("v");
        assertEquals("v",n0.getInfo());
    }

    @Test
    void getTag() {
        assertEquals(-1,n0.getTag());
    }

    @Test
    void setTag() {
        n0.setTag(1);
        assertEquals(1,n0.getTag());
        n1.setTag(45);
        assertEquals(45,n1.getTag());
    }
    static class NodeDataJsonTest{

        node_data n0=new NodeData(0,1,1,1);
        node_data n1= new NodeData(1,0.5,-1,10);
        NodeData.NodeDataJson nj=new NodeData.NodeDataJson();
        Type type;
        JsonSerializationContext jsonSerializationContext;
        JsonDeserializationContext jsonDeserializationContext;

        @Test
        void serialize_deserialize()
        {
            JsonElement je=nj.serialize(n0,type, jsonSerializationContext);
            node_data node= nj.deserialize(je,type,jsonDeserializationContext);
            assertEquals(0, node.getKey());
            assertEquals(1,node.getLocation().x());
            assertEquals(1,node.getLocation().y());
            assertEquals(1,node.getLocation().z());
            node_data node2=nj.deserialize(nj.serialize(n1,type,jsonSerializationContext),type,jsonDeserializationContext);
            assertEquals(1,node2.getKey());
            assertEquals(0.5,node2.getLocation().x());
            assertEquals(-1,node2.getLocation().y());
            assertEquals(10,node2.getLocation().z());
        }
    }
}
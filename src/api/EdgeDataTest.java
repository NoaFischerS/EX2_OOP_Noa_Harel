package api;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

class EdgeDataTest {

    edge_data e1 = new EdgeData(0,2,2.45);
    edge_data e2 = new EdgeData(0,1,0.11);
    edge_data e3 = new EdgeData(1,2,5);


    @Test
    void getSrc() {
        assertEquals(0,e1.getSrc());
        assertEquals(0,e2.getSrc());
        assertEquals(1,e3.getSrc());
    }

    @Test
    void getDest() {
        assertEquals(2,e1.getDest());
        assertEquals(1,e2.getDest());
        assertEquals(2,e3.getDest());
    }

    @Test
    void getWeight() {
        assertEquals(2.45,e1.getWeight());
        assertEquals(0.11,e2.getWeight());
        assertEquals(5,e3.getWeight());
    }

    @Test
    void getInfo() {
        assertEquals("x",e1.getInfo());
    }

    @Test
    void setInfo() {
        e1.setInfo("v");
        assertEquals("v",e1.getInfo());

    }

    @Test
    void getTag() {
        assertEquals(-1,e2.getTag());

    }

    @Test
    void setTag() {
        e2.setTag(43);
        assertEquals(43,e2.getTag());
        e2.setTag(-55);
        assertEquals(-55,e2.getTag());
    }

    static class EdgeDataJsonTest
    {
        edge_data e1 = new EdgeData(0,2,2.45);
        edge_data e2 = new EdgeData(0,1,0.11);
        edge_data e3 = new EdgeData(1,2,5);
        EdgeData.EdgeDataJson ej=new EdgeData.EdgeDataJson();
        Type type;
        JsonSerializationContext jsonSerializationContext;
        JsonDeserializationContext jsonDeserializationContext;

        @Test
        void serialize_deserialize()
        {
            JsonElement je1=ej.serialize(e1,type, jsonSerializationContext);
            edge_data edge1= ej.deserialize(je1,type,jsonDeserializationContext);
            JsonElement je3=ej.serialize(e3,type, jsonSerializationContext);
            edge_data edge3= ej.deserialize(je3,type,jsonDeserializationContext);
            assertEquals(e1.getSrc(),edge1.getSrc());
            assertEquals(e1.getDest(),edge1.getDest());
            assertEquals(e1.getWeight(),edge1.getWeight());

            assertEquals(e3.getSrc(),edge3.getSrc());
            assertEquals(e3.getDest(),edge3.getDest());
            assertEquals(e3.getWeight(),edge3.getWeight());

            edge_data edge2= ej.deserialize(ej.serialize(e2,type,jsonSerializationContext),type,jsonDeserializationContext);
            assertEquals(e2.getSrc(),edge2.getSrc());
            assertEquals(e2.getDest(),edge2.getDest());
            assertEquals(e2.getWeight(),edge2.getWeight());
        }
    }
}
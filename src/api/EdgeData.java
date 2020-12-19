package api;

import com.google.gson.*;

import java.lang.reflect.Type;

public class EdgeData implements edge_data {

    private int s;
    private int d;
    private double w;
    private String info="x";
    private int tag=-1;

    public EdgeData(int src, int dest, double weight) {
        this.s=src;
        this.d=dest;
        this.w=weight;
    }

    public EdgeData(edge_data k) {
        this.s=k.getSrc();
        this.d=k.getDest();
        this.w=k.getWeight();
    }

    @Override
    public int getSrc() {
        return this.s;
    }

    @Override
    public int getDest() {
        return this.d;
    }

    @Override
    public double getWeight() {
        return this.w;
    }

    @Override
    public String getInfo() {
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        if(s!=null)
        {
            this.info=s;
        }
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag=t;
    }

    /**
     * methode to equals between 2 nodes.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() == getClass() && o != null) {
            edge_data n = (edge_data) o;
            if (this.s == n.getSrc() && this.d==n.getDest() && this.w== n.getWeight() &&
                    this.info.equals(n.getInfo()) && this.tag == n.getTag()) {
                return true;
            }
        }
        return false;
    }

    static class EdgeDataJson implements JsonDeserializer<edge_data>, JsonSerializer<edge_data>
    {

        @Override
        public edge_data deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            int src=jsonElement.getAsJsonObject().get("src").getAsInt();
            double weight=jsonElement.getAsJsonObject().get("w").getAsDouble();
            int dest=jsonElement.getAsJsonObject().get("dest").getAsInt();
            return new EdgeData(src,dest,weight);
        }


        @Override
        public JsonElement serialize(edge_data e, Type type, JsonSerializationContext jsonSerializationContext)
        {
            JsonObject edgeJson = new JsonObject();
            edgeJson.addProperty("src", e.getSrc());
            edgeJson.addProperty("w",e.getWeight());
            edgeJson.addProperty("dest",e.getDest());
            return edgeJson;

        }
    }
}

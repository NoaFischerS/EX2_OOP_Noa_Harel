package api;

import com.google.gson.*;

import java.lang.reflect.Type;

public class NodeData implements node_data{

    private int keyId;
    private int tag=-1;
    private String info="x";
    private geo_location g;

    /**
     * constructor
     * @param key
     * @param x
     * @param y
     * @param z
     */
    public NodeData(int key,double x, double y, double z)
    {
        this.keyId=key;
        this.g=new GeoLocation(x,y,z);
    }

    public NodeData(int key, geo_location geoL)
    {
        this.keyId=key;
        this.g=new GeoLocation(geoL);
    }

    /**
     * copy constructor
     */
    public NodeData(node_data n)
    {
        this.keyId=n.getKey();
        this.tag=n.getTag();
        this.info=n.getInfo();
        this.g=new GeoLocation(n.getLocation());
    }

    /**
     * return node key id
     * @return
     */
    @Override
    public int getKey() {
        return this.keyId;
    }

    /**
     * return node location
     * @return
     */
    @Override
    public geo_location getLocation() {

        return this.g;
    }

    /**
     * set node location
     * @param p - new  location (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        if(p!=null) {
            this.g = new GeoLocation(p);
        }
    }

    /**
     * isn't used
     * @return
     */
    @Override
    public double getWeight() {
        return 0;
    }

    @Override
    public void setWeight(double w) { }

    /**
     * return node info
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * set node info
     * @param s
     */
    @Override
    public void setInfo(String s) {
        if(s!=null)
        {
            this.info=s;
        }
    }

    /**
     * return node tag
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * set node tag
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag=t;

    }

    /**
     * Compers between two nodes.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o.getClass() == getClass() && o != null) {
            node_data n = (node_data) o;
            if (this.keyId == n.getKey() && this.info.equals(n.getInfo()) && this.tag == n.getTag()) {
                return true;
            }
        }
        return false;
    }

    /**
     * class to serialize and deserialize node object to/from json object
     */
    static class NodeDataJson implements JsonDeserializer<node_data>, JsonSerializer<node_data>
    {

        @Override
        public node_data deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            int key =jsonElement.getAsJsonObject().get("id").getAsInt();
            String geoL=jsonElement.getAsJsonObject().get("pos").getAsString();
            String [] arr=geoL.split(",");
            double x=Double.parseDouble(arr[0]);
            double y=Double.parseDouble(arr[1]);
            double z=Double.parseDouble(arr[2]);
            node_data n=new NodeData(key,x,y,z);
            return n;
        }

        @Override
        public JsonElement serialize(node_data n, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject nodeJson=new JsonObject();
            nodeJson.addProperty("pos", n.getLocation().toString());
            nodeJson.addProperty("id", n.getKey());
            return nodeJson;
        }
    }

}

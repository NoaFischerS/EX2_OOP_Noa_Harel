package api;

public class GeoLocation implements geo_location{

    private double x;
    private double y;
    private double z;

    public GeoLocation(double x1,double y1,double z1)
    {
        this.x=x1;
        this.y=y1;
        this.z=z1;
    }

    public GeoLocation(geo_location g)
    {
        if (g!= null)
        {
            this.x = g.x();
            this.y = g.y();
            this.z = g.z();
        }
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    @Override
    public double distance(geo_location g) {
        if (g != null) {
            double s = Math.pow(this.x - g.x(), 2) + Math.pow(this.y - g.y(), 2) + Math.pow(this.z - g.z(), 2);
            return Math.sqrt(s);
        }
        return -1;
    }

    public String toString()
    {
        String s= x+","+y+","+z;
        return s;
    }

}

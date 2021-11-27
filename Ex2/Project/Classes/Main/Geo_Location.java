package Main; /**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */

import api.GeoLocation;

/**
 * This class represent a location x,y,z for a certain vertices (aka node)
 */
public class Geo_Location implements GeoLocation {
    private double x, y, z;

    /**
     * Constructor
     *
     * @param x double
     * @param y double
     * @param z double
     */
    public Geo_Location(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * deep copy constructor.
     *
     * @param other represents another GeoLocation
     */
    public Geo_Location(GeoLocation other) {
        this.x = other.x();
        this.y = other.y();
        this.z = other.z();
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }


    /**
     * calculates the distance between 3 dimensional 2 points.
     * A(x1,y1,z1), B(x2,y2,z2) = Math.sqrt((x2-x1)^2+(y2-y1)^2+(z2-z1)^2)
     */
    public double distance(GeoLocation g) {
        double distance;
        double dist_x = Math.pow((g.x() - this.x), 2);
        double dist_y = Math.pow((g.y() - this.y), 2); //was g.z -> g.y
        double dist_z = Math.pow((g.z() - this.z), 2); //was g.y -> g.z

        distance = Math.sqrt(dist_x + dist_y + dist_z);

        return distance;
    }

    @Override
    public String toString() {
        return "Main.Geo_Location{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    /**
     * Checks if other location is equal to current location x,y,z
     *
     * @param g another GeoLocation
     * @return true if equal, false if not.
     */
    public boolean is_equal(GeoLocation g) {
        if (this.x == g.x() && this.y == g.y() && this.z == g.z()) {
            return true;
        }
        return false;
    }
}

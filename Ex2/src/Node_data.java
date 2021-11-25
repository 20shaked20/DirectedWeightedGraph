/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */

import api.GeoLocation;
import api.NodeData;

public class Node_data implements NodeData {
    /**
     * This class is a representation of a Node in our DW_graph.
     * it's a set of operations applicable on a
     * node (vertex) in a (directional) weighted graph.
     */

    private int key;
    private Geo_Location geo_location;
    private double weight = Integer.MAX_VALUE;
    private String info = "White"; // the node is set to white unless touched.
    private int tag;

    /**
     * normal constructor, its not DEEP COPYING THE geo_location.
     *
     * @param key          integer
     * @param geo_location (x,y,z) Vector
     * @param info         data (white,black,gray...)
     * @param tag          ?
     */
    public Node_data(int key, Geo_Location geo_location, String info, int tag) {
        this.key = key;
        this.geo_location = geo_location;
        this.info = info;
        this.tag = tag;
    }

    /**
     * deep copy constructor > this one we'd rather to use.
     *
     * @param other Node_data.
     */
    public Node_data(Node_data other) {
        this.key = other.key;
        this.geo_location = new Geo_Location(other.geo_location);
        this.weight = other.weight;
        this.info = other.info;
        this.tag = other.tag;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public GeoLocation getLocation() {
        return this.geo_location;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.geo_location = new Geo_Location(p); // deep copy const
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return this.info;
    } // algo helper info

    @Override
    public void setInfo(String s) {
        this.info = s;
    } // algo helper info

    @Override
    public int getTag() {
        return this.tag;
    } //color

    @Override
    public void setTag(int t) {
        this.tag = t;
    } // color

    @Override
    public String toString() {
        return "Node_data{" +
                "key=" + key +
                ", geo_location=" + geo_location +
                ", weight=" + weight +
                ", info='" + info + '\'' +
                ", tag=" + tag +
                '}';
    }

    /**
     * Checks if a current node is equal to another.
     *
     * @param other Node_data object.
     * @return true for equals, false for not equals.
     */
    public boolean is_equals(Node_data other) {
        if (this.key == other.key && this.weight == other.weight && this.tag == other.tag) {
            if (this.info.equals(other.info)) {
                if (this.geo_location.is_equal(other.geo_location)) {
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */

import api.GeoLocation;
import api.NodeData;

import java.lang.Double;

public class Node_data implements NodeData {
    /**
     * This class is a representation of a Node in our DW_graph.
     * it's a set of operations applicable on a
     * node (vertex) in a (directional) weighted graph.
     */

    private int key;
    private Geo_Location geo_location;
    private double weight; // Redundant code: = Integer.MAX_VALUE;
    private String info; //the node is set to white unless touched. \\ Redundant code: = "White";
    private int tag;

    /**
     * normal constructor, its not DEEP COPYING THE geo_location.
     *
     * @param key          integer
     * @param geo_location (x,y,z) Vector
     * @param weight       double
     * @param info         data (white,black,gray...)
     * @param tag          ?
     */
    public Node_data(int key, Geo_Location geo_location, double weight, String info, int tag) {
        this.key = key;
        this.geo_location = geo_location;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    /**
     * deep copy constructor > this one we'd rather to use.
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
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public String toString() {
        return ""; // not sure what to print r.n
    }

    /**
     * This method compares by weight two nodes ->
     * I assume this one's the most needed because we'll use it mostly to check shortest paths...
     * @param other Node_data object
     * @return :
     * return 0 -> equals
     * return -1 -> less than 'n'
     * return 1 -> more than 'n;
     */
    public int compare_by_weight(Node_data other) {
        double compare_weight = other.weight;
        return Double.compare(this.weight, compare_weight);
    }

    /**
     * Checks if a current node is equal to another.
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

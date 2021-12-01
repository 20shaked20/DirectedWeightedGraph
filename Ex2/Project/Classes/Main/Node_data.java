package Main; /**
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
    private double weight = Double.MAX_VALUE; // USED in dijkstra algo as helper.
    private String info = "Unvisited"; // any meta_data we can use later in the code.
    private int tag = 0; // usable in the code.

    /**
     * normal constructor, its not DEEP COPYING THE geo_location.
     *
     * @param key          integer
     * @param geo_location (x,y,z) Vector
     */
    public Node_data(int key, Geo_Location geo_location) {
        this.key = key;
        this.geo_location = geo_location;
    }

    /**
     * deep copy constructor > this one we'd rather to use.
     *
     * @param other Main.Node_data.
     */
    public Node_data(Node_data other) {
        this.key = other.key;
        this.geo_location = new Geo_Location(other.geo_location);
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
        return "(" + this.key + ")";

    }

    /**
     * Checks if a current node is equal to another.
     *
     * @param other Main.Node_data object.
     * @return true for equals, false for not equals.
     */
    public boolean equals(Node_data other) {
        if (this.key == other.key && this.weight == other.weight && this.tag == other.tag) {
            if (this.info.equals(other.info)) {
                return this.geo_location.is_equal(other.geo_location);
            }
        }
        return false;
    }


}

package Main;

import api.GeoLocation;
import api.NodeData;

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */
public class Node_data implements NodeData {
    /**
     * This class is a representation of a Node in our DW_graph.
     * it's a set of operations applicable on a
     * node (vertex) in a (directional) weighted graph.
     */

    private Geo_Location pos;
    private final int id;
    private transient double weight;
    private transient String info = "Unvisited"; // any meta_data we can use later in the code.
    private transient int tag = 0; // usable in the code.

    /**
     * normal constructor, its not DEEP COPYING THE geo_location.
     *
     * @param key          integer
     * @param geo_location (x,y,z) Vector
     */
    public Node_data(int key, Geo_Location geo_location) {
        this.id = key;
        this.pos = geo_location;
    }

    /**
     * deep copy constructor > this one we'd rather to use.
     *
     * @param other Main.Node_data.
     */
    public Node_data(Node_data other) {
        this.id = other.id;
        this.pos = new Geo_Location(other.pos);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public GeoLocation getLocation() {
        return this.pos;
    }

    @Override
    public void setLocation(GeoLocation p) {
        this.pos = new Geo_Location(p); // deep copy const
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
        return "(" + this.id + ")";

    }

    /**
     * Checks if a current node is equal to another.
     *
     * @param other Main.Node_data object.
     * @return true for equals, false for not equals.
     */
    public boolean equals(Node_data other) {
        if (this.id == other.id && this.weight == other.weight && this.tag == other.tag) {
            if (this.info.equals(other.info)) {
                return this.pos.is_equal(other.pos);
            }
        }
        return false;
    }


}

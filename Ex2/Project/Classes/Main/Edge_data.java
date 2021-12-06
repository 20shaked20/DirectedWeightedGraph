package Main;

import api.EdgeData;
import java.io.Serializable;

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */
public class Edge_data implements EdgeData, Serializable {

    private final int src;
    private final double w; // the weight of the code.
    private final int dest;
    private transient String info; // any meta_data we can use later in the code.
    private transient int tag = 0; // represent a color -> -1 = Black, 0 = White, 1 = Gray. (always starts as 0, unvisited)

    /**
     * Constructor
     */
    public Edge_data(int src, int dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.w = weight;
    }

    /**
     * deep copy constructor
     */
    public Edge_data(Edge_data other) {
        this.src = other.src;
        this.dest = other.dest;
        this.w = other.w;
    }

    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.w;
    }

    @Override
    public String getInfo() { // algo helper info
        return this.info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    } // algo helper info

    @Override
    public int getTag() {
        return this.tag;
    } // color

    @Override
    public void setTag(int t) {
        this.tag = t;
    } //color

    @Override
    public String toString() {
        return '{' +
                "src=" + src +
                ", dest=" + dest +
                ", weight=" + w +
                '}';
    }

    /**
     * This method compares by weight two Edges ->
     *
     * @param other Main.Node_data object
     * @return :
     * return 0 -> equals
     * return -1 -> less than 'n'
     * return 1 -> more than 'n;
     */
    public int compare_by_weight(Edge_data other) {
        return Double.compare(this.w, other.w);
    }

    /**
     * Checks if a current edge is equal to another.
     *
     * @param other Main.Edge_data object.
     * @return true for equals, false for not equals.
     */
    public boolean is_equals(Edge_data other) {
        if (this.src == other.src && this.dest == other.dest && this.w == other.w && this.tag == other.tag) {
            return this.info.equals(other.info);
        }
        return false;
    }

}

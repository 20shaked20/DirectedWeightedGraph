package Main; /**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */

import api.EdgeData;

public class Edge_data implements EdgeData {

    private int src;
    private int dest;
    private double weight; // the weight of the code.
    private String info; // any meta_data we can use later in the code.
    private int tag; // represent a color -> -1 = Black, 0 = White, 1 = Gray.

    /**
     * Constructor
     */
    public Edge_data(int src, int dest, double weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    /**
     * deep copy constructor
     */
    public Edge_data(Edge_data other) {
        this.src = other.src;
        this.dest = other.dest;
        this.weight = other.weight;
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
        return this.weight;
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
                ", weight=" + weight +
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
        double compare_weight = other.weight;
        return Double.compare(this.weight, compare_weight);
    }

    /**
     * Checks if a current edge is equal to another.
     *
     * @param other Main.Edge_data object.
     * @return true for equals, false for not equals.
     */
    public boolean is_equals(Edge_data other) {
        if (this.src == other.src && this.dest == other.dest && this.weight == other.weight && this.tag == other.tag) {
            return this.info.equals(other.info);
        }
        return false;
    }

}

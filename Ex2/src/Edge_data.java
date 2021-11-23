/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */
import api.EdgeData;

public class Edge_data implements EdgeData {

//    private Node_data src; < maybe create using nodes? because its two nodes that create an EDGE :X?
//    private Node_data dest;
    private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;

    /**
     * Constructor
     */
    public Edge_data(int src, int dest, double weight, String info, int tag) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    /**
     * deep copy constructor
     */
    public Edge_data(Edge_data other){
        this.src = other.src;
        this.dest = other.dest;
        this.weight = other.weight;
        this.info = other.info;
        this.tag = other.tag;
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

    /** implement edge location finder maybe// < we want to find a edge in given space.
     * public Edge_data get_edge(int src,int dest) {return null;}// returns edge location
     */

}

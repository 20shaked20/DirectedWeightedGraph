/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */

import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;

/**
 * This is a java class containing methods of a DirectedWeightedGraph.
 * (according to the interface we were given).
 */

public class DW_Graph implements api.DirectedWeightedGraph {

    private HashMap<Integer, Node_data> Nodes;
    private HashMap<Integer, HashMap<Integer, Edge_data>> Edges; // hash map of hashmaps > get.get in o(1)
    //<src,<dest,edge> << info
    private int MC; // every time our graph changes, increase this.
    private int EdgesCounter;
    private int NodesCounter;

    // default const
    public DW_Graph() {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        this.EdgesCounter = 0;
        this.NodesCounter = 0;
        this.MC = 0;
    }
    // Deep copy const
    public DW_Graph(DW_Graph other) {
        this.Nodes = new HashMap<>(); // TODO: add deep copy method.
        this.Edges = new HashMap<>(); // TODO: add deep copy method.
        this.EdgesCounter = other.EdgesCounter;
        this.NodesCounter = other.NodesCounter;
        this.MC = other.MC;
    }

    @Override
    public NodeData getNode(int key) { //o(1)
        return this.Nodes.get(key); //simple return.
    }

    @Override
    public EdgeData getEdge(int src, int dest) {
        return this.Edges.get(src).get(dest); //simple return.

    }
    @Override
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(), (Node_data) n); // casting to my class.
        this.MC++;
    }

    @Override
    public void connect(int src, int dest, double w) {
        Edge_data e = new Edge_data(src,dest,w, "",0);
        HashMap<Integer, Edge_data> tmp = new HashMap<>(); // creates a temporary edge.
        tmp.put(dest,e);
        this.Edges.put(src,tmp);
    }
//TODO: implement iterators.
    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    //TODO: check this function for Edges.remove
    @Override
    public NodeData removeNode(int key) {
        this.Edges.remove(key); // < that simple? I don't think.
        this.MC++; // increase changes to graph
        return this.Nodes.remove(key); // simply remove key.

    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        Edge_data e = this.Edges.get(src).get(dest); // gets the EDGE from the hashmap.
        HashMap<Integer, Edge_data> tmp = new HashMap<>(); // creates a temporary Removable edge.
        tmp.put(dest,e);
        this.Edges.remove(src,tmp); //remove it.
        this.MC++; // increase changes to graph
        return e; // returns the EDGE (null if none)
    }

    @Override
    public int nodeSize() {
        return this.Nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.Edges.size();
    }

    @Override
    public int getMC() {
        return this.MC;
    }

}

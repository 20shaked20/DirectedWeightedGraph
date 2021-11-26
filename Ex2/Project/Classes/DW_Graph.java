/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */

import api.EdgeData;
import api.NodeData;

import java.util.HashMap;
import java.util.Iterator;

public class DW_Graph implements api.DirectedWeightedGraph {
    /**
     * This is a java class containing methods of DirectedWeightedGraph interface.
     * <p>
     * Our graph implementation is made with HashMap data structure.
     * We choose to use hashmap for nodes and edges because in this way we can access each node/edge in o(1) time complexity.
     * Moreover, space complexity is dynamically allocated hence efficient.
     */
    private HashMap<Integer, Node_data> Nodes;
    private HashMap<Integer, HashMap<Integer, Edge_data>> Edges;
    private int MC;
    private int EdgesCounter;
    private int NodesCounter;

    /**
     * A default constructor.
     */
    public DW_Graph() {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        this.EdgesCounter = 0;
        this.NodesCounter = 0;
        this.MC = 0;
    }

    // Deep copy const
    public DW_Graph(DW_Graph other) {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        deep_copy_nodes(other.Nodes, other);// fix
        deep_copy_edges(other.Edges, other); // fix
        this.EdgesCounter = other.EdgesCounter;
        this.NodesCounter = other.NodesCounter;
        this.MC = other.MC;
    }
// TODO: create deep copy method.

    /**
     * This method deep copies graph hashmap of nodes to our graph.
     *
     * @param nodes - HashMap of nodes.
     */
    public void deep_copy_nodes(HashMap nodes, DW_Graph g) {
    }

// TODO: create deep copy method.

    /**
     * This method deep copies graph hashmap of edges to our graph.
     *
     * @param edges - HashMap of nodes.
     */
    public void deep_copy_edges(HashMap edges, DW_Graph g) {
    }


    /**
     * returns a Node_Data in o(1)
     *
     * @param key - the node_id
     * @return - Node_data object
     */
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    /**
     * returns an Edge_Data in o(1)
     *
     * @param src  - Integer representing the start of edge.
     * @param dest - Integer representing the end of edge.
     * @return - Edge_data object
     */
    public EdgeData getEdge(int src, int dest) {
        return this.Edges.get(src).get(dest);

    }

    /**
     * Adds a node(vertex) to the graph.
     *
     * @param n Node_data.
     */
    public void addNode(NodeData n) {
        this.Nodes.put(n.getKey(), (Node_data) n); // casting to my class.
        this.MC++;
    }

    /**
     * Connects src -> dest (aka edge) and adds it to the graph.
     *
     * @param src  - Integer representing the start of edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc) between src-->dest.
     */
    public void connect(int src, int dest, double w) {
        Edge_data e = new Edge_data(src, dest, w, "", 0);
        HashMap<Integer, Edge_data> tmp = new HashMap<>(); // creates a temporary edge.
        tmp.put(dest, e);
        this.Edges.put(src, tmp);
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

    /**
     * Removes edge from the graph.
     *
     * @param src  - Integer representing the start of edge.
     * @param dest - Integer representing the end of edge.
     * @return - The removed edge.
     */
    public EdgeData removeEdge(int src, int dest) {
        Edge_data e = this.Edges.get(src).get(dest); // gets the EDGE from the hashmap.
        HashMap<Integer, Edge_data> tmp = new HashMap<>(); // creates a temporary Removable edge.
        tmp.put(dest, e);
        this.Edges.remove(src, tmp); //remove it.
        this.MC++; // increase changes to graph
        return e; // returns the EDGE (null if none)
    }

    /**
     * @return - how many nodes are in the graph.
     */
    public int nodeSize() {
        return this.Nodes.size();
    }

    /**
     * @return - how many edges are in the graph.
     */
    public int edgeSize() {
        return this.Edges.size();
    }

    /**
     * @return - how many changes were made to the graph.
     */
    public int getMC() {
        return this.MC;
    }

}

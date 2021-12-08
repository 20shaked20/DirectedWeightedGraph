package Main;

import api.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */
public class DW_Graph implements api.DirectedWeightedGraph {
    /**
     * This is a java class containing methods of DirectedWeightedGraph interface.
     * <p>
     * Our graph implementation is made with HashMap data structure.
     * We choose to use hashmap for nodes and edges because in this way we can access each node/edge in o(1) time complexity.
     * Moreover, space complexity is dynamically allocated hence efficient.
     */
    private final HashMap<Integer, NodeData> Nodes;
    private final HashMap<Integer, HashMap<Integer, EdgeData>> Edges;
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

    /**
     * Deep copy constructor.
     *
     * @param other - Another Directed Weighted Graph
     */
    public DW_Graph(DirectedWeightedGraph other) {
        this.Nodes = new HashMap<>();
        this.Edges = new HashMap<>();
        DW_Graph.deep_copy_nodes(this.Nodes, other);
        DW_Graph.deep_copy_edges(this.Edges, other);
        this.EdgesCounter = other.edgeSize();
        this.NodesCounter = other.nodeSize();
        this.MC = other.getMC();
    }

    /**
     * his method deep copies the nodes of a given graph.
     * It uses the Iterator method. -> nodeIter()
     * Running time -> O(n) while n represents the amount of nodes in the graph.
     *
     * @param nodes - HashMap of nodes.
     */
    private static void deep_copy_nodes(HashMap<Integer, NodeData> nodes, DirectedWeightedGraph g) {
        Iterator<NodeData> n = g.nodeIter();
        while (n.hasNext()) {
            Node_data tmp = (Node_data) g.getNode(n.next().getKey());
            nodes.put(tmp.getKey(), tmp);
        }
    }

    /**
     * This method deep copies the edges of a given graph.
     * It uses the Iterator method. -> edgeIter()
     * Running time -> O(n) while n represents the amount of edges in the graph.
     *
     * @param edges - HashMap of edges.
     */
    private static void deep_copy_edges(HashMap<Integer, HashMap<Integer, EdgeData>> edges, DirectedWeightedGraph g) {
        Iterator<EdgeData> e = g.edgeIter();
        EdgeData tmp_edge;
        HashMap<Integer, EdgeData> tmp_map;
        while (e.hasNext()) {
            // tmp_edge = g.getEdge(e.next().getSrc(), e.next().getDest());
            tmp_edge = e.next();
            tmp_map = new HashMap<>();
            tmp_map.put(tmp_edge.getDest(), tmp_edge);
            edges.put(tmp_edge.getSrc(), tmp_map);
        }
    }

    /**
     * returns a Node_Data
     * Running time -> O(1).
     *
     * @param key - the node_id
     * @return - Main.Node_data object
     */
    public NodeData getNode(int key) {
        return this.Nodes.get(key);
    }

    /**
     * returns an Edge_Data.
     * Running time -> O(1).
     *
     * @param src  - Integer representing the start of edge.
     * @param dest - Integer representing the end of edge.
     * @return - Main.Edge_data object
     */
    public EdgeData getEdge(int src, int dest) {
        return this.Edges.get(src).get(dest);
    }

    /**
     * Adds a node(vertex) to the graph.
     * Running time -> O(1).
     *
     * @param n Main.Node_data.
     */
    public void addNode(NodeData n) {
        if (!this.Nodes.containsKey(n.getKey())) { //if it doest not exist add it, otherwise don't.
            this.Nodes.put(n.getKey(), n); // casting to my class.
            this.NodesCounter++;
            this.MC++;
        }
    }

    /**
     * Connects src -> dest (aka edge) and adds it to the graph.
     * Running time -> O(1).
     *
     * @param src  - Integer representing the start of edge.
     * @param dest - the destination of the edge.
     * @param w    - positive weight representing the cost (aka time, price, etc...) between src-->dest.
     */
    public void connect(int src, int dest, double w) {
        if (this.Nodes.containsKey(src) && this.Nodes.containsKey(dest)) {
            // System.out.println("src: " + src + " , " + "dest: " + dest);

            if (this.Edges.containsKey(src)) {
                Edge_data e = new Edge_data(src, dest, w);
                this.Edges.get(src).put(dest, e);
            } else {
                this.Edges.put(src, new HashMap<>());
                Edge_data e = new Edge_data(src, dest, w);
                this.Edges.get(src).put(dest, e);
            }
        } else {
            throw new IllegalArgumentException("Invalid src or dest position");
        }
        this.EdgesCounter++;
        this.MC++;
    }

    /**
     * This is an implementation of the interface Iterator.
     *
     * @return -> an iterator for all the nodes in the graph, with the usage of:
     * hasNext, next methods.
     */
    @Override
    public Iterator<NodeData> nodeIter() {
        return new Iterator<>() {

            //Private class Fields:
            private final int starting_MC = MC;
            private final Iterator<NodeData> it = Nodes.values().iterator();

            @Override
            public boolean hasNext() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                return it.hasNext();
            }

            @Override
            public NodeData next() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                return it.next();
            }

            @Override
            public void remove() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                it.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super NodeData> action) {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                it.forEachRemaining(action);
            }
        };
    }

    /**
     * This is an implementation of the interface Iterator.
     *
     * @return -> an iterator for all the edges in the graph, with the usage of:
     * hasNext, next methods.
     */
    @Override
    public Iterator<EdgeData> edgeIter() {
        return new Iterator<>() {

            //Private class Fields:
            private final int starting_MC = MC;
            private final Iterator<HashMap<Integer, EdgeData>> it1 = Edges.values().iterator();
            private Iterator<EdgeData> it2 = it1.next().values().iterator(); //initializing as not null - important!

            @Override
            public boolean hasNext() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                // check that it1 has the next hashmap iterator or and it2 is not null
                //return it1.hasNext() || ( it2 != null && it2.hasNext());
                return it1.hasNext() || it2.hasNext();
            }

            @Override
            public EdgeData next() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                //if (it2 == null || !(it2.hasNext())) {
                if (!it2.hasNext() && it1.hasNext()) {
                    it2 = it1.next().values().iterator();
                }
                return it2.next();
            }

            @Override
            public void remove() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                it2.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                it2.forEachRemaining(action);
            }
        };
    }

    /**
     * This is an implementation of the interface Iterator.
     *
     * @param node_id -> Integer representing a node_id in the graph.
     * @return -> an iterator for all the edges getting out of a certain node in the graph, with the usage of:
     * hasNext, next methods.
     */
    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return new Iterator<>() {

            //Private class Fields:
            private final int starting_MC = MC;
            private final Iterator<EdgeData> it = Edges.get(node_id).values().iterator();

            @Override
            public boolean hasNext() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                return it.hasNext();
            }

            @Override
            public EdgeData next() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                return it.next();
            }

            @Override
            public void remove() {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                it.remove();
            }

            @Override
            public void forEachRemaining(Consumer<? super EdgeData> action) {
                if (this.starting_MC != MC) {
                    throw new RuntimeException("graph was changed after iterator creation");
                }
                it.forEachRemaining(action);
            }
        };
    }


    /**
     * This method removes a node and all the edges connected to it.
     * Running time -> O(n) while n is the amount of edges to remove.
     *
     * @param key -> Integer representing a node to remove.
     * @return -> the removed node.
     */
    @Override
    public NodeData removeNode(int key) {
        if (this.Edges.containsKey(key)) {
            this.Edges.remove(key);
            this.EdgesCounter -= this.Edges.get(key).size();
        }
        EdgeData curr_edge;
        Iterator<EdgeData> edges = this.edgeIter();
        while (edges.hasNext()) {
            curr_edge = edges.next();
            if (this.Edges.get(curr_edge.getSrc()).containsKey(key)) {
                this.Edges.get(curr_edge.getSrc()).remove(key);
                this.EdgesCounter--;
            }
        }
        this.MC++; // increase changes to graph
        return this.Nodes.remove(key); // simply remove the node
    }

    /**
     * This method removes an edge from the graph.
     * Running time -> O(1).
     *
     * @param src  - Integer representing the start of edge.
     * @param dest - Integer representing the end of edge.
     * @return - the removed edge.
     */
    public EdgeData removeEdge(int src, int dest) {
        EdgeData e = this.Edges.get(src).remove(dest);
        this.MC++; // increase changes to graph
        this.EdgesCounter--;
        return e; // returns the EDGE (null if none)
    }

    /**
     * @return - how many nodes are in the graph.
     */
    public int nodeSize() {
        return this.NodesCounter;
    }

    /**
     * @return - how many edges are in the graph.
     */
    public int edgeSize() {
        return this.EdgesCounter;
    }

    /**
     * @return - how many changes were made to the graph.
     */
    public int getMC() {
        return this.MC;
    }

}

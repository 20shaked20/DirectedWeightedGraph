package Main; /**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 * TODO:
 * <p>
 * This interface represents a Directed (positive) Weighted Graph Theory Algorithms including:
 * 0. clone(); (copy)
 * 1. init(graph);
 * 2. isConnected(); // strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest); < dijkstra < ?
 * 4. List<api.NodeData> shortestPath(int src, int dest);
 * 5. api.NodeData center(); // finds the api.NodeData which minimizes the max distance to all the other nodes.
 * // Assuming the graph isConnected, else return null. See: https://en.wikipedia.org/wiki/Graph_center
 * 6. List<api.NodeData> tsp(List<api.NodeData> cities); // computes a list of consecutive nodes which go over all the nodes in cities.
 * // See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
 * 7. save(file); // JSON file
 * 8. load(file); // JSON file
 *
 * @author boaz.benmoshe
 */


import api.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DW_Graph_Algo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    /**
     * Init the graph which we will operate on, simple assignment method.
     * we assume there's no need to deeply create the graph.
     *
     * @param g -> DirectedWeightedGraph.
     */
    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = g;
    }

    /**
     * @return the graph we operate on.
     */
    @Override
    public DirectedWeightedGraph getGraph() {
        return this.graph;
    }

    /**
     * Computes a deep copy of this weighted graph.
     * This method is implemented as a constructor in the DirectedWeightedGraph class.
     *
     * @return copied DirectedWeightedGraph
     */
    @Override
    public DirectedWeightedGraph copy() {
        return new DW_Graph(this.graph);
    }

    /**
     * //STRONGLY CONNECTED!!! << use DFS
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * Running time -> O(n^2) while n represents the amount of nodes in the graph.(could implement better, r/n will stick to this)
     *
     * @return true if the graph is Strongly connected, false if not.
     */
    @Override
    public boolean isConnected() {
        Iterator<NodeData> nodes = graph.nodeIter();
        boolean[] connected = new boolean[graph.nodeSize()]; //T for possible reach, F for not possible.
        Arrays.fill(connected, false); // init the array.
        while (nodes.hasNext()) {
            NodeData n = nodes.next();
            if (!connected[n.getKey()]) {
                dfs(n, connected); // checks the boolean sol
            }
            for (boolean b : connected) {
                if (!b) { // if even one of the nodes is not reachable return false immediately
                    return false;
                }
            }
            Arrays.fill(connected, false); // after each iteration, fill the array with false for the next iteration.
        }
        return true; // if flag was not popped then return true. the graph is strongly connected.
    }

    /**
     * This method gets a NodeData and in the use concept of dfs algorithm,
     * checks all the edges getting out of the node while considering the edges getting out of each edge dest.
     * if from one node it is possible to reach to all other nodes, the graph is strongly connected.
     * I choose to use the help of boolean array to check if each of the nodes has a way to reach to other node.
     * Running time -> O(n) while n represent the amount of nodes each edge can reach.
     *
     * @param n NodeData
     * @return true if possible to reach with this node, false if not.
     */
    private void dfs(NodeData n, boolean[] connected) {
        System.out.println("n: " + n.getKey());
        connected[n.getKey()] = true;
        Iterator<EdgeData> edges_from_node = graph.edgeIter(n.getKey());
        while (edges_from_node.hasNext()) {
            EdgeData e = edges_from_node.next();
            NodeData next_node = graph.getNode(e.getDest()); //gets the next node to operate dfs on.
            if (!connected[e.getDest()]) { // < indicates the current node we operate on.
                dfs(next_node, connected);
            }
        }
    }


    /**
     * TODO: First implement the shortest path finder.
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    /**
     * Computes the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    /**
     * Finds the api.NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, elese return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the max shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        return null;
    }

    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
     * the lower the better.
     * See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
     *
     * @param cities
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    /**
     * Saves this weighted (directed) graph to the given
     * file name - in JSON format
     *
     * @param file - the file name (may include a relative path).
     * @return true - iff the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        return false;
    }

    /**
     * This method loads a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name of JSON file
     * @return true - iff the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        return Json_Helper.Json_Deserializer(this.graph, file);
    }
}

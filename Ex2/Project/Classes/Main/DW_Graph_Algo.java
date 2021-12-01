package Main; /**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
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

import java.util.*;

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
     * TODO: fix to iterative mode.
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
        //System.out.println("n: " + n.getKey());
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
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1.
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return A double value representing the weight of the shortest path.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            NodeData n1 = graph.getNode(src); // converting them to nodes, so it will be easier to use in the Dijkstra method.
            NodeData n2 = graph.getNode(dest);
            return dijkstra(n1, n2); // will return -1 if no path exists.
        }
        return -1;
    }

    /**
     * This method is an implementation of dijkstra algorithm using priority Queue.
     * the method uses the iterator method in help of going over all the possible edges available from each node,
     * and checks the best path available considering weight of edge and keeps it in the required node.
     *
     * @param src  NodeData
     * @param dest NodeData
     * @return -1 if there's no path available, OR double representing the shortest path.
     */
    private double dijkstra(NodeData src, NodeData dest) { // use info to SET > "True" if visited, "False" if not && size --?
        if (this.graph.getEdge(src.getKey(), dest.getKey()) != null) {
            PriorityQueue<NodeData> nodesQ = new PriorityQueue<NodeData>(this.graph.nodeSize(), new Comparator<NodeData>() {
                //Comparing by weight the nodes, hidden class.
                public int compare(NodeData n1, NodeData n2) {
                    return Double.compare(n1.getWeight(), n2.getWeight());
                }
            });
            src.setWeight(0.0); // setting weight to zero, because we will increase it during the whole process until we reach destination.
            nodesQ.add(src);
            while (!nodesQ.isEmpty()) {
                NodeData poll_node = nodesQ.poll();
                Iterator<EdgeData> curr_edges = this.graph.edgeIter(poll_node.getKey());
                while (curr_edges.hasNext()) {
                    EdgeData curr_edge = curr_edges.next(); // if I don't create this, it will 'no such element exception' because of overlapping in iterator.
                    NodeData n = this.graph.getNode(curr_edge.getDest()); // points on the next destination.
                    if (n.getInfo().equals("Unvisited")) { // unvisited node, it is "Unvisited" as stated.
                        if (n.getWeight() > poll_node.getWeight() + curr_edge.getWeight()) {
                            n.setWeight(poll_node.getWeight() + curr_edge.getWeight());
                            n.setTag(poll_node.getKey());
                        }
                        nodesQ.add(n);
                    }
                }
                poll_node.setInfo("Visited"); // visited node, and its black then done.
                if (poll_node.getKey() == dest.getKey()) { // same key, then we're done checking.
                    //Before exiting the method, because I changed tags,weights and info I will reset them using iterator:
                    Iterator<NodeData> it = graph.nodeIter();
                    this.tag_refresh(it);
                    this.info_refresh(it);
                    this.weight_refresh(it);
                    ////////////////////////////////////////////////////////////////////////////////////////////////
                    return poll_node.getWeight();
                }
            }
        }
        return -1; // no such path.
    }

    /**
     * This is a private method to resets tag of edges or nodes.
     *
     * @param it Edge Iterator Or Node Iterator.
     */
    private void tag_refresh(Iterator<?> it) {
        if (it.hasNext()) {
            if (it.next() instanceof NodeData) {
                while (it.hasNext()) {
                    ((NodeData) it.next()).setTag(0);
                }
            }
            if (it.next() instanceof EdgeData) {
                while (it.hasNext()) {
                    ((EdgeData) it.next()).setTag(0);
                }
            }
        }
    }

    /**
     * This is a private method to resets tag of edges or nodes.
     *
     * @param it Edge Iterator Or Node Iterator.
     */
    private void info_refresh(Iterator<?> it) {
        if (it.hasNext()) {
            if (it.next() instanceof NodeData) {
                while (it.hasNext()) {
                    ((NodeData) it.next()).setInfo("Unvisited");
                }
            }
            if (it.next() instanceof EdgeData) {
                while (it.hasNext()) {
                    ((EdgeData) it.next()).setInfo("Unvisited");
                }
            }
        }
    }

    /**
     * This is a private method to resets weight of nodes.
     *
     * @param it Edge Iterator Or Node Iterator.
     */
    private void weight_refresh(Iterator<NodeData> it) {
        while (it.hasNext()) {
            ((NodeData) it.next()).setWeight(Double.MAX_VALUE);
        }
    }


    //TODO: its a temporary dijkstra for shortestPath, try to implement it inside the real dijkstra
    public List<NodeData> dijkstra2(NodeData src, NodeData dest) { // use info to SET > "True" if visited, "False" if not && size --?
        if (this.graph.getEdge(src.getKey(), dest.getKey()) != null) {
            int[] parentVertex = new int[this.graph.nodeSize()]; // parents to indicate the route.
            PriorityQueue<NodeData> nodesQ = new PriorityQueue<NodeData>(this.graph.nodeSize(), new Comparator<NodeData>() {
                //Comparing by weight the nodes, hidden class.
                public int compare(NodeData n1, NodeData n2) {
                    return Double.compare(n1.getWeight(), n2.getWeight());
                }
            });
            src.setWeight(0.0); // setting weight to zero, because we will increase it during the whole process until we reach destination.
            nodesQ.add(src);
            parentVertex[0] = src.getKey(); // init as starting point -1.
            while (!nodesQ.isEmpty()) {
                NodeData poll_node = nodesQ.poll();
                Iterator<EdgeData> curr_edges = this.graph.edgeIter(poll_node.getKey());
                while (curr_edges.hasNext()) {
                    EdgeData curr_edge = curr_edges.next(); // if I don't create this, it will 'no such element exception' because of overlapping in iterator.
                    NodeData n = this.graph.getNode(curr_edge.getDest()); // points on the next destination.
                    if (n.getInfo().equals("Unvisited")) { // unvisited node, it is "Unvisited" as stated.
                        if (n.getWeight() > poll_node.getWeight() + curr_edge.getWeight()) {
                            n.setWeight(poll_node.getWeight() + curr_edge.getWeight());
                            n.setTag(poll_node.getKey());
                            parentVertex[poll_node.getKey()] = n.getKey(); // saves the path.
                        }
                        nodesQ.add(n);
                    }
                }
                poll_node.setInfo("Visited"); // visited node, and its black then done.
                if (poll_node.getKey() == dest.getKey()) { // same key, then we're done checking.
                    //Converting to node_data array.
                    List<NodeData> parentVertex2 = new ArrayList<NodeData>();
                    for (int i = 0; i < parentVertex.length; i++) {
                        if (parentVertex[i] != 0) {
                            parentVertex2.add(graph.getNode(parentVertex[i]));
                        }
                    }
                    return parentVertex2;
                }
            }
            // TODO: Fix those.
            // Before exiting the method, because I changed tags I will reset them using iterator:
            Iterator<NodeData> it = graph.nodeIter();
            this.tag_refresh(it);
            this.info_refresh(it);
            ////////////////////////////////////////////////////////////////////////////////////////////////
        }
        return null; // no such path.
    }


    /**
     * Computes the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return A list of nodes representing the shortest path such that it contains src--> n1-->n2-->...dest
     */
    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            NodeData n1 = graph.getNode(src); // converting them to nodes, so it will be easier to use in the Dijkstra method.
            NodeData n2 = graph.getNode(dest);
            List<NodeData> n_path = new ArrayList<>(graph.nodeSize());
            n_path = dijkstra2(n1, n2); // will return -1 if no path exists.
            return n_path;
        }
        return null;
    }

    /**
     * Finds the api.NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, else return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the shortest path to all the other nodes is minimized.
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


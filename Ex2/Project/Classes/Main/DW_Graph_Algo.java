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
    private double[] floyd_warshall;
    private boolean calcFW = false;

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
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1.  O(TODO: add this when we know shortestPath time complexity.)
     * @param src  - start node
     * @param dest - end (target) node
     * @return A double value representing the weight of the shortest path.
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        LinkedList<NodeData> list = (LinkedList<NodeData>) shortestPath(src, dest);
        double totalWeight = 0;
        for (int i = 0; i < list.size(); i++){
            totalWeight += list.get(i).getWeight();
        }
        return totalWeight;
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
        //using Dijkstra's algorithm https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
        LinkedList<NodeData> ans = new LinkedList<>();

        // TODO: Understand how to alter Dijkstra's algorithm for this!

        return ans;
    }

    /**
     * a helper function for our single source Dijkstra algorithm.
     * O(E) - worst case is when all the Edges (E-1) are neighboring node U.
     * @param u int representing the current node for which we calculate the shortest distance to it's neighbours
     * @param adj an adjacency matrix.
     * @param settled set of nodes that have been covered.
     * @param dist an array of the shortest distances from a single source.
     * @param prq a priority queue that is used in the Dijkstra Algorithm.
     */
    private void adjacentHelper(int u, LinkedList<Node>[] adj, HashSet<Integer> settled, double[] dist, PriorityQueue<Node> prq){

        double edgeDist;
        double newDist;
        Node temp;

        for (int i = 0; i < adj[u].size(); i++){
            temp = adj[u].get(i);

            if(!settled.contains(temp.node)){
                edgeDist = temp.cost;
                newDist = dist[u] + edgeDist;
                dist[temp.node] = Math.min(newDist,dist[temp.node]); // instead of an if argument

                prq.add(new Node(temp.node, dist[temp.node]));
            }
        }

    }

    /**
     * Calculates the shortest path from a single source to every other node (Integer.MAXVALUE if unreachable!)
     * using Dijkstra's algorithm. O(|V|^2) - |V| = amount of vertices.
     * @param src the source for the calculation
     * @return an array of distances from src to i ( such that arr[i] = this distance)
     */
    private double[] singleSourceDijkstraAlgo(int src){ //used in Center!
        double[] dist = new double[graph.nodeSize()];
        for (int i =0; i < dist.length; i++){
            dist[i] = Integer.MAX_VALUE; //init distances from source to be 'infinity'
        }

        HashSet<Integer> settled = new HashSet<>();
        PriorityQueue<Node> prq = new PriorityQueue<>(graph.nodeSize()); //priority queue with initial capacity

        LinkedList<Node>[] adj = new LinkedList[graph.nodeSize()];
        Iterator<EdgeData> edges = graph.edgeIter();
        EdgeData edge;
        while (edges.hasNext()){ //O(|V|)
            edge = edges.next();
            adj[edge.getSrc()].add(new Node(edge.getDest(),edge.getWeight())); //initialing an adjacency matrix.
        }
        prq.add(new Node(src,0));
        dist[src] = 0;

        while (settled.size() != graph.nodeSize()){ //O(|V|)
            int u = prq.remove().node;
            settled.add(u);
            adjacentHelper(u, adj, settled, dist, prq); // O(|V|)
        }
        return dist;
    }

    /**
     * Finds the api.NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, else return null. See: https://en.wikipedia.org/wiki/Graph_center
     *
     * @return the Node data to which the shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        if(!isConnected()){
            return null; //if the graph is not strongly connected, then there is no center!
        }
        double[] dist;
        double bestAverage = Double.MAX_VALUE;
        int bestNode = 0; // Initialized to 0 just in case.
        Iterator<NodeData> nodes = graph.nodeIter();
        while (nodes.hasNext()) { //|V|
            int node = nodes.next().getKey();
            dist = singleSourceDijkstraAlgo(node); //|V|^2 + nodes.next advances the Iterator.
            double avg = average(dist); //|V|
            if (avg < bestAverage){
                bestAverage = avg;
                bestNode = node;
            }
        }
        return graph.getNode(bestNode);
    }

    private double average(double[] dist){
        if (dist == null || dist.length == 0)
            return Integer.MAX_VALUE;
        double sum = 0;
        for (int i = 0; i < dist.length; i++){
            sum+=dist[i]; // note that we assume the graph is strongly connected, therefore no dist[i]=Max_Value!!!
        }
        return sum/dist.length;
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

    private class Node implements Comparator<Node> {
        /**
         * private class for Dijkstra's algorithm implementation.
         * (Node_data is inefficient for this, Implementation-Wise)
         */
        public int node;
        public double cost;
        public Node() { } //empty constructor

        public Node(int node, double cost) {
            this.node = node;
            this.cost = cost;
        }
        @Override
        public int compare(Node node1, Node node2)
        {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }
    }
}


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
    public double shortestPathDist(int src, int dest) {//|V|^2 worst case
        if (graph.getNode(src) == null || graph.getNode(dest) == null)
            return -1;
        double dist = DijkstraAlgo(src, dest);
        if (dist == Integer.MAX_VALUE)
            return -1;
        return dist;
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
        //Also, this is technically a pathfinding algorithm
        if (graph.getNode(src) == null || graph.getNode(dest) == null)
            return null;
        double dist = DijkstraAlgo(src, dest); //calculate the shortest path from source to dest
        if (dist == Integer.MAX_VALUE){
            return null;
        }
        LinkedList<NodeData> ans = new LinkedList<>();
        double[] n_path = DijkstraAlgoPath(src, dest);

        int pointer = src;
        while (pointer != dest){
            ans.add(graph.getNode(pointer));
            pointer = (int)n_path[pointer];
        }
        ans.add(graph.getNode(dest));
        /*
        for (int i = 0; i < n_path.length; i++) { // iterates until last position which is saved for the weight of the path.
            if (n_path[i] != -1) {
                ans.add(graph.getNode((int) n_path[i]));
            }
        }
        */

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
    private void adjacentHelper(int u, LinkedList<Node>[] adj, HashSet<Integer> settled, double[] dist, PriorityQueue<Node> prq, double[] parents){

        double edgeDist;
        double newDist;
        Iterator<EdgeData> neighbours = graph.edgeIter(u);
        Node temp;
        EdgeData edge = null;

        while (neighbours.hasNext()){
                edge = neighbours.next();
                temp = new Node(edge.getDest(),edge.getWeight());

                if(!settled.contains(temp.node)){
                    edgeDist = temp.cost;
                    newDist = dist[u] + edgeDist;
                    if (dist[temp.node] > newDist){
                        dist[temp.node] = newDist;
                        if (parents != null)
                            parents[u] = temp.node;// TODO: Check correctness?
                    }
                    //dist[temp.node] = Math.min(newDist,dist[temp.node]); // instead of an if argument

                    prq.add(new Node(temp.node, dist[temp.node]));
                }
            }
    }

    /**
     * Solves for the shortest path from src to dest using Dijkstra's algorithm for a single pair
     * @param src an integer representing the source
     * @param dest an integer representing the destination
     * @return a double value representing the shortest distance
     */
    private double DijkstraAlgo(int src, int dest) { //used in Center!
        double[] dist = new double[graph.nodeSize()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        HashSet<Integer> settled = new HashSet<>();
        PriorityQueue<Node> prq = new PriorityQueue<>(graph.nodeSize()); //priority queue with initial capacity

        LinkedList<Node>[] adj = new LinkedList[graph.nodeSize()];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new LinkedList<>();
        }
        prq.add(new Node(src,0));
        dist[src] = 0;
        int unreachable = -1;

        while (settled.size() != graph.nodeSize()){ //O(|V|)
            if(prq.isEmpty()){
                settled.add(unreachable--); //ignores unreachable nodes and increases settled size to achieve stop cond.
                //also advances unreachable towards the negative for Hashing purposes
                continue;
            }
            int u = prq.remove().node;
            if (u == dest)
                return dist[dest];
            settled.add(u);
            adjacentHelper(u, adj, settled, dist, prq, null); // O(|V|)
        }
        return dist[dest];
    }

    /**
     * Solves for the shortest path from src to dest using Dijkstra's algorithm for a single pair
     * @param src an integer representing the source
     * @param dest an integer representing the destination
     * @return a double value representing the shortest distance
     */
    private double[] DijkstraAlgoPath(int src, int dest) {
        double[] dist = new double[graph.nodeSize()];
        double[] parents = new double[graph.nodeSize()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parents, -1);
        dist[src] = 0;
        parents[0] = src;

        HashSet<Integer> settled = new HashSet<>();
        PriorityQueue<Node> prq = new PriorityQueue<>(graph.nodeSize()); //priority queue with initial capacity

        LinkedList<Node>[] adj = new LinkedList[graph.nodeSize()];
        for (int i = 0; i < adj.length; i++) {
            adj[i] = new LinkedList<>();
        }
        prq.add(new Node(src,0));
        dist[src] = 0;
        int unreachable = -1;

        while (settled.size() != graph.nodeSize()){ //O(|V|)
            if(prq.isEmpty()){
                settled.add(unreachable--); //ignores unreachable nodes and increases settled size to achieve stop cond.
                //also advances unreachable towards the negative for Hashing purposes
                continue;
            }
            int u = prq.remove().node;
            if (u == dest)
                return parents;
            settled.add(u);
            adjacentHelper(u, adj, settled, dist, prq, parents); // O(|V|)
        }
        return parents;
    }

    /**
     * Calculates the shortest path from a single source to every other node (Integer.MAXVALUE if unreachable!)
     * using Dijkstra's algorithm. O(|V|^2) - |V| = amount of vertices.
     * @param src the source for the calculation
     * @return an array of distances from src to i ( such that arr[i] = this distance)
     */
    private double[] singleSourceDijkstraAlgo(int src){ //used in Center!
        double[] dist = new double[graph.nodeSize()];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;

        HashSet<Integer> settled = new HashSet<>();
        PriorityQueue<Node> prq = new PriorityQueue<>(graph.nodeSize()); //priority queue with initial capacity

        LinkedList<Node>[] adj = new LinkedList[graph.nodeSize()];
        for (int i = 0; i < adj.length; i++){
            adj[i] = new LinkedList<>();
        }
        prq.add(new Node(src,0));
        dist[src] = 0;
        int unreachable = -1;

        while (settled.size() != graph.nodeSize()){ //O(|V|)
            if(prq.isEmpty()){
                settled.add(unreachable--); //ignores unreachable nodes and increases settled size to achieve stop cond.
                //also advances unreachable towards the negative for Hashing purposes
                continue;
            }
            int u = prq.remove().node;
            settled.add(u);
            adjacentHelper(u, adj, settled, dist, prq, null); // O(|V|)
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
        //step 1 : create a subgraph representing the cities
        DW_Graph subgraph;
        if (graph.nodeSize() == cities.size()){
            subgraph = (DW_Graph) graph; //if the list contain all nodes
        }
        else { // otherwise, create a subgraph
            subgraph = new DW_Graph();
            Iterator<NodeData> nodes = cities.iterator();
            while (nodes.hasNext()){
                subgraph.addNode(nodes.next());
            }
            Iterator<EdgeData> edges = graph.edgeIter();
            EdgeData temp;
            while (edges.hasNext()){
                temp = edges.next();
                if (subgraph.containsNode(temp.getSrc()) && subgraph.containsNode(temp.getDest())){
                    subgraph.connect(temp.getSrc(),temp.getDest(),temp.getWeight());
                    //adds edge only if both nodes are part of the subgraph
                }
            }
        }
        //end of step 1
        //step 2 : sanity check
        DW_Graph_Algo subgraphalgo = new DW_Graph_Algo();
        subgraphalgo.init(subgraph);
        if (!subgraphalgo.isConnected())
            return null; // tsp is not valid if at least one node is unreachable.
        //end of step 2
        //step 3 : greedy algorithm
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

    private class Node implements Comparable<Node> {
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

        public int compare(Node node1, Node node2)
        {
            if (node1.cost < node2.cost)
                return -1;
            if (node1.cost > node2.cost)
                return 1;
            return 0;
        }

        @Override
        public int compareTo(Node o) {
            if (this.cost < o.cost)
                return -1;
            if (this.cost > o.cost)
                return 1;
            return 0;
        }
    }
}


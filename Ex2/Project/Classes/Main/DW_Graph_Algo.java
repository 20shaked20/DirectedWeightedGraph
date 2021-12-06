package Main;

import api.*;
import java.util.*;

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 * <p>
 * This interface represents Directed Weighted Graph (positive Integers) Algorithms including:
 * 0. clone() (copy)
 * 1. init(graph)
 * 2. isConnected() - strongly (all ordered pais connected)
 * 3. double shortestPathDist(int src, int dest)
 * 4. List<api.NodeData> shortestPath(int src, int dest)
 * 5. api.NodeData center() - finds the api.NodeData which minimizes the max distance to all the other nodes.
 * Assuming the graph isConnected, else return null. See: https://en.wikipedia.org/wiki/Graph_center
 * 6. List<api.NodeData> tsp(List<api.NodeData> cities) - computes a list of consecutive nodes which go over all the nodes in cities.
 * See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
 * 7. save(file); - JSON file
 * 8. load(file); - JSON file
 * </p>
 */
public class DW_Graph_Algo implements DirectedWeightedGraphAlgorithms {
    private DirectedWeightedGraph graph;

    public DW_Graph_Algo(){
        graph = new DW_Graph();
    }

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
     */
    private void dfs(NodeData n, boolean[] connected) {
        //TODO: MAKE THIS ITERATIVE!
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
     * Note: if no such path --> returns -1.  O(n^2) - Dijkstra's Algorithm cost
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
     * Computes the shortest path between src to dest - as an ordered List of nodes:
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

        return ans;
    }

    /**
     * a helper function for our single source Dijkstra algorithm.
     * O(E) - worst case is when all the Edges (E-1) are neighboring node U.
     * @param u int representing the current node for which we calculate the shortest distance to it's neighbours
     * @param settled set of nodes that have been covered.
     * @param dist an array of the shortest distances from a single source.
     * @param prq a priority queue that is used in the Dijkstra Algorithm.
     */
    private void adjacentHelper(int u, HashSet<Integer> settled, double[] dist, PriorityQueue<Node> prq, double[] parents){

        double edgeDist;
        double newDist;
        Iterator<EdgeData> neighbours = graph.edgeIter(u);
        Node temp;
        EdgeData edge;

        while (neighbours.hasNext()){
                edge = neighbours.next();
                temp = new Node(edge.getDest(), edge.getWeight());

                if(!settled.contains(temp.node)){
                    edgeDist = temp.cost;
                    newDist = dist[u] + edgeDist;
                    if (dist[temp.node] > newDist){
                        dist[temp.node] = newDist;
                        if (parents != null)
                            parents[u] = temp.node;
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

        prq.add(new Node(src, 0));
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
            adjacentHelper(u, settled, dist, prq, null); // O(|V|)
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

        prq.add(new Node(src, 0));
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
            adjacentHelper(u, settled, dist, prq, parents); // O(|V|)
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

        prq.add(new Node(src, 0));
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
            adjacentHelper(u, settled, dist, prq, null); // O(|V|)
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
        for (double v : dist) {
            sum += v; // note that we assume the graph is strongly connected, therefore no dist[i]=Max_Value!!!
        }
        return sum/dist.length;
    }
    /**
     * Computes a list of consecutive nodes which go over all the nodes in cities.
     * the sum of the weights of all the consecutive (pairs) of nodes (directed) is the "cost" of the solution -
     * the lower, the better.
     * See: https://en.wikipedia.org/wiki/Travelling_salesman_problem
     *
     * @param cities A list of cities that must be visited.
     */
    @Override
    public List<NodeData> tsp(List<NodeData> cities) { //O(n^3)
        //step 1 : create hashset of cities to visit

        HashSet<Integer> ctv = new HashSet<>();
        for (NodeData n : cities){
            ctv.add(n.getKey());
        }
        //step 2 : create an array of possible routes

        LinkedList<NodeData>[] routes = new LinkedList[this.graph.nodeSize()];
        double[] routeWeights = new double[this.graph.nodeSize()];

        //step 3 : calculate route from node, stop when all nodes in cities have been stopped

        EdgeData tempEdge;
        NodeData current;
        NodeData currentStart;
        int counter;
        boolean hasAllCities;
        HashSet<Integer> visited;

        Iterator<NodeData> iterator = graph.nodeIter();
        while (iterator.hasNext()){
            //re-initialize values:
            visited = new HashSet<>();
            hasAllCities = false;
            currentStart = iterator.next();
            current = currentStart;
            routes[currentStart.getKey()] = new LinkedList<>(); //this inits the linked list - very important.
            routes[currentStart.getKey()].add(current); //adds the start of the route immediately.
            counter = 0;
            //step 3.5 - compute solution from node i :
            while (!hasAllCities){ //O(n)
                visited.add(current.getKey());
                if (ctv.contains(current.getKey())){
                    counter += 1; //only adds to the counter if current is part of the cities to visit.
                }
                if (counter == cities.size()){
                    hasAllCities = true;
                    continue; //prettier way to stop the loop for this iteration!
                }
                tempEdge = getCheapestNeighbour(current,visited);
                if (tempEdge == null){
                    hasAllCities = true; //to 'break' this segment if current has no neighbours
                    continue;
                }
                current = graph.getNode(tempEdge.getDest()); //O(n)
                routes[currentStart.getKey()].add(current);
                routeWeights[currentStart.getKey()] += tempEdge.getWeight();
            }
        }

        //step 4 : check index of the fastest route

        double bestWeight = routeWeights[0];
        int bestRoute = 0;
        for (int i = 1 ; i < routeWeights.length ; i++){ //O(n)
            if (routeWeights[i] <= bestWeight ){
                counter = 0;
                for (int j = 0; j < routes[i].size(); j++){ //O(n)
                    if (ctv.contains(routes[i].get(j).getKey())){
                        counter++;
                    }
                }
                if (counter >= ctv.size()) {
                    bestWeight = routeWeights[i];
                    bestRoute = i;
                }
            }
        }
        return routes[bestRoute];
    }

    /**
     * The method returns the cheapest cost neighbour of a node
     * @param node node for which we calculate the cheapest neighbour
     * @param visited a Hashset containing all nodes already traversed
     * @return returns the node that is the cheapest neighbour of 'node', or null if no no neighbours!
     */
    private EdgeData getCheapestNeighbour(NodeData node, HashSet<Integer> visited) {
        HashSet<Integer> lookedAt = new HashSet<>();
        Iterator<EdgeData> iterator = graph.edgeIter(node.getKey());
        if (!iterator.hasNext()){
            return null;  //return null when node has no neighbours
        }
        EdgeData best = iterator.next();
        EdgeData temp;
        while (iterator.hasNext()){
            temp = iterator.next();
            if (best.getWeight() < temp.getWeight()){
                best = temp;
            }
        }
        iterator = graph.edgeIter(node.getKey());
        lookedAt.add(best.getDest());
        boolean flag = !visited.contains(best.getDest());
        while (iterator.hasNext()){
            temp = iterator.next();
            if (temp.getWeight() <= best.getWeight()){
                if (!visited.contains(temp.getDest()) && !lookedAt.contains(temp.getDest())){
                    flag = true;
                    best = temp;
                }
                lookedAt.add(temp.getDest());
            }
        }
        if (!flag){ //this code block activates only if there are no unvisited neighbours
            lookedAt = new HashSet<>();
            iterator = graph.edgeIter(node.getKey());
            best = iterator.next();
            while (iterator.hasNext()){
                temp = iterator.next();
                if (temp.getWeight() <= best.getWeight()){
                    if (!lookedAt.contains(temp.getDest()))
                        best = temp;
                }
                lookedAt.add(temp.getDest());
            }
        }
        return best;
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

    private static class Node implements Comparable<Node> {
        /**
         * private class for Dijkstra's algorithm implementation.
         * (Node_data is inefficient for this, Implementation-Wise)
         */
        public int node;
        public double cost;

        public Node(int node, double cost) {
            this.node = node;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Double.compare(this.cost, o.cost);
        }
    }
}


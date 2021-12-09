package Main;

import api.*;
import org.w3c.dom.Node;

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

    public DW_Graph_Algo() {
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
     * Returns true if and only if (iff) there is a valid path from each node to each
     * other node. NOTE: assume directional graph (all n*(n-1) ordered pairs).
     * In this method, we use the Iterative BFS Algorithm, to check if we can reach all nodes from a single node.
     * Running time -> O(n^2) while n represents the amount of nodes in the graph.(could implement better, r/n will stick to this)
     *
     * @return true if the graph is Strongly connected, false if not.
     */
    @Override
    public boolean isConnected() { //O(|V|*(|V| + |E|))
        Iterator<NodeData> nodes = graph.nodeIter();
        NodeData n;
        while (nodes.hasNext()) {
            n = nodes.next();
            if (!BFS_Helper(n)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This private method gets a NodeData object and using a BFS search, attempts to reach every other node in the graph
     * Running time -> O(V+E) BFS algo run time.
     *
     * @param n A NodeData object from which the BFS starts.
     * @return true if all nodes are reachable from n, otherwise false.
     */ // Was recurisve, changed to iterative because of stack overflow on 10k nodes.
    private boolean BFS_Helper(NodeData n) {
        HashSet<Integer> visited = new HashSet<>();
        visited.add(n.getKey());
        Queue<NodeData> q = new LinkedList<>();
        q.add(n);
        NodeData temp;
        NodeData dest;
        Iterator<EdgeData> edges;
        while (!q.isEmpty()) {
            temp = q.poll();
            edges = this.graph.edgeIter(temp.getKey());
            while (edges.hasNext()) {
                dest = this.graph.getNode(edges.next().getDest());
                if (!visited.contains(dest.getKey())) {
                    visited.add(dest.getKey());
                    q.add(dest);
                }
            }
        }
        return visited.size() == this.graph.nodeSize();
    }


    /**
     * Computes the length of the shortest path between src to dest
     * Note: if no such path --> returns -1.
     * In this method, we use the Dijkstra Algorithm.
     * Running time ->  O(n^2) worst case - Dijkstra's Algorithm cost
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return A double value representing the weight of the shortest path.
     */
    @Override
    public double shortestPathDist(int src, int dest) {//|V|^2 worst case
        if (graph.getNode(src) != null && graph.getNode(dest) != null) {
            NodeData n1 = graph.getNode(src); // converting them to nodes, so it will be easier to use in the Dijkstra method.
            NodeData n2 = graph.getNode(dest);
            double weight = dijkstra_algo(n1, n2);
            //Before exiting the method, because I changed tags I will reset them using iterator:
            this.tag_refresh();
            this.info_refresh();
            this.weight_refresh();

            return weight;
        }
        return -1;
    }

    /**
     * Computes the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * see: https://en.wikipedia.org/wiki/Shortest_path_problem
     * Note if no such path --> returns null;
     * In this method, we use the Dijkstra Algorithm.
     * The Idea of the method was to use dijkstra algorithm and keep a Parenting TAG to the updated weight.
     * While going back in the nodes until we finished and keep the nodes in the array as the path.
     * Running time ->  O(n^2) worst case - Dijkstra's Algorithm cost
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
            double tmp_weight = dijkstra_algo(n1, n2);
            if (tmp_weight == -1) {  // no path then return null.
                return null;
            }
            List<NodeData> reverse = new LinkedList<>();
            NodeData tmp = n2;
            //loop to reach parent list == the shortest path, given dijkstra algorithm usage.
            while (tmp.getTag() != -1) {
                reverse.add(tmp);
                tmp = this.graph.getNode(tmp.getTag());
            }
            if (reverse.isEmpty()) {
                return null;
            }
            Collections.reverse(reverse); // needed to reverse the list because it stored parent backwards in the loop while.
            reverse.add(0, graph.getNode(src));
            //Before exiting the method, because I changed tags I will reset them using iterator:
            this.tag_refresh();
            this.info_refresh();
            this.weight_refresh();
            return reverse;
        }
        return null;
    }


    /**
     * Solves for the shortest path from src to dest using Dijkstra's algorithm.
     * Dijkstra algorithm implementation using priority queue.
     * We choose to use priority queue with comparator of weights, as each time we get the minimum weighted node.
     * the algorithm traverses through the nodes, and considers each path of node its minimum available weight.
     * it updates the weights of all available paths with the use of "relaxation" as studied in class,
     * that is to update the neighbouring weights to the father weight in case they are cheaper.
     * adding to that, we each time add a new node we meet to our queue to be checked next iteration.
     * each time we finished checking a node, we set it to > "visited" so it won't repeat checking it.
     * once our 'src' key is equal to our 'dest' key, we know that we checked all possible edges to reach that,
     * therefore, return the weight of the 'dest', (As we know, we made sure to update it with relaxation).
     * furthermore, because of our use in the node weight,tag,info data, we had to reset them using outside private methods.
     * Running time ->  O(n^2) worst case - Dijkstra's Algorithm cost
     *
     * @param src  an integer representing the source
     * @param dest an integer representing the destination
     * @return a double value representing the shortest distance
     */
    private double dijkstra_algo(NodeData src, NodeData dest) {
        if (src != null && dest != null) {
            //Comparing by weight the nodes, hidden class.
            PriorityQueue<NodeData> nodesQ = new PriorityQueue<>(this.graph.nodeSize(), Comparator.comparingDouble(NodeData::getWeight));
            src.setWeight(0.0); // setting weight to zero, because we will increase it during the whole process until we reach destination.
            nodesQ.add(src);
            while (!nodesQ.isEmpty()) {
                NodeData poll_node = nodesQ.poll(); // picks the minimum each time ( thanks to comparator )
                Iterator<EdgeData> curr_edges = this.graph.edgeIter(poll_node.getKey());
                while (curr_edges.hasNext()) {
                    EdgeData curr_edge = curr_edges.next(); // if I don't create this, it will 'no such element exception' because of overlapping in iterator.
                    NodeData n = this.graph.getNode(curr_edge.getDest()); // points on the next destination.
                    if (n.getInfo().equals("Unvisited")) { // unvisited node, it is "Unvisited" as stated.
                        // Relaxation, as studied in class.
                        if (n.getWeight() > poll_node.getWeight() + curr_edge.getWeight()) {
                            n.setWeight(poll_node.getWeight() + curr_edge.getWeight());
                            n.setTag(poll_node.getKey());
                        }
                        nodesQ.add(n);
                    }
                }
                poll_node.setInfo("Visited"); // visited node, and its black then done.
                if (poll_node.getKey() == dest.getKey()) { // same key, then we're done checking.
                    return poll_node.getWeight();
                }
            }
        }
        return -1;
    }

    /**
     * This is a private method to resets tag of nodes.
     */
    private void tag_refresh() {
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            it.next().setTag(-1);
        }
    }

    /**
     * This is a private method to resets tag of nodes.
     */
    private void info_refresh() {
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            it.next().setInfo("Unvisited");
        }
    }

    /**
     * This is a private method to resets weight of nodes.
     */
    private void weight_refresh() {
        Iterator<NodeData> it = this.graph.nodeIter();
        while (it.hasNext()) {
            it.next().setWeight(Double.MAX_VALUE);
        }
    }


    /**
     * Finds the api.NodeData which minimizes the max distance to all the other nodes.
     * Assuming the graph isConnected, else return null. See: https://en.wikipedia.org/wiki/Graph_center
     * The idea of this method was to find all Shortest path distances from each node, get the maximum of each one.
     * and from all those maximum distances, pick the minimum to be our center.
     * Running time ->  O(n^3)  - Dijkstra's Algorithm cost * n nodes.
     *
     * @return the Node data to which the shortest path to all the other nodes is minimized.
     */
    @Override
    public NodeData center() {
        if (!isConnected()) {
            return null;
        }
        Iterator<NodeData> nodes = this.graph.nodeIter();
        double tmp;
        double min1 = Double.MAX_VALUE;
        int node_id = -1;
        while (nodes.hasNext()) {
            NodeData curr_node = nodes.next();
            Iterator<NodeData> nodes2 = this.graph.nodeIter();
            double max1 = Double.MIN_VALUE;
            while (nodes2.hasNext()) {
                NodeData next_node = nodes2.next();
                tmp = dijkstra_algo(curr_node, next_node);
                this.tag_refresh();
                this.info_refresh();
                this.weight_refresh();
                if (tmp > max1) {
                    max1 = tmp;
                }
            }
            if (min1 > max1) {
                min1 = max1;
                node_id = curr_node.getKey();
            }
        }
        return this.graph.getNode(node_id);
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
        for (NodeData n : cities) {
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
        while (iterator.hasNext()) {
            //re-initialize values:
            visited = new HashSet<>();
            hasAllCities = false;
            currentStart = iterator.next();
            current = currentStart;
            routes[currentStart.getKey()] = new LinkedList<>(); //this inits the linked list - very important.
            routes[currentStart.getKey()].add(current); //adds the start of the route immediately.
            counter = 0;
            //step 3.5 - compute solution from node i :
            while (!hasAllCities) { //O(n)
                visited.add(current.getKey());
                if (ctv.contains(current.getKey())) {
                    counter += 1; //only adds to the counter if current is part of the cities to visit.
                }
                if (counter == cities.size()) {
                    hasAllCities = true;
                    continue; //prettier way to stop the loop for this iteration!
                }
                tempEdge = getCheapestNeighbour(current, visited);
                if (tempEdge == null) {
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
        for (int i = 1; i < routeWeights.length; i++) { //O(n)
            if (routeWeights[i] <= bestWeight) {
                counter = 0;
                for (int j = 0; j < routes[i].size(); j++) { //O(n)
                    if (ctv.contains(routes[i].get(j).getKey())) {
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
     * The private method returns the cheapest cost neighbour of a node
     *
     * @param node    node for which we calculate the cheapest neighbour
     * @param visited a Hashset containing all nodes already traversed
     * @return returns the node that is the cheapest neighbour of 'node', or null if no no neighbours!
     */
    private EdgeData getCheapestNeighbour(NodeData node, HashSet<Integer> visited) {
        HashSet<Integer> lookedAt = new HashSet<>();
        Iterator<EdgeData> iterator = graph.edgeIter(node.getKey());
        if (!iterator.hasNext()) {
            return null;  //return null when node has no neighbours
        }
        EdgeData best = iterator.next();
        EdgeData temp;
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (best.getWeight() < temp.getWeight()) {
                best = temp;
            }
        }
        iterator = graph.edgeIter(node.getKey());
        lookedAt.add(best.getDest());
        boolean flag = !visited.contains(best.getDest());
        while (iterator.hasNext()) {
            temp = iterator.next();
            if (temp.getWeight() <= best.getWeight()) {
                if (!visited.contains(temp.getDest()) && !lookedAt.contains(temp.getDest())) {
                    flag = true;
                    best = temp;
                }
                lookedAt.add(temp.getDest());
            }
        }
        if (!flag) { //this code block activates only if there are no unvisited neighbours
            lookedAt = new HashSet<>();
            iterator = graph.edgeIter(node.getKey());
            best = iterator.next();
            while (iterator.hasNext()) {
                temp = iterator.next();
                if (temp.getWeight() <= best.getWeight()) {
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
        return Json_Helper.Json_Serializer(this.graph, file);
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


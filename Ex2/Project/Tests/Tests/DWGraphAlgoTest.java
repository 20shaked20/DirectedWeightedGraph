package Tests;

import api.*;
import Main.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

class DWGraphAlgoTest {

    //declaring objects to be used with testing.
    private final DW_Graph g1 = new DW_Graph(); // simple one way circled graph
    private final DW_Graph g2 = new DW_Graph(); // 2 forested graph.
    private final DW_Graph g3 = new DW_Graph(); // strongly connected, but intricate graph for center and tsp testing.
    private final DW_Graph_Algo a1 = new DW_Graph_Algo();
    private final DW_Graph_Algo a2 = new DW_Graph_Algo();
    private final DW_Graph_Algo a3 = new DW_Graph_Algo();
    private boolean initialized = false;
    private final DW_Graph_Algo g4 = new DW_Graph_Algo();

    public void initTests() {
        //inits g1 as a 0 -1-> 1 -1-> 2 -1-> 3 -1-> 4 -1-> 5 -1-> 1 dwg with all weights = 1 and a one directional circle
        Node_data tmpN = new Node_data(0, new Geo_Location(0, 0, 0));
        g1.addNode(tmpN);
        tmpN = new Node_data(1, new Geo_Location(1, 0, 0));
        g1.addNode(tmpN);
        tmpN = new Node_data(2, new Geo_Location(2, 0, 0));
        g1.addNode(tmpN);
        tmpN = new Node_data(3, new Geo_Location(2, 1, 0));
        g1.addNode(tmpN);
        tmpN = new Node_data(4, new Geo_Location(1, 1, 0));
        g1.addNode(tmpN);
        tmpN = new Node_data(5, new Geo_Location(0, 1, 0));
        g1.addNode(tmpN);
        g1.connect(0, 1, 1);
        g1.connect(1, 2, 1);
        g1.connect(2, 3, 1);
        g1.connect(3, 4, 1);
        g1.connect(4, 5, 1);
        g1.connect(5, 0, 1);

        //inits g2 as a small graph with two forests
        tmpN = new Node_data(0, new Geo_Location(0, 0, 0));
        g2.addNode(tmpN);
        tmpN = new Node_data(1, new Geo_Location(1, 0, 0));
        g2.addNode(tmpN);
        tmpN = new Node_data(2, new Geo_Location(1, 1, 0));
        g2.addNode(tmpN);
        tmpN = new Node_data(3, new Geo_Location(0, 1, 0));
        g2.addNode(tmpN);
        tmpN = new Node_data(4, new Geo_Location(1, 1, 1));
        g2.addNode(tmpN);
        tmpN = new Node_data(5, new Geo_Location(2, 2, 2));
        g2.addNode(tmpN);
        g2.connect(0, 1, 10);
        g2.connect(0, 2, 6);
        g2.connect(0, 3, 3);
        g2.connect(1, 0, 1);
        g2.connect(2, 1, 1);
        g2.connect(3, 2, 1);
        g2.connect(4, 5, 1);
        g2.connect(5, 4, 1);

        g3.addNode(new Node_data(0, new Geo_Location(0, 0, 0)));
        g3.addNode(new Node_data(1, new Geo_Location(0, 1, 0)));
        g3.addNode(new Node_data(2, new Geo_Location(0, -1, 0)));
        g3.addNode(new Node_data(3, new Geo_Location(-1, 0, 0)));
        g3.addNode(new Node_data(4, new Geo_Location(1, 0, 0)));
        g3.connect(0, 1, 1);
        g3.connect(0, 2, 1);
        g3.connect(0, 3, 1);
        g3.connect(0, 4, 1);
        g3.connect(1, 2, 1);
        g3.connect(1, 4, 1);
        g3.connect(2, 0, 1);
        g3.connect(2, 1, 1);
        g3.connect(2, 3, 10);
        g3.connect(3, 2, 1);
        g3.connect(3, 4, 4);
        g3.connect(4, 1, 2);
        g3.connect(4, 3, 5);

        //g4.load("/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json");
        g4.load("C:\\Users\\yonar\\IdeaProjects\\DirectedWeightedGraph\\Ex2\\data\\G1.json");

        //initializes the algorithm object
        a1.init(g1);
        a2.init(g2);
        a3.init(g3);

        //change initialization flag to true
        this.initialized = true;
    }


    @Test
    public void init() {
        if (!initialized)
            initTests();
        Assertions.assertNotNull(a1.getGraph());
        Assertions.assertNotNull(a2.getGraph());
        Assertions.assertNotNull(a3.getGraph());
        Assertions.assertEquals(a1.getGraph(), g1); //should assert true seeing as a1.init(g1) does NOT use a deep copy
        Assertions.assertEquals(a2.getGraph(), g2); //same reasoning
        Assertions.assertEquals(a3.getGraph(), g3); //same reasoning
    }

    @Test
    public void getGraph() { //the function itself returns an DirectedWeightedGraph object
        if (!initialized)
            initTests();
        Assertions.assertEquals(a1.getGraph(), g1); //should assert true seeing as a1.init(g1) does NOT use a deep copy
        Assertions.assertEquals(a2.getGraph(), g2); //same reasoning
        Assertions.assertEquals(a3.getGraph(), g3); //same reasoning
    }

    @Test
    public void copy() { //the function itself returns an DirectedWeightedGraph object
        if (!initialized)
            initTests();

        DirectedWeightedGraph copy = a1.copy();

        Assertions.assertEquals(copy.getEdge(1, 2), a1.getGraph().getEdge(1, 2));
        Assertions.assertEquals(copy.getEdge(1, 3), a1.getGraph().getEdge(1, 3));
        Assertions.assertEquals(copy.getEdge(2, 3), a1.getGraph().getEdge(2, 3));
        Assertions.assertEquals(copy.getEdge(4, 5), a1.getGraph().getEdge(4, 5));
        Assertions.assertEquals(0, copy.getNode(1).getLocation().distance(a1.getGraph().getNode(1).getLocation()));
        Assertions.assertEquals(0, copy.getNode(2).getLocation().distance(a1.getGraph().getNode(2).getLocation()));
        Assertions.assertEquals(0, copy.getNode(3).getLocation().distance(a1.getGraph().getNode(3).getLocation()));

    }

    @Test
    public void isConnected() { //the function itself returns a boolean value
        if (!initialized)
            initTests();
        Assertions.assertTrue(a1.isConnected()); //every node can access the others (not directly!)
        Assertions.assertFalse(a2.isConnected()); //has two forests - so false!
    }

    @Test
    public void shortestPathDist() { //the function itself returns a double value
        if (!initialized)
            initTests();

        double expDist = 5; // 1 + 1 + 1 + 1 +1 to get from 1 -> 6
        double dist = a1.shortestPathDist(0, 5);
        Assertions.assertEquals(expDist, dist);

        expDist = 4;
        dist = a1.shortestPathDist(0, 4);
        Assertions.assertEquals(expDist, dist);

        expDist = 5;
        dist = a2.shortestPathDist(0, 1);
        Assertions.assertEquals(expDist, dist);

        expDist = -1;
        dist = a2.shortestPathDist(0, 5);
        Assertions.assertEquals(expDist, dist);
    }

    @Test
    public void shortestPath() { //the function itself returns a List<NodeData> object
        if (!initialized)
            initTests();

        //block 1
        List<NodeData> path = a1.shortestPath(0, 5);
        LinkedList<NodeData> expPath = new LinkedList<>();
        expPath.add(new Node_data(0, new Geo_Location(0, 0, 0)));
        expPath.add(new Node_data(1, new Geo_Location(1, 0, 0)));
        expPath.add(new Node_data(2, new Geo_Location(2, 0, 0)));
        expPath.add(new Node_data(3, new Geo_Location(2, 1, 0)));
        expPath.add(new Node_data(4, new Geo_Location(1, 1, 0)));
        expPath.add(new Node_data(5, new Geo_Location(0, 1, 0)));

        System.out.println("expected " + expPath);
        System.out.println("actual " + path);
        System.out.println();

        Assertions.assertEquals(expPath.size(), path.size());
        for (int i = 0; i < path.size(); i++) {
            Assertions.assertEquals(expPath.get(i).getKey(), path.get(i).getKey());
            Assertions.assertEquals(expPath.get(i).getLocation().x(), path.get(i).getLocation().x());
            Assertions.assertEquals(expPath.get(i).getLocation().y(), path.get(i).getLocation().y());
            Assertions.assertEquals(expPath.get(i).getLocation().z(), path.get(i).getLocation().z());
        }

        //block 2
        path = a2.shortestPath(0, 1); //this case is more complex.
        expPath = new LinkedList<>();
        expPath.add(new Node_data(0, new Geo_Location(0, 0, 0)));
        expPath.add(new Node_data(3, new Geo_Location(0, 1, 0)));
        expPath.add(new Node_data(2, new Geo_Location(1, 1, 0)));
        expPath.add(new Node_data(1, new Geo_Location(1, 0, 0)));

        System.out.println("expected " + expPath);
        System.out.println("actual " + path);
        System.out.println();

        Assertions.assertEquals(expPath.size(), path.size());
        for (int i = 0; i < path.size(); i++) {
            Assertions.assertEquals(expPath.get(i).getKey(), path.get(i).getKey());
            Assertions.assertEquals(expPath.get(i).getLocation().x(), path.get(i).getLocation().x());
            Assertions.assertEquals(expPath.get(i).getLocation().y(), path.get(i).getLocation().y());
            Assertions.assertEquals(expPath.get(i).getLocation().z(), path.get(i).getLocation().z());
        }

        //block 3
        path = a2.shortestPath(1, 5); //this case is simple and should just return a null path.
        Assertions.assertNull(path);
    }

    @Test
    public void center() { //the function itself returns a NodeData object
        if (!initialized)
            initTests();

        //for a1, every single node has the same access to other nodes so no point in testing on it.
        NodeData node;

        node = a2.center();
        Assertions.assertNull(node);

        node = a3.center();
        Assertions.assertEquals(0, node.getKey());
        Assertions.assertEquals(node.getLocation().x(), 0);
        Assertions.assertEquals(node.getLocation().y(), 0);
        Assertions.assertEquals(node.getLocation().z(), 0);
    }

    @Test
    public void tsp() { //the function itself returns a List<NodeData> object
        if (!initialized)
            initTests();

        LinkedList<NodeData> expPath = new LinkedList<>();
        expPath.add(new Node_data(0, new Geo_Location(0, 0, 0)));
        expPath.add(new Node_data(1, new Geo_Location(0, 1, 0)));
        expPath.add(new Node_data(4, new Geo_Location(1, 0, 0)));
        expPath.add(new Node_data(3, new Geo_Location(-1, 0, 0)));
        expPath.add(new Node_data(2, new Geo_Location(0, -1, 0)));
        HashMap<Integer, NodeData> expPathHashMap = new HashMap<>();
        for (NodeData nodeData : expPath) {
            expPathHashMap.put(nodeData.getKey(), nodeData);
        }

        List<NodeData> path = a3.tsp(expPath); //technically contains all my "cities".

        double sum = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            sum += a3.getGraph().getEdge(path.get(i).getKey(), path.get(i + 1).getKey()).getWeight();
        }

        System.out.println(path);

        Assertions.assertEquals(4.0, sum, 2);

        for (NodeData nodeData : path) {
            Assertions.assertTrue(expPathHashMap.containsKey(nodeData.getKey()));
        }
    }

    @Test
    public void save() { //the function itself returns a boolean value
        if (!initialized)
            initTests();
        Assertions.assertTrue(a1.save("OutputTest.json"));
    }

    @Test
    public void load() { //the function itself returns a boolean value
        if (!initialized)
            initTests();
        Assertions.assertNotNull(g4.getGraph().getNode(16));
        Assertions.assertNotNull(g4.getGraph().getEdge(0, 16));
    }

}
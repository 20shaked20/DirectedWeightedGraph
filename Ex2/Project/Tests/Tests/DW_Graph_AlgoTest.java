//package Tests;
//
//import api.*;
//import Main.*;
//import org.junit.Assert;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//
//class DW_Graph_AlgoTest {
//
//    //declaring objects to be used with testing.
//    private DW_Graph g1 = new DW_Graph(); // simple one way circled graph
//    private DW_Graph g2 = new DW_Graph(); // 2 forested graph.
//    private DW_Graph g3 = new DW_Graph(); // strongly connected, but intricate graph for center and tsp testing.
//    private DW_Graph_Algo a1 = new DW_Graph_Algo();
//    private DW_Graph_Algo a2 = new DW_Graph_Algo();
//    private DW_Graph_Algo a3 = new DW_Graph_Algo();
//    private boolean initialized = false;
//
//    public void initTests(){
//        //inits g1 as a 1 -1-> 2 -1-> 3 -1-> 4 -1-> 5 -1-> 6 -1-> 1 dwg with all weights = 1 and a one directional circle
//        Geo_Location tmpG = new Geo_Location(0,0,0);
//        Node_data tmpN = new Node_data(1, tmpG);
//        g1.addNode(tmpN);
//        tmpG = new Geo_Location(1,0,0);
//        tmpN = new Node_data(2, tmpG);
//        g1.addNode(tmpN);
//        tmpG = new Geo_Location(2,0,0);
//        tmpN = new Node_data(3, tmpG);
//        g1.addNode(tmpN);
//        tmpG = new Geo_Location(2,1,0);
//        tmpN = new Node_data(4, tmpG);
//        g1.addNode(tmpN);
//        tmpG = new Geo_Location(1,1,0);
//        tmpN = new Node_data(5, tmpG);
//        g1.addNode(tmpN);
//        tmpG = new Geo_Location(0,1,0);
//        tmpN = new Node_data(6, tmpG);
//        g1.addNode(tmpN);
//        g1.connect(1,2,1);
//        g1.connect(2,3,1);
//        g1.connect(3,4,1);
//        g1.connect(4,5,1);
//        g1.connect(5,6,1);
//        g1.connect(6,1,1);
//
//        //inits g2 as a small graph with two forests
//        tmpG = new Geo_Location(0,0,0);
//        tmpN = new Node_data(1, tmpG);
//        g2.addNode(tmpN);
//        tmpG = new Geo_Location(1,0,0);
//        tmpN = new Node_data(2, tmpG);
//        g2.addNode(tmpN);
//        tmpG = new Geo_Location(1,1,0);
//        tmpN = new Node_data(3, tmpG);
//        g2.addNode(tmpN);
//        tmpG = new Geo_Location(0,1,0);
//        tmpN = new Node_data(4, tmpG);
//        g2.addNode(tmpN);
//        tmpG = new Geo_Location(1,1,1);
//        tmpN = new Node_data(5, tmpG);
//        g2.addNode(tmpN);
//        tmpG = new Geo_Location(2,2,2);
//        tmpN = new Node_data(6, tmpG);
//        g2.addNode(tmpN);
//        g2.connect(1,2,10);
//        g2.connect(1,3,6);
//        g2.connect(1,4,3);
//        g2.connect(2,1,1);
//        g2.connect(3,2,1);
//        g2.connect(4,3,1);
//
//        g3.addNode(new Node_data(1, new Geo_Location(0,0,0)));
//        g3.addNode(new Node_data(2, new Geo_Location(0,1,0)));
//        g3.addNode(new Node_data(3, new Geo_Location(0,-1,0)));
//        g3.addNode(new Node_data(4, new Geo_Location(-1,0,0)));
//        g3.addNode(new Node_data(5, new Geo_Location(1,0,0)));
//        g3.connect(1,2,1);
//        g3.connect(1,3,1);
//        g3.connect(1,4,1);
//        g3.connect(1,5,1);
//        g3.connect(2,3,1);
//        g3.connect(2,5,1);
//        g3.connect(3,1,1);
//        g3.connect(3,2,1);
//        g3.connect(3,4,10);
//        g3.connect(4,3,1);
//        g3.connect(4,5,4);
//        g3.connect(5,2,2);
//        g3.connect(5,4,5);
//
//        //initializes the algorithm object
//        a1.init(g1);
//        a2.init(g2);
//        a3.init(g3);
//    }
//
//
//
//    @Test
//    public void init() {
//        if (!initialized)
//            initTests();
//        Assert.assertTrue(a1.getGraph()!=null);
//        Assert.assertTrue(a2.getGraph()!=null);
//        Assert.assertTrue(a3.getGraph()!=null);
//        Assert.assertTrue(a1.getGraph().equals(g1)); //should assert true seeing as a1.init(g1) does NOT use a deep copy
//        Assert.assertTrue(a2.getGraph().equals(g2)); //same reasoning
//        Assert.assertTrue(a3.getGraph().equals(g3)); //same reasoning
//    }
//
//    @Test
//    public void getGraph() { //the function itself returns an DirectedWeightedGraph object
//        if (!initialized)
//            initTests();
//        Assert.assertTrue(a1.getGraph().equals(g1)); //should assert true seeing as a1.init(g1) does NOT use a deep copy
//        Assert.assertTrue(a2.getGraph().equals(g2)); //same reasoning
//        Assert.assertTrue(a3.getGraph().equals(g3)); //same reasoning
//    }
//
//    @Test
//    public void copy() { //the function itself returns an DirectedWeightedGraph object
//        if (!initialized)
//            initTests();
//
//        DW_Graph copy = (DW_Graph)((DW_Graph_Algo) a1.copy()).getGraph();
//        Assertions.assertEquals(copy.getEdge(1,2),a1.getGraph().getEdge(1,2));
//        Assertions.assertEquals(copy.getEdge(1,3),a1.getGraph().getEdge(1,3));
//        Assertions.assertEquals(copy.getEdge(2,3),a1.getGraph().getEdge(2,3));
//        Assertions.assertEquals(copy.getEdge(4,5),a1.getGraph().getEdge(4,5));
//        Assertions.assertTrue(copy.getNode(1).getLocation().distance(a1.getGraph().getNode(1).getLocation())==0);
//        Assertions.assertTrue(copy.getNode(2).getLocation().distance(a1.getGraph().getNode(2).getLocation())==0);
//        Assertions.assertTrue(copy.getNode(3).getLocation().distance(a1.getGraph().getNode(3).getLocation())==0);
//
//    }
//
//    @Test
//    public void isConnected() { //the function itself returns a boolean value
//        if (!initialized)
//            initTests();
//        Assert.assertTrue(a1.isConnected()); //every node can access the others (not directly!)
//        Assert.assertFalse(a2.isConnected()); //has two forests - so false!
//    }
//
//    @Test
//    public void shortestPathDist() { //the function itself returns a double value
//        if (!initialized)
//            initTests();
//
//        double expDist = 5; // 1 + 1 + 1 + 1 +1 to get from 1 -> 6
//        double dist = a1.shortestPathDist(1,6);
//        Assertions.assertEquals(expDist,dist);
//
//        expDist = 4;
//        dist = a1.shortestPathDist(1,5);
//        Assertions.assertEquals(expDist,dist);
//
//        expDist = 5;
//        dist = a2.shortestPathDist(1,2);
//        Assertions.assertEquals(expDist,dist);
//
//        expDist = -1;
//        dist = a2.shortestPathDist(1,5);
//        Assertions.assertEquals(expDist,dist);
//    }
//
//    @Test
//    public void shortestPath(int src, int dest) { //the function itself returns a List<NodeData> object
//        if (!initialized)
//            initTests();
//
//        //block 1
//        List<NodeData> path = a1.shortestPath(1, 6);
//        DW_Graph tmp = (DW_Graph) a1.getGraph();
//        LinkedList<NodeData> expPath = new LinkedList<>();
//        expPath.add(new Node_data(1,new Geo_Location(0,0,0)));
//        expPath.add(new Node_data(2,new Geo_Location(1,0,0)));
//        expPath.add(new Node_data(3,new Geo_Location(2,0,0)));
//        expPath.add(new Node_data(4,new Geo_Location(2,1,0)));
//        expPath.add(new Node_data(5,new Geo_Location(1,1,0)));
//        expPath.add(new Node_data(6,new Geo_Location(0,1,0)));
//
//        for(int i = 0; i < path.size(); i++){
//             Assertions.assertEquals(path.get(i).getKey(),expPath.get(i).getKey());
//             Assertions.assertEquals(path.get(i).getLocation().x(), expPath.get(i).getLocation().x());
//             Assertions.assertEquals(path.get(i).getLocation().y(), expPath.get(i).getLocation().y());
//             Assertions.assertEquals(path.get(i).getLocation().z(), expPath.get(i).getLocation().z());
//        }
//
//        //block 2
//        path = a2.shortestPath(1,2); //this case is more complex.
//        tmp = (DW_Graph) a2.getGraph();
//        expPath = new LinkedList<>();
//        expPath.add(new Node_data(1,new Geo_Location(0,0,0)));
//        expPath.add(new Node_data(4,new Geo_Location(0,1,0)));
//        expPath.add(new Node_data(3,new Geo_Location(1,1,0)));
//        expPath.add(new Node_data(2,new Geo_Location(1,0,0)));
//
//        for(int i = 0; i < path.size(); i++){
//            Assertions.assertEquals(path.get(i).getKey(),expPath.get(i).getKey());
//            Assertions.assertEquals(path.get(i).getLocation().x(), expPath.get(i).getLocation().x());
//            Assertions.assertEquals(path.get(i).getLocation().y(), expPath.get(i).getLocation().y());
//            Assertions.assertEquals(path.get(i).getLocation().z(), expPath.get(i).getLocation().z());
//        }
//
//        for(int i = 0; i < path.size()-1; i++){
//             tmp.getEdge(path.get(i).getKey(),path.get(i+1).getKey()).getWeight();
//        }
//
//        //block 3
//        path = a2.shortestPath(1,5); //this case is simple and should just return a null path.
//        Assertions.assertNull(path);
//    }
//
//    @Test
//    public void center() { //the function itself returns a NodeData object
//        if (!initialized)
//            initTests();
//
//        //for a1, every single node has the same access to other nodes so no point in testing on it.
//
//        NodeData node = a2.center();
//        Assertions.assertNull(node);
//
//        node = a3.center();
//        Assertions.assertTrue(node.getKey() == 1);
//        Assertions.assertEquals(node.getLocation().x(),0);
//        Assertions.assertEquals(node.getLocation().y(),0);
//        Assertions.assertEquals(node.getLocation().z(),0);
//    }
//
//    @Test
//    public void tsp() { //the function itself returns a List<NodeData> object
//        if (!initialized)
//            initTests();
//
//        LinkedList<NodeData> expPath = new LinkedList<>();
//        expPath.add(new Node_data(1, new Geo_Location(0,0,0)));
//        expPath.add(new Node_data(2, new Geo_Location(0,1,0)));
//        expPath.add(new Node_data(5, new Geo_Location(1,0,0)));
//        expPath.add(new Node_data(4, new Geo_Location(-1,0,0)));
//        expPath.add(new Node_data(3, new Geo_Location(0,-1,0)));
//        expPath.add(new Node_data(1, new Geo_Location(0,0,0)));
//
//        List<NodeData> path = a3.tsp(expPath); //technically contains all my "cities".
//
//        for(int i = 0; i < path.size(); i++){
//            Assertions.assertEquals(path.get(i).getKey(),expPath.get(i).getKey());
//            Assertions.assertEquals(path.get(i).getLocation().x(), expPath.get(i).getLocation().x());
//            Assertions.assertEquals(path.get(i).getLocation().y(), expPath.get(i).getLocation().y());
//            Assertions.assertEquals(path.get(i).getLocation().z(), expPath.get(i).getLocation().z());
//        }
//    }
//
//    @Test
//    public void save() { //the function itself returns a boolean value
//        if (!initialized)
//            initTests();
//    }
//
//    @Test
//    public void load() { //the function itself returns a boolean value
//        if (!initialized)
//            initTests();
//    }
//
//}
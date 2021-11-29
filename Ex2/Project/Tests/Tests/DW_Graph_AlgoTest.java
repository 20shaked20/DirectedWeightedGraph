package Tests;

import api.*;
import Main.*;
import org.junit.jupiter.api.Test;

import java.util.List;

class DW_Graph_AlgoTest {

    //declaring objects to be used with testing.
    private DW_Graph g1 = new DW_Graph(); // simple one way circled graph
    private DW_Graph g2 = new DW_Graph(); // 2 forested graph.
    private boolean initialized = false;

    public void initTests(){
        //inits g1 as a 1 -1-> 2 -1-> 3 -1-> 4 -1-> 5 -1-> 6 -1-> 1 dwg with all weights = 1 and a one directional circle
        Geo_Location tmpG = new Geo_Location(0,0,0);
        Node_data tmpN = new Node_data(1, tmpG);
        g1.addNode(tmpN);
        tmpG = new Geo_Location(1,0,0);
        tmpN = new Node_data(2, tmpG);
        g1.addNode(tmpN);
        tmpG = new Geo_Location(2,0,0);
        tmpN = new Node_data(3, tmpG);
        g1.addNode(tmpN);
        tmpG = new Geo_Location(2,1,0);
        tmpN = new Node_data(4, tmpG);
        g1.addNode(tmpN);
        tmpG = new Geo_Location(1,1,0);
        tmpN = new Node_data(5, tmpG);
        g1.addNode(tmpN);
        tmpG = new Geo_Location(0,1,0);
        tmpN = new Node_data(6, tmpG);
        g1.addNode(tmpN);
        g1.connect(1,2,1);
        g1.connect(2,3,1);
        g1.connect(3,4,1);
        g1.connect(4,5,1);
        g1.connect(5,6,1);
        g1.connect(6,1,1);

        //inits g2 as a small graph with two forests
        tmpG = new Geo_Location(0,0,0);
        tmpN = new Node_data(1, tmpG);
        g2.addNode(tmpN);
        tmpG = new Geo_Location(1,0,0);
        tmpN = new Node_data(2, tmpG);
        g2.addNode(tmpN);
        tmpG = new Geo_Location(1,1,0);
        tmpN = new Node_data(3, tmpG);
        g2.addNode(tmpN);
        tmpG = new Geo_Location(0,1,0);
        tmpN = new Node_data(4, tmpG);
        g2.addNode(tmpN);
        tmpG = new Geo_Location(1,1,1);
        tmpN = new Node_data(5, tmpG);
        g2.addNode(tmpN);
        tmpG = new Geo_Location(2,2,2);
        tmpN = new Node_data(6, tmpG);
        g2.addNode(tmpN);
        g2.connect(1,2,10);
        g2.connect(1,3,6);
        g2.connect(1,4,3);
        g2.connect(2,1,1);
        g2.connect(3,2,1);
        g2.connect(4,3,1);
    }



    @Test
    public void init(DirectedWeightedGraph g) {
        if (!initialized)
            initTests();

    }

    @Test
    public void getGraph() { //the function itself returns an DirectedWeightedGraph object
        if (!initialized)
            initTests();
    }

    @Test
    public void copy() { //the function itself returns an DirectedWeightedGraph object
        if (!initialized)
            initTests();
    }

    @Test
    public void isConnected() { //the function itself returns a boolean value
        if (!initialized)
            initTests();
    }

    @Test
    public void shortestPathDist(int src, int dest) { //the function itself returns a double value
        if (!initialized)
            initTests();
    }

    @Test
    public void shortestPath(int src, int dest) { //the function itself returns a List<NodeData> object
        if (!initialized)
            initTests();
    }

    @Test
    public void center() { //the function itself returns a NodeData object
        if (!initialized)
            initTests();
    }

    @Test
    public void tsp(List<NodeData> cities) { //the function itself returns a List<NodeData> object
        if (!initialized)
            initTests();
    }

    @Test
    public void save(String file) { //the function itself returns a boolean value
        if (!initialized)
            initTests();
    }

    @Test
    public void load(String file) { //the function itself returns a boolean value
        if (!initialized)
            initTests();
    }

}
package Tests;

import api.*;
import Main.*;
import org.junit.jupiter.api.Test;

class DW_GraphTest {
    private DW_Graph g1 = new DW_Graph(); // simple one way circled graph
    private DW_Graph g2 = new DW_Graph(); // 2 forested graph.
    private DW_Graph g3 = new DW_Graph(); // strongly connected, but intricate graph for center and tsp testing.
    private DW_Graph_Algo a1 = new DW_Graph_Algo();
    private DW_Graph_Algo a2 = new DW_Graph_Algo();
    private DW_Graph_Algo a3 = new DW_Graph_Algo();
    private boolean initialized = false;

    public void initTests(){
        //inits g1 as a 0 -1-> 1 -1-> 2 -1-> 3 -1-> 4 -1-> 5 -1-> 1 dwg with all weights = 1 and a one directional circle
        Node_data tmpN = new Node_data(0, new Geo_Location(0,0,0));
        g1.addNode(tmpN);
        tmpN = new Node_data(1, new Geo_Location(1,0,0));
        g1.addNode(tmpN);
        tmpN = new Node_data(2, new Geo_Location(2,0,0));
        g1.addNode(tmpN);
        tmpN = new Node_data(3, new Geo_Location(2,1,0));
        g1.addNode(tmpN);
        tmpN = new Node_data(4, new Geo_Location(1,1,0));
        g1.addNode(tmpN);
        tmpN = new Node_data(5, new Geo_Location(0,1,0));
        g1.addNode(tmpN);
        g1.connect(0,1,1);
        g1.connect(1,2,1);
        g1.connect(2,3,1);
        g1.connect(3,4,1);
        g1.connect(4,5,1);
        g1.connect(5,0,1);

        //inits g2 as a small graph with two forests
        tmpN = new Node_data(0, new Geo_Location(0,0,0));
        g2.addNode(tmpN);
        tmpN = new Node_data(1, new Geo_Location(1,0,0));
        g2.addNode(tmpN);
        tmpN = new Node_data(2, new Geo_Location(1,1,0));
        g2.addNode(tmpN);
        tmpN = new Node_data(3, new Geo_Location(0,1,0));
        g2.addNode(tmpN);
        tmpN = new Node_data(4, new Geo_Location(1,1,1));
        g2.addNode(tmpN);
        tmpN = new Node_data(5, new Geo_Location(2,2,2));
        g2.addNode(tmpN);
        g2.connect(0,1,10);
        g2.connect(0,2,6);
        g2.connect(0,3,3);
        g2.connect(1,0,1);
        g2.connect(2,1,1);
        g2.connect(3,2,1);
        g2.connect(4,5,1);
        g2.connect(5,4,1);

        g3.addNode(new Node_data(0, new Geo_Location(0,0,0)));
        g3.addNode(new Node_data(1, new Geo_Location(0,1,0)));
        g3.addNode(new Node_data(2, new Geo_Location(0,-1,0)));
        g3.addNode(new Node_data(3, new Geo_Location(-1,0,0)));
        g3.addNode(new Node_data(4, new Geo_Location(1,0,0)));
        g3.connect(0,1,1);
        g3.connect(0,2,1);
        g3.connect(0,3,1);
        g3.connect(0,4,1);
        g3.connect(1,2,1);
        g3.connect(1,4,1);
        g3.connect(2,0,1);
        g3.connect(2,1,1);
        g3.connect(2,3,10);
        g3.connect(3,2,1);
        g3.connect(3,4,4);
        g3.connect(4,1,2);
        g3.connect(4,3,5);

        //initializes the algorithm object
        a1.init(g1);
        a2.init(g2);
        a3.init(g3);

        //change initialization flag to true
        this.initialized = true;
    }

    //TODO: Implement basic tests

    @Test
    void getNode() {
        if (!initialized)
            initTests();
    }

    @Test
    void getEdge() {
        if (!initialized)
            initTests();
    }

    @Test
    void addNode() {
        if (!initialized)
            initTests();
    }

    @Test
    void connect() {
        if (!initialized)
            initTests();
    }

    @Test
    void nodeIter() {
        if (!initialized)
            initTests();
    }

    @Test
    void edgeIter() {
        if (!initialized)
            initTests();
    }

    @Test
    void testEdgeIter() {
        if (!initialized)
            initTests();
    }

    @Test
    void removeNode() {
        if (!initialized)
            initTests();
    }

    @Test
    void removeEdge() {
        if (!initialized)
            initTests();
    }

    @Test
    void pointerNodes() {
        if (!initialized)
            initTests();
    }

    @Test
    void nodeSize() {
        if (!initialized)
            initTests();
    }

    @Test
    void edgeSize() {
        if (!initialized)
            initTests();
    }

    @Test
    void getMC() {
        if (!initialized)
            initTests();
    }
}
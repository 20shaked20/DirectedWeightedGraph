package Tests;

import api.*;
import Main.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

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

        Assertions.assertEquals(3,g3.getNode(3).getId());
        Assertions.assertEquals(1,g1.getNode(1).getId());
        Assertions.assertEquals(2,g2.getNode(2).getId());
        Assertions.assertEquals(5,g2.getNode(5).getId());
        Assertions.assertEquals(4,g3.getNode(4).getId());
        Assertions.assertEquals(3,g3.getNode(3).getId());
    }

    @Test
    void getEdge() {
        if (!initialized)
            initTests();
        EdgeData edge = g1.getEdge(0,1);
        Assertions.assertEquals(1,edge.getWeight());
        edge = g3.getEdge(2,3);
        Assertions.assertEquals(10,edge.getWeight());
        edge = g3.getEdge(4,3);
        Assertions.assertEquals(5,edge.getWeight());
        edge = g3.getEdge(3,4);
        Assertions.assertEquals(4,edge.getWeight());
        edge = g2.getEdge(0,1);
        Assertions.assertEquals(10,edge.getWeight());
        edge = g2.getEdge(0,4);
        Assertions.assertNull(edge);
    }

    @Test
    void addNode() {
        if (!initialized)
            initTests();
        g1.addNode(new Node_data(7,new Geo_Location(1,1,1)));
        NodeData newNode = g1.getNode(7);
        Assertions.assertEquals(7,newNode.getId());
        Assertions.assertEquals(0, newNode.getLocation().distance(new Geo_Location(1,1,1)));

    }

    @Test
    void connect() {
        if (!initialized)
            initTests();
        g1.connect(0,3,2);
        EdgeData newEdge = g1.getEdge(0,3);
        Assertions.assertEquals(2,newEdge.getWeight());
    }

    @Test
    void nodeIter() {
        if (!initialized)
            initTests();
        Iterator<NodeData> iterator = g1.nodeIter();
        int key;
        while (iterator.hasNext()){
            key = iterator.next().getId();
            Assertions.assertNotNull(g1.getNode(key));
        }
    }

    @Test
    void edgeIter() {
        if (!initialized)
            initTests();
        Iterator<EdgeData> iterator = g1.edgeIter();
        EdgeData temp;
        while (iterator.hasNext()){
            temp = iterator.next();
            Assertions.assertNotNull(g1.getEdge(temp.getSrc(),temp.getDest()));
        }
    }

    @Test
    void testEdgeIter() {
        if (!initialized)
            initTests();
        try {
            Iterator<NodeData> nodeIterator = g1.nodeIter();
            Iterator<EdgeData> iterator;
            EdgeData temp;
            int key;
            while (nodeIterator.hasNext()) {
                key = nodeIterator.next().getId();
                iterator = g1.edgeIter(key);
                while (iterator.hasNext()) {
                    temp = iterator.next();
                    Assertions.assertNotNull(g1.getEdge(temp.getSrc(), temp.getDest()));
                    Assertions.assertNotNull(g1.getNode(temp.getSrc()));
                    Assertions.assertNotNull(g1.getNode(temp.getDest()));
                    Assertions.assertEquals(key, temp.getSrc());
                }
            }
        }
        catch (RuntimeException e){
            Assertions.assertEquals("graph was changed after iterator creation",e.getMessage());
        }
    }

    @Test
    void removeNode() {
        if (!initialized)
            initTests();
        g1.removeNode(0);
        Assertions.assertNull(g1.getNode(0));
        try {
            Assertions.assertNull(g1.getEdge(0,1));
        }
        catch (NullPointerException e){
            //this is expected to happen!
        }
    }

    @Test
    void removeEdge() {
        if (!initialized)
            initTests();
        g1.removeEdge(0,1);
        try {
            Assertions.assertNull(g1.getEdge(0,1));
        }
        catch (NullPointerException e){
            //this is expected to happen!
        }
        try {
            g1.connect(0,1,1);
        }
        catch (NullPointerException e){
            Assertions.fail("re-connecting the edge should NOT throw nullPointerExceptions!");
        }
    }

    @Test
    void nodeSize() {
        if (!initialized)
            initTests();
        Assertions.assertEquals(6,g1.nodeSize());
        Assertions.assertEquals(6,g2.nodeSize());
        Assertions.assertEquals(5,g3.nodeSize());
    }

    @Test
    void edgeSize() {
        if (!initialized)
            initTests();
        Assertions.assertEquals(6,g1.edgeSize());
        Assertions.assertEquals(8,g2.edgeSize());
        Assertions.assertEquals(13,g3.edgeSize());
    }

    @Test
    void getMC() {
        if (!initialized)
            initTests();
        Assertions.assertEquals(12,g1.getMC());
        Assertions.assertEquals(14,g2.getMC());
        Assertions.assertEquals(18,g3.getMC());
    }
}
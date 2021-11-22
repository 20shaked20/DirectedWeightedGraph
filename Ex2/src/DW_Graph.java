/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */
import api.EdgeData;
import api.NodeData;

import java.util.Iterator;

/**
 * This is a java class containing methods of a DirectedWeightedGraph.
 * (according to the interface we were given).
 */

public class DW_Graph implements api.DirectedWeightedGraph {
    @Override
    public NodeData getNode(int key) {return null;}


    @Override
    public EdgeData getEdge(int src, int dest) {return null;}

    @Override
    public void addNode(NodeData n) {

    }

    @Override
    public void connect(int src, int dest, double w) {

    }

    @Override
    public Iterator<NodeData> nodeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter() {
        return null;
    }

    @Override
    public Iterator<EdgeData> edgeIter(int node_id) {
        return null;
    }

    @Override
    public NodeData removeNode(int key) {
        return null;
    }

    @Override
    public EdgeData removeEdge(int src, int dest) {
        return null;
    }

    @Override
    public int nodeSize() {
        return 0;
    }

    @Override
    public int edgeSize() {
        return 0;
    }

    @Override
    public int getMC() {
        return 0;
    }

}

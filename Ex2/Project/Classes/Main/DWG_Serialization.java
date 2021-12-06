package Main;

import api.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021,
 * this class is a stripped down version of DW_Graph, used only for properly - and easily - serializing a DW_Graph
 * to .json format.
 */
public class DWG_Serialization implements Serializable {
    List<EdgeData> Edges;
    List<Node_Serialization> Nodes;

    /**
     * This is a constructor method, it used the edges and nodes Iterators implemented in DW_Graph to initialize
     * this class
     * @param graph The DirectedWeightedGraph - (DW_Graph) - object this class serializes
     */
    public DWG_Serialization(DirectedWeightedGraph graph){
        Iterator<NodeData> n = graph.nodeIter();
        Iterator<EdgeData> e = graph.edgeIter();

        Nodes = new ArrayList<>();
        Edges = new ArrayList<>();

        NodeData tempNode;
        while (n.hasNext()){
            tempNode = n.next();
            this.Nodes.add(new Node_Serialization(tempNode.getKey(),tempNode.getLocation()));
        }

        while (e.hasNext()){
            this.Edges.add(e.next());
        }
    }
}

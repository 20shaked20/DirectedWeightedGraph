package Tests;

import api.*;
import Main.*;

public class Algo_tester {

    public static void main(String[] args) {
        DirectedWeightedGraph graph = new DW_Graph();
        Geo_Location xyz = new Geo_Location(9, 9, 0);
        NodeData n0 = new Node_data(0, xyz);
        NodeData n1 = new Node_data(1, xyz);
        NodeData n2 = new Node_data(2, xyz);
        NodeData n3 = new Node_data(3, xyz);
        graph.addNode(n0);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.connect(n0.getKey(), n1.getKey(), 0);
        graph.connect(n0.getKey(), n3.getKey(), 0);
        graph.connect(n1.getKey(), n2.getKey(), 0);
        graph.connect(n2.getKey(), n0.getKey(), 0);
        graph.connect(n3.getKey(), n2.getKey(), 0);

//        String path = "/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json";
//        Json_Deserializer(graph, path);

        DirectedWeightedGraphAlgorithms algo = new DW_Graph_Algo();
        algo.init(graph);
        //algo.getGraph();
        System.out.println(algo.isConnected());


        //Json_Serializer(graph.pointerNodes());

    }
}

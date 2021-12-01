package Tests;

import Main.DW_Graph;
import Main.DW_Graph_Algo;
import Main.Geo_Location;
import Main.Node_data;
import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

public class Algo_tester {

    public static void main(String[] args) {
        DirectedWeightedGraph graph = new DW_Graph();
        Geo_Location xyz = new Geo_Location(9, 9, 0);
        NodeData n0 = new Node_data(0, xyz);
        NodeData n1 = new Node_data(1, xyz);
        NodeData n2 = new Node_data(2, xyz);
        NodeData n3 = new Node_data(3, xyz);
        NodeData n4 = new Node_data(4, xyz);
        graph.addNode(n0);
        graph.addNode(n1);
        graph.addNode(n2);
        graph.addNode(n3);
        graph.addNode(n4);
//        graph.connect(0,1, 1.0);
//        graph.connect( 0,3,5.0);
//        graph.connect(1,2,10.0);
//        graph.connect(2,0,10.0);
//        graph.connect( 3,2,10.0);
        graph.connect(1,3,1.0);
        graph.connect(3,2,5.0);
        graph.connect(1,2,15.0);

        DirectedWeightedGraphAlgorithms algo = new DW_Graph_Algo();
        algo.init(graph);
        System.out.println(algo.shortestPath(1,2).toString());
        //System.out.println(algo.shortestPathDist(1,2));
        //System.out.println(algo.isConnected());


//        for (int i = 0; i < 1000; i++)
//            graph.addNode(new Node_data(i, new Geo_Location(0, 0, 0)));
//
//        for (int i = 1; i < 1000; i++)
//            graph.connect(i - 2, i, 3);
//        algo.init(graph);
//        System.out.println(algo.shortestPath(1,2).toString());
//
//        g1.connect(999,0,0.5);
//        g1.addNode(new Node_data(1000, new Geo_Location(0, 0, 0)));
//        for(int i =1 ;i<1000; i++){
//            g1.connect(1000,i,1);
//        }

    }
}

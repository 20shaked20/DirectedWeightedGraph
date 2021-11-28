package Main; /**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

import java.util.List;

public class DW_Graph_Algo implements DirectedWeightedGraphAlgorithms {
    private DW_Graph graph;

    @Override
    public void init(DirectedWeightedGraph g) {
        this.graph = (DW_Graph) g;
    }

    @Override
    public DirectedWeightedGraph getGraph() {
        return null;
    }

    @Override
    public DirectedWeightedGraph copy() {
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        return 0;
    }

    @Override
    public List<NodeData> shortestPath(int src, int dest) {
        return null;
    }

    @Override
    public NodeData center() {
        return null;
    }

    @Override
    public List<NodeData> tsp(List<NodeData> cities) {
        return null;
    }

    @Override
    public boolean save(String file) {
        return false;
    }

    @Override
    public boolean load(String file) {
        return Json_Helper.Json_Deserializer(this.graph, file);
    }

}

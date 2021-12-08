package Main;

import api.DirectedWeightedGraph;
import api.DirectedWeightedGraphAlgorithms;
import gui.Menu;
// use > swing, dijkstra

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021 ,
 * <p>
 * This class is the main class for Main.Ex2 - your implementation will be tested using this class.
 */
public class Ex2 {
    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return A DirectedWeightedGraph object
     */
    public static DirectedWeightedGraph getGrapq(String json_file) {
        DirectedWeightedGraph ans = new DW_Graph();
        Json_Helper.Json_Deserializer(ans, json_file);
        return ans;
    }

    /**
     * This static function will be used to test your implementation
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     * @return A DirectedWeightedGraphAlgorithms object
     */
    public static DirectedWeightedGraphAlgorithms getGrapgAlgo(String json_file) {
        DirectedWeightedGraphAlgorithms ans = new DW_Graph_Algo();
        ans.init(getGrapq(json_file));
        return ans;
    }

    /**
     * This static function will run your GUI using the json fime.
     *
     * @param json_file - a json file (e.g., G1.json - G3.gson)
     */
    public static void runGUI(String json_file) {
        Menu gui = new Menu(json_file);
    }

    public static void main(String[] args) {
        String file_loc = "/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json";
        runGUI(file_loc);
        //System.out.println(getGrapgAlgo(file_loc).center());
        //System.out.println(getGrapgAlgo(file_loc).shortestPath(3,9));
    }
}
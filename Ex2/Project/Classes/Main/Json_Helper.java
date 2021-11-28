package Main;

import api.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;


public class Json_Helper {

    /**
     * This method deserializes a json file and loads it to objects in our graph ( Nodes, Edges )
     *
     * @param g          - a Directed Weighted Graph
     * @param input_path - Json file location.
     * @return
     */
    public static boolean Json_Deserializer(DW_Graph g, String input_path) {
        File input = new File(input_path);
        JsonElement fileElement;
        {
            try {
                fileElement = JsonParser.parseReader(new FileReader(input));
                JsonObject fileObject = fileElement.getAsJsonObject();

                //System.out.println("Prints EDGES:");
                JsonArray Edges = fileObject.get("Edges").getAsJsonArray();
                for (JsonElement edgeElement : Edges) {
                    JsonObject EdgeObject = edgeElement.getAsJsonObject();

                    //Extract data from object:
                    Integer src = EdgeObject.get("src").getAsInt();
                    //System.out.println("src: " + src);
                    Double w = EdgeObject.get("w").getAsDouble();
                    //System.out.println("w: " + w);
                    Integer dest = EdgeObject.get("dest").getAsInt();
                    //System.out.println("dest: " + dest);
                    g.connect(src, dest, w);
                }
                //System.out.println("Prints Nodes:");
                JsonArray Nodes = fileObject.get("Nodes").getAsJsonArray();
                for (JsonElement nodeElement : Nodes) {
                    JsonObject NodeObject = nodeElement.getAsJsonObject();

                    //Extract data from object:
                    //parsing the position ->
                    String pos = NodeObject.get("pos").getAsString();
                    String[] xyz = pos.split(",");
                    double x = Double.parseDouble(xyz[0]);
                    double y = Double.parseDouble(xyz[1]);
                    double z = Double.parseDouble(xyz[2]);
                    Geo_Location loc = new Geo_Location(x, y, z);

                    // System.out.println("Pos: " + pos);
                    Integer id = NodeObject.get("id").getAsInt();
                    // System.out.println("id: " + id);
                    Node_data n = new Node_data(id, loc);
                    g.addNode(n);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

// TODO: implement serializer, rn basic need to fix to fit my saving method.

    public static void Json_Serializer(HashMap<Integer, NodeData> n) {
//        HashMap<String,String> myMap = new HashMap();
//        myMap.put("one", "hello");
//        myMap.put("two", "world");
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(n);

        System.out.println(json);
    }

    //tester ->
    public static void main(String[] args) {
        DW_Graph graph = new DW_Graph();
        String path = "/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json";
        Json_Deserializer(graph, path);
        Json_Serializer(graph.pointerNodes());
    }

}



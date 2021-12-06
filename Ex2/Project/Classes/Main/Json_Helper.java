package Main;

import api.*;
import com.google.gson.*;
import java.io.*;
import java.util.Iterator;

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021
 */
public class Json_Helper {

    /**
     * This method deserializes a json file and loads it to objects in our graph ( Nodes, Edges )
     *
     * @param g          - a Directed Weighted Graph
     * @param input_path - Json file location.
     * @return true iff Deserialized
     */
    public static boolean Json_Deserializer(DirectedWeightedGraph g, String input_path) {
        File input = new File(input_path);
        JsonElement fileElement;
        {
            try {
                fileElement = JsonParser.parseReader(new FileReader(input));
                JsonObject fileObject = fileElement.getAsJsonObject();

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
                    int id = NodeObject.get("id").getAsInt();
                    // System.out.println("id: " + id);
                    Node_data n = new Node_data(id, loc);
                    g.addNode(n);
                }

                //System.out.println("Prints EDGES:");
                JsonArray Edges = fileObject.get("Edges").getAsJsonArray();
                for (JsonElement edgeElement : Edges) {
                    JsonObject EdgeObject = edgeElement.getAsJsonObject();

                    //Extract data from object:
                    int src = EdgeObject.get("src").getAsInt();
                    //System.out.println("src: " + src);
                    double w = EdgeObject.get("w").getAsDouble();
                    //System.out.println("w: " + w);
                    int dest = EdgeObject.get("dest").getAsInt();
                    //System.out.println("dest: " + dest);
                    g.connect(src, dest, w);
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public static boolean Json_Serializer(DirectedWeightedGraph graph) {
        // https://stackoverflow.com/questions/29319434/how-to-save-data-with-gson-in-a-json-file
        // https://stackoverflow.com/questions/46210867/json-file-i-o-with-pretty-print-format-using-gson-in-java

        DWG_Serialization dwg = new DWG_Serialization(graph);

        try (Writer writer = new FileWriter("Output.json")){
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(dwg , writer);
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //tester ->
    public static void main(String[] args) {
        DW_Graph graph = new DW_Graph();
        String path = "C:/Users/yonar/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json";
        Json_Deserializer(graph, path);
        Iterator<EdgeData> it = graph.edgeIter();
        while (it.hasNext()){
            System.out.println(it.next());
        }
        Json_Serializer(graph);
    }

}



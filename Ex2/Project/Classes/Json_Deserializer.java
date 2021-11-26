import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Json_Deserializer {

    /**
     * This method deserializes a json file and loads it to objects in our graph ( Nodes, Edges )
     *
     * @param g    - a Directed Weighted Graph
     * @param Path - Json file location.
     */
    Json_Deserializer(DW_Graph g, String Path) {
        File input = new File("/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json");
        JsonElement fileElement;
        g = new DW_Graph();

        {
            try {
                fileElement = JsonParser.parseReader(new FileReader(input));
                JsonObject fileObject = fileElement.getAsJsonObject();

                System.out.println("Prints EDGES:");
                JsonArray Edges = fileObject.get("Edges").getAsJsonArray();
                for (JsonElement edgeElement : Edges) {
                    JsonObject EdgeObject = edgeElement.getAsJsonObject();

                    //Extract data from object:
                    Integer src = EdgeObject.get("src").getAsInt();
                    System.out.println("src: " + src);
                    Double w = EdgeObject.get("w").getAsDouble();
                    System.out.println("w: " + w);
                    Integer dest = EdgeObject.get("dest").getAsInt();
                    System.out.println("dest: " + dest);
                    g.connect(src, dest, w);
                }
                System.out.println("Prints Nodes:");
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

                    System.out.println("Pos: " + pos);
                    Integer id = NodeObject.get("id").getAsInt();
                    System.out.println("id: " + id);
                    Node_data n = new Node_data(id, loc, "", 0);
                    g.addNode(n);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //tester ->
    public static void main(String[] args) {
        //Json_Deserializer();
    }

}



package Main;

import api.*;
import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public static boolean Json_Serializer(DirectedWeightedGraph graph, String path_name) {
        // https://stackoverflow.com/questions/29319434/how-to-save-data-with-gson-in-a-json-file
        // https://stackoverflow.com/questions/46210867/json-file-i-o-with-pretty-print-format-using-gson-in-java

        DWG_Serialization dwg = new DWG_Serialization(graph);

        try (Writer writer = new FileWriter(path_name)) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(dwg, writer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

class DWG_Serialization implements Serializable {
    List<EdgeData> Edges;
    List<Node_Serialization> Nodes;

    /**
     * This is a constructor method, it used the edges and nodes Iterators implemented in DW_Graph to initialize
     * this class
     *
     * @param graph The DirectedWeightedGraph - (DW_Graph) - object this class serializes
     */
    public DWG_Serialization(DirectedWeightedGraph graph) {
        Iterator<NodeData> n = graph.nodeIter();
        Iterator<EdgeData> e = graph.edgeIter();

        Nodes = new ArrayList<>();
        Edges = new ArrayList<>();

        NodeData tempNode;
        while (n.hasNext()) {
            tempNode = n.next();
            this.Nodes.add(new Node_Serialization(tempNode.getKey(), tempNode.getLocation()));
        }

        while (e.hasNext()) {
            this.Edges.add(e.next());
        }
    }
}

/**
 * This is a class for serialization only, and is here to easily and smoothly transfer data to the desired .json format.
 */
class Node_Serialization implements Serializable {
    String pos;
    int id;

    /**
     * This is a constructor method, unmentioned parameters are not required for serialization
     *
     * @param id  The id of the node for serialization
     * @param pos a Geo_Location object, the same one that's in the Node_data object
     */
    public Node_Serialization(int id, GeoLocation pos) {
        this.id = id;
        this.pos = pos.toString();
    }
}


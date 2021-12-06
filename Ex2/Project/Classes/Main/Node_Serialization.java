package Main;

import api.*;
import java.io.Serializable;

/**
 * Authors - Yonatan Ratner & Shaked Levi
 * Date - 21.11.2021,
 * This is a class for serialization only, and is here to easily and smoothly transfer data to the desired .json format.
 */
public class Node_Serialization implements Serializable {
    String pos;
    int id;

    /**
     * This is a constructor method, unmentioned parameters are not required for serialization
     * @param id The id of the node for serialization
     * @param pos a Geo_Location object, the same one that's in the Node_data object
     */
    public Node_Serialization(int id, GeoLocation pos) {
        this.id = id;
        this.pos = pos.toString();
    }
}

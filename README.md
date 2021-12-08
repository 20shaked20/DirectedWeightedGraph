# DirectedWeightedGraph

This is a repository of DirectedWeightedGraph as part of an assignment in Ariel University 

## Introduction

In this assignment we are expected to construct and implement solutions to known problems in the subject of Graphs, specifically Directed and Weighted graphs (see links at the end of the readme), and also represent the graph with a gui of our own making, in this exercise we were given free reign on things like which libraries to use and the worst case runtime complexity of algorithms (naturally we are expected to do our best and not concoct O(n!) solutions).

## Approach

Firstly, we set off to understand the assignment, reading exactly what we are trying to accomplish, Then we seperate the work into (mostly) 2 parts, consisting of the algorithms and more the abstract ideas that are required for the completion of the project, and the second part is the more defined work, like saving and reading from a .json file for example, or the things that are concerned with runtime </br>
in simpler terms, we define what's the 'dirty work' and what's not, so we can later finish the 'dirty work' and focus completely on algorithms and brainstorming ideas as needed. </br>
As for the workflow itself, after we have defined what can be done immediatly and what requires thinking, we set off to understanding what interfaces we must implement, in this project these are ``` public interface DirectedWeightedGraph  ``` and ``` public interface DirectedWeightedGraphAlgorithms ```, after that we will implement the bare minimum such that we can run the 'simpler' functions and proceed to finish the 'dirty work' of the project, after that is done we will proceed to design the algorithms we need to implement a solution in pseudo code, and after we are satisfied with our pen & paper solution we will begin to design tests for the functions we know we will implement, and naturally add tests as we go if at any point new functions are needed. </br>

After the groundwork is done we will start implementing our algorithms while using TDD with our pre-designed tests and cases in which we know the answers beforehand.
</br>

Simultaneously, we will research which GUI library best suits our needs for this project, and use similar steps to represent our solution through the GUI we choose to use. </br>

And then we polish until were satisfied. </br>

## The Algorithms
We will not go into the heavy depths of each algorithm, but rather describe what solution we used. </br>

- ``` shortestPath(int src, int dest) ``` and ``` shortestPathDist(int src, int dest) ``` : </br>
Using the dijkstra algorithm we managed to get accurate results in ``` O(|V| * (|E| + |V|)) ```, using the pseudo-code located in the wiki page as a guideline for implementation and private properties of nodes such as tag, info, and weight.
- ``` center() ``` : </br>
Using the dijkstra algorithm to calculate the sum of distances from each node the every other one, and then dividing by the amount of nodes to get an average. </br>
after that we simply iterate to find the node with the best average distance to every other node.
- ``` tsp(List<NodeData> cities) ``` : </br>
Firstly, TSP is an np-hard problem and thus difficult to find an accurate solution for, and in this exercise we were allowed to visit a city (node) more than once </br>
Therefore we used a heuristic and greedy algorithm that does not guarantee a solution but runs in ``` O(|V|^3) ```, and when it does provide a solution it's good, but probably not the truly optimal one ( around 90% match ). </br>
This algorithm attempts to travel from a node to every other node while taking the cheapest neighbour to travel to, with a heavy bias towards unvisited nodes, this is calculated for every node, and then the best path is evaluated and returned, However this algorithm is flawed for specific graphs where it has trouble 'escaping' circles and can be optimized with a topographical sort and other methods, in short - this is a good, but improvable attempt.

## The Classes
For nearly every class there is an interface object in the api folder, which goes into detail about the methods the classes must have and what they do, so unless it's not stated somewhere we will not elaborate

- ``` DW_Graph implements DirectedWeightedGraph  ```
- ``` DW_Graph_Algo implements DirectedWeightedGraphAlgorithms ```
- ``` Edge_data implements EdgeData ``` 
- ``` Node_data implements NodeData ``` 
- ``` Geo_Location implements GeoLocation ```
- ``` Json_Helper ``` : </br>
This class Serialized and Deserializes .json files to the format given to us during the exercise, please see Ex2/data for 3 examples of such jsons, and it uses the Gson library to accomplish this
- ``` Ex2 ```: </br>
This class has 3 methods - ``` getGrapq(String json_file) ```, ``` getGrapgAlgo ```, and ``` runGUI(String json_file) ``` </br>
which, given the location string of a .json file (which describes a graph) returns the appropriate object or runs the gui

## How To use

## File Hierarchy

![image](https://user-images.githubusercontent.com/73063105/145205941-3674ece7-454b-401f-8699-d1ad3f15bd63.png)


## Reading Material
- About Directed, Weighted, and Directed + Weighted graphs: http://math.oxford.emory.edu/site/cs171/directedAndEdgeWeightedGraphs/
- Shortest Path: https://en.wikipedia.org/wiki/Shortest_path_problem#Algorithms
- Dijkstra: https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
- Graph Center: https://en.wikipedia.org/wiki/Graph_center
- Travelling Salesman Problem (TSP): https://en.wikipedia.org/wiki/Travelling_salesman_problem
- Gson library: https://github.com/google/gson
- Heuristics https://en.wikipedia.org/wiki/Heuristic_(computer_science)


package gui;

import Main.DW_Graph;
import Main.DW_Graph_Algo;
import Main.Geo_Location;
import Main.Node_data;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

public class Menu extends JFrame implements ActionListener {

    private JPanel Screen = new JPanel(new GridLayout(5, 1, 0, 0));
    private JButton Json = new JButton("Load Json File");
    private JButton ShortestPathDist = new JButton("Run ShortestPathDist");
    private JButton ShortestPath = new JButton("Run ShortestPath");
    private JButton Center = new JButton("Run Center");
    private JButton Tsp = new JButton("Run Tsp");

    private DirectedWeightedGraph graph = new DW_Graph();
    private DirectedWeightedGraphAlgorithms graph_algo = new DW_Graph_Algo();


    public Menu() {
        this.graph_algo.init(this.graph);
        this.graph_algo.load("/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json");
        this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER)); // center the buttons.
        this.ActionListener();
        this.setColor();
        this.Addons();
        this.setSize();
        this.Screen.setSize(500, 500);
        this.Screen.setVisible(true);
        this.add(Screen);
        this.setSize(500, 500);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }


    private void ActionListener() {
        Json.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (ae.getSource() == Json) {
                    JFileChooser fc = new JFileChooser();
                    int i = fc.showOpenDialog(Json);
                    if (i == JFileChooser.APPROVE_OPTION) {
                        File f = fc.getSelectedFile();
                        String filepath = f.getPath();
                        try {
                            BufferedReader br = new BufferedReader(new FileReader(filepath));
                            String s1 = "", s2 = "";
                            while ((s1 = br.readLine()) != null) {
                                s2 += s1 + "\n";
                            }
                            br.close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        ShortestPathDist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo);
            }
        });
        ShortestPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo);
            }
        });
        Center.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo);
            }
        });
        Tsp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo);
            }
        });
    }

    private void setColor() {
        Json.setForeground(Color.BLACK);
        ShortestPathDist.setForeground(Color.RED);
        ShortestPath.setForeground(Color.RED);
        Center.setForeground(Color.RED);
        Tsp.setForeground(Color.RED);
    }

    private void setSize() {
        Json.setSize(220, 30);
        ShortestPathDist.setSize(220, 30);
        ShortestPath.setSize(220, 30);
        Center.setSize(220, 30);
        Tsp.setSize(220, 30);
    }

    private void Addons() {
        Screen.add(Json);
        Screen.add(ShortestPathDist);
        Screen.add(ShortestPath);
        Screen.add(Center);
        Screen.add(Tsp);
    }


    public static void main(String[] args) {
        new Menu();
        //new Menu("/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

// SecondFrame.java

/**
 * This class is responsible for the second frame layout, it will pop up the new graphs using the algorithms.
 */
class SecondFrame extends JFrame {
    private DirectedWeightedGraphAlgorithms graph;
    private int[] nodeXpos;
    private int[] nodeYpos;
    private int Width = 850;
    private int Height = 850;

    public SecondFrame(DirectedWeightedGraphAlgorithms graph) {
        this.graph = graph;
        this.nodeXpos = new int[this.graph.getGraph().nodeSize()];
        this.nodeYpos = new int[this.graph.getGraph().nodeSize()];
        this.update_x_y_pos();
        DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeXpos, this.nodeYpos);
        this.add(m);
        this.setSize(this.Width, this.Height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void update_x_y_pos() {
        Iterator nodes = this.graph.getGraph().nodeIter();
        NodeData curr_node;
        GeoLocation curr_geo;
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        double x, y;
        while (nodes.hasNext()) {
            curr_node = (NodeData) nodes.next();
            curr_geo = curr_node.getLocation();
            x = curr_geo.x();
            y = curr_geo.y();
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }
        double uintX = this.Width / (maxX - minX) * 0.9;
        double unitY = this.Height / (maxY - minY) * 0.8;

        nodes = this.graph.getGraph().nodeIter();

        while (nodes.hasNext()) {
            curr_node = (NodeData) nodes.next();
            curr_geo = curr_node.getLocation();
            x = (curr_geo.x() - minX) * uintX;
            y = (curr_geo.y() - minY) * unitY;

            this.nodeXpos[curr_node.getKey()] = (int) x;
            this.nodeYpos[curr_node.getKey()] = (int) y;

        }
    }
}

class DisplayGraphics extends Canvas {
    private DirectedWeightedGraphAlgorithms graph;
    private int[] nodeXpos;
    private int[] nodeYpos;

    public DisplayGraphics(DirectedWeightedGraphAlgorithms graph, int[] nodeXpos, int[] nodeYpos) {
        this.graph = graph;
        this.nodeXpos = nodeXpos;
        this.nodeYpos = nodeYpos;
    }

    public void paint(Graphics g) {
        int i, j;
        i = j = 10;
        for (int z = 0; z < this.graph.getGraph().nodeSize(); ++z) {
            g.setColor(Color.RED);
            g.fillOval(nodeXpos[z] - 5, nodeYpos[z] - 5, i, j);
//            if (z < 10) {
//                g.drawString(String.valueOf(z), nodeXpos[z] + 3, nodeYpos[z] + 12);
//            } else {
//                g.drawString(String.valueOf(z), nodeXpos[z], nodeYpos[z] + 12);
//            }
        }
        int x1, y1;
        int x2, y2;
        Iterator<EdgeData> edges = this.graph.getGraph().edgeIter();
        while (edges.hasNext()) {
            EdgeData edge = edges.next();
            x1 = nodeXpos[edge.getSrc()];
            y1 = nodeYpos[edge.getSrc()];
            x2 = nodeXpos[edge.getDest()];
            y2 = nodeYpos[edge.getDest()];
            g.setColor(Color.BLACK);
            g.drawLine(x1, y1, x2, y2);
        }
    }
}
package gui;

import Main.*;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Menu extends JFrame implements ActionListener {

    private JPanel Screen = new JPanel(new GridLayout(8, 1, 0, 0));
    private JButton Json = new JButton("Load Json File");
    private JButton ShowGraph = new JButton("Show Graph");
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

        ShowGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "ShowGraph", null);
            }
        });
        ShortestPathDist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "ShortestPathDist", null);
            }
        });
        ShortestPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "ShortestPath", null);
            }
        });
        Center.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "Center", null);
            }
        });
        Tsp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "Tsp", null);
            }
        });
    }

    private void setColor() {
        Json.setForeground(Color.BLACK);
        ShowGraph.setForeground(Color.RED);
        ShortestPathDist.setForeground(Color.RED);
        ShortestPath.setForeground(Color.RED);
        Center.setForeground(Color.RED);
        Tsp.setForeground(Color.RED);
    }

    private void setSize() {
        Json.setSize(220, 30);
        ShowGraph.setSize(220, 30);
        ShortestPathDist.setSize(220, 30);
        ShortestPath.setSize(220, 30);
        Center.setSize(220, 30);
        Tsp.setSize(220, 30);
    }

    private void Addons() {
        Screen.add(Json);
        Screen.add(ShowGraph);
        Screen.add(ShortestPathDist);
        Screen.add(ShortestPath);
        Screen.add(Center);
        Screen.add(Tsp);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }


    public static void main(String[] args) {
        new Menu();
        //new Menu("/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json");
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
    private int Width = 1500;
    private int Height = 1080;
    private Toolkit toolkit;
    private LinkedList<NodeData> path;
    private String operation; // what to activate.

    public SecondFrame(DirectedWeightedGraphAlgorithms graph, String operation, LinkedList<NodeData> path) {
        this.graph = graph;
        this.operation = operation;
        this.path = path;
        this.nodeXpos = new int[this.graph.getGraph().nodeSize()];
        this.nodeYpos = new int[this.graph.getGraph().nodeSize()];
        this.update_x_y_pos();
        this.operations();

        this.setSize(this.Width, this.Height);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void operations() {
        if (this.operation.equals("ShowGraph")) {
            DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeXpos, this.nodeYpos, this.path);
            this.add(m);
        }
        if (this.operation.equals("ShortestPathDist")) {
            setTitle("ShortestPathDist");
            setSize(300, 200);
            toolkit = getToolkit();
            Dimension size = toolkit.getScreenSize();
            setLocation((size.width - getWidth()) / 2, (size.height - getHeight()) / 2);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            getContentPane().add(panel);
            panel.setLayout(null);
            JLabel Node1 = new JLabel("First Node");
            Node1.setBounds(30, 20, 80, 30);
            JLabel Node2 = new JLabel("Second Node");
            Node2.setBounds(30, 55, 110, 30);
            JLabel ans = new JLabel("Distance: ");
            ans.setBounds(30, 90, 80, 30);
            final JTextField txt1 = new JTextField(5);
            txt1.setBounds(145, 20, 50, 30);
            txt1.setText("0");
            final JTextField txt2 = new JTextField(5);
            txt2.setBounds(145, 55, 50, 30);
            txt2.setText("0");
            final JTextField txt3 = new JTextField(5);
            txt3.setBounds(145, 90, 200, 30);

            JButton comp = new JButton("Calculate");
            comp.setBounds(30, 125, 110, 30);
            comp.addActionListener(new ActionListener() {
                DirectedWeightedGraphAlgorithms tmp = graph;

                public void actionPerformed(ActionEvent event) {
                    int a1 = Integer.parseInt(txt1.getText());
                    int a2 = Integer.parseInt(txt2.getText());
                    double a3 = tmp.shortestPathDist(a1, a2);
                    txt3.setText(String.valueOf(a3));
                }
            });

            panel.add(Node1);
            panel.add(Node2);
            panel.add(ans);
            panel.add(txt1);
            panel.add(txt2);
            panel.add(txt3);
            panel.add(comp);
        }

        if (this.operation.equals("ShortestPath")) {
            setTitle("ShortestPath");
            setSize(300, 200);
            toolkit = getToolkit();
            Dimension size = toolkit.getScreenSize();
            setLocation((size.width - getWidth()) / 2, (size.height - getHeight()) / 2);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            getContentPane().add(panel);
            panel.setLayout(null);
            JLabel Node1 = new JLabel("First Node");
            Node1.setBounds(30, 20, 80, 30);
            JLabel Node2 = new JLabel("Second Node");
            Node2.setBounds(30, 55, 110, 30);
            final JTextField txt1 = new JTextField(5);
            txt1.setBounds(145, 20, 50, 30);
            txt1.setText("0");
            final JTextField txt2 = new JTextField(5);
            txt2.setBounds(145, 55, 50, 30);
            txt2.setText("0");
            JButton comp = new JButton("Calculate");
            comp.setBounds(30, 125, 110, 30);
            comp.addActionListener(new ActionListener() {
                private DirectedWeightedGraphAlgorithms tmpG = graph;

                public void actionPerformed(ActionEvent event) {
                    int a1 = Integer.parseInt(txt1.getText());
                    int a2 = Integer.parseInt(txt2.getText());
                    LinkedList<NodeData> path = (LinkedList<NodeData>) tmpG.shortestPath(a1, a2);
                    new SecondFrame(tmpG, "ShowGraph", path);
                }
            });

            panel.add(Node1);
            panel.add(Node2);
            panel.add(txt1);
            panel.add(txt2);
            panel.add(comp);
        }
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
    private LinkedList<NodeData> path;

    public DisplayGraphics(DirectedWeightedGraphAlgorithms graph, int[] nodeXpos, int[] nodeYpos, LinkedList<NodeData> path) {
        this.graph = graph;
        this.nodeXpos = nodeXpos;
        this.nodeYpos = nodeYpos;
        this.path = path;
    }

    public void paint(Graphics g) {
        int i, j;
        i = j = 20;
        for (int z = 0; z < this.graph.getGraph().nodeSize(); ++z) {
            g.setColor(Color.BLACK);
            g.fillOval(nodeXpos[z] - 8, nodeYpos[z] - 8, i, j);
            g.setColor(Color.MAGENTA);
            if (z < 10) {
                g.drawString(String.valueOf(z), nodeXpos[z], nodeYpos[z] + 8);
            } else {
                g.drawString(String.valueOf(z), nodeXpos[z] - 4, nodeYpos[z] + 8);
            }
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
        if (this.path!=null) {
            int size = this.path.size();
            for (int x = 0; x < size; ++x) {
                g.setColor(Color.RED);
                NodeData curr_node1 = this.path.pop();
                x1 = nodeXpos[curr_node1.getKey()];
                y1 = nodeYpos[curr_node1.getKey()];
                NodeData curr_node2 = this.path.peek();
                x2 = nodeXpos[curr_node2.getKey()];
                y2 = nodeYpos[curr_node2.getKey()];
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }
}
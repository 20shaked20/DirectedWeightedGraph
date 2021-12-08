package gui;

import Main.*;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class Menu extends JFrame {

    private JPanel Screen = new JPanel(new GridLayout(6, 1, 0, 0));
    private JButton Json = new JButton("Load Json File");
    private JButton ShowGraph = new JButton("Show Graph");
    private JButton ShortestPathDist = new JButton("Run ShortestPathDist");
    private JButton ShortestPath = new JButton("Run ShortestPath");
    private JButton Center = new JButton("Run Center");
    private JButton TSP = new JButton("Run TSP");

    private DirectedWeightedGraph graph = new DW_Graph();
    private DirectedWeightedGraphAlgorithms graph_algo = new DW_Graph_Algo();


    public Menu(String file_location) {
        this.graph_algo.init(graph);
        this.graph_algo.load(file_location);
        this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER)); // center the buttons.
        this.ActionListener();
        this.setColor();
        this.Addons();
        this.setSize();
        this.Screen.setSize(300, 300);
        this.Screen.setVisible(true);
        this.add(Screen);
        this.setSize(300, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                        graph_algo.init(graph);
                        graph_algo.load(filepath);
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
        TSP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "TSP", null);
            }
        });
    }

    private void setColor() {
        Json.setForeground(Color.BLACK);
        ShowGraph.setForeground(Color.RED);
        ShortestPathDist.setForeground(Color.RED);
        ShortestPath.setForeground(Color.RED);
        Center.setForeground(Color.RED);
        TSP.setForeground(Color.RED);
    }

    private void setSize() {
        Json.setSize(220, 30);
        ShowGraph.setSize(220, 30);
        ShortestPathDist.setSize(220, 30);
        ShortestPath.setSize(220, 30);
        Center.setSize(220, 30);
        TSP.setSize(220, 30);
    }

    private void Addons() {
        Screen.add(Json);
        Screen.add(ShowGraph);
        Screen.add(ShortestPathDist);
        Screen.add(ShortestPath);
        Screen.add(Center);
        Screen.add(TSP);
    }


    public static void main(String[] args) {
        String file_loc = "/Users/Shaked/IdeaProjects/DirectedWeightedGraph/Ex2/data/G1.json";
        new Menu(file_loc);
    }

}

// SecondFrame.java

/**
 * This class is responsible for the second frame layout, it will pop up the new graphs using the algorithms.
 */
class SecondFrame extends JFrame implements ActionListener {
    private DirectedWeightedGraphAlgorithms graph;
    private int[] nodeXpos;
    private int[] nodeYpos;
    private Dimension screenSize;
    private Toolkit toolkit;
    private LinkedList<NodeData> path;
    private String operation; // what to activate.

    private JMenuBar Menu_Bar = new JMenuBar();
    private JMenuItem Add_Node = new JMenuItem("Add Node"); // create
    private JMenuItem Remove_Node = new JMenuItem("Remove Node"); // working > Fix to make it
    private JMenuItem Save = new JMenuItem("Save Graph"); // create
    private JMenuItem Remove_Edge = new JMenuItem("Remove Edge"); // create
    private JMenuItem Add_Edge = new JMenuItem("Connect Edge"); // create

    public SecondFrame(DirectedWeightedGraphAlgorithms graph, String operation, LinkedList<NodeData> path) {
        this.graph = graph;
        this.operation = operation;
        this.path = path;
        this.nodeXpos = new int[this.graph.getGraph().nodeSize()];
        this.nodeYpos = new int[this.graph.getGraph().nodeSize()];
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.update_x_y_pos();
        this.operations();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void MenuBar() {
        this.Menu_Bar.add(this.Add_Node);
        this.Menu_Bar.add(this.Remove_Node);
        this.Menu_Bar.add(this.Remove_Edge);
        this.Menu_Bar.add(this.Add_Edge);
        this.Menu_Bar.add(this.Save);

    }

    private void operations() {
        if (this.operation.equals("ShowGraph")) {
            DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeXpos, this.nodeYpos, this.path);
            this.setSize(this.screenSize.width, this.screenSize.height);
            if (this.path == null) { // only add menu when showing graph.
                this.MenuBar();
                this.setJMenuBar(Menu_Bar);
                this.Remove_Node.addActionListener(this::actionPerformed);
                this.Remove_Edge.addActionListener(this::actionPerformed);
            }
            this.add(m);
        }

        if (this.operation.equals("ShortestPathDist")) {
            this.setTitle("ShortestPathDist");
            this.setSize(400, 400);
            toolkit = getToolkit();
            Dimension size = toolkit.getScreenSize();
            setLocation((size.width - getWidth()) / 2, (size.height - getHeight()) / 2);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
            txt3.setBounds(145, 90, 180, 30);

            JButton comp = new JButton("Calculate");
            comp.setBounds(30, 125, 110, 30);
            comp.addActionListener(new ActionListener() {
                DirectedWeightedGraphAlgorithms tmpG = graph;

                public void actionPerformed(ActionEvent event) {
                    int a1 = Integer.parseInt(txt1.getText());
                    int a2 = Integer.parseInt(txt2.getText());
                    double a3 = tmpG.shortestPathDist(a1, a2);
                    JLabel error = new JLabel("Error -> No Path is available between both nodes ");
                    if (a3 == 0 || a3 == -1.0) {
                        error.setBounds(30, 90, 400, 30);
                        panel.removeAll();
                        panel.add(error);
                        panel.updateUI();
                    } else {
                        txt3.setText(String.valueOf(a3));
                        panel.remove(error);
                        panel.add(txt3);
                        panel.add(ans);
                        panel.updateUI();
                    }

                }
            });

            panel.add(Node1);
            panel.add(Node2);
            panel.add(txt1);
            panel.add(txt2);
            panel.add(comp);
        }

        if (this.operation.equals("ShortestPath")) {
            this.setTitle("ShortestPath");
            this.setSize(400, 400);
            toolkit = getToolkit();
            Dimension size = toolkit.getScreenSize();
            setLocation((size.width - getWidth()) / 2, (size.height - getHeight()) / 2);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
                    if (path != null) {
                        new SecondFrame(tmpG, "ShowGraph", path);
                    } else {
                        JLabel error = new JLabel("Error -> No Path is available between both nodes ");
                        error.setBounds(30, 90, 400, 30);
                        panel.add(error);
                        panel.updateUI();
                    }
                }
            });

            panel.add(Node1);
            panel.add(Node2);
            panel.add(txt1);
            panel.add(txt2);
            panel.add(comp);
        }

        if (this.operation.equals("Center")) {
            this.setTitle("Center");
            this.setSize(250, 250);
            toolkit = getToolkit();
            Dimension size = toolkit.getScreenSize();
            setLocation((size.width - getWidth()) / 2, (size.height - getHeight()) / 2);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            getContentPane().add(panel);
            panel.setLayout(null);
            NodeData center = this.graph.center();
            JLabel Node1 = new JLabel("The Center Of The Graph IS: " + "\n" + "( " + center.getKey() + " )");
            Node1.setBounds(20, 75, 250, 50);
            panel.add(Node1);

        }

        if (this.operation.equals("TSP")) {
            this.setTitle("TSP");
            this.setSize(400, 400);
            toolkit = getToolkit();
            Dimension size = toolkit.getScreenSize();
            setLocation((size.width - getWidth()) / 2, (size.height - getHeight()) / 2);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            JPanel panel = new JPanel();
            getContentPane().add(panel);
            panel.setLayout(null);
            JLabel Node1 = new JLabel("Insert Nodes ID With ',' : ");
            Node1.setBounds(30, 20, 180, 30);
            final JTextField txt1 = new JTextField(5);
            txt1.setBounds(200, 20, 50, 30);
            txt1.setText("0");
            JButton comp = new JButton("Find Path");
            comp.setBounds(30, 110, 110, 30);
            comp.addActionListener(new ActionListener() {
                private DirectedWeightedGraphAlgorithms tmpG = graph;

                public void actionPerformed(ActionEvent event) {
                    String a1 = txt1.getText();
                    String[] items = a1.split(",");
                    int[] ID_S = new int[items.length];
                    for (int i = 0; i < items.length; ++i) {
                        ID_S[i] = Integer.parseInt(items[i]);
                    }
                    LinkedList<NodeData> cities = new LinkedList<>();
                    for (int i = 0; i < ID_S.length; ++i) {
                        cities.add(this.tmpG.getGraph().getNode(ID_S[i]));
                    }
                    this.tmpG.tsp(cities);
                    if (!cities.isEmpty()) {
                        new SecondFrame(tmpG, "ShowGraph", cities);
                    } else {
                        JLabel error = new JLabel("Error -> No Path is available between both nodes ");
                        error.setBounds(30, 90, 400, 30);
                        panel.add(error);
                        panel.updateUI();
                    }
                }
            });

            panel.add(Node1);
            panel.add(txt1);
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
        double uintX = this.screenSize.width / (maxX - minX) * 0.9;
        double unitY = this.screenSize.height / (maxY - minY) * 0.8;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == this.Remove_Node) {
                String id = JOptionPane.showInputDialog(null, "Insert Node id");
                int id_int = Integer.parseInt(id);
                this.graph.getGraph().removeNode(id_int);
                this.getContentPane().removeAll();
                DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeXpos, this.nodeYpos, this.path);
                this.add(m);
                this.repaint();
                this.revalidate();
            }
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Invalid, enter again");
        }

        if (e.getSource() == this.Remove_Edge) {
            try {
                String id = JOptionPane.showInputDialog(null, "Insert Edge src,dest ");
                String[] items = id.split(",");
                int src = Integer.parseInt(items[0]);
                int dest = Integer.parseInt(items[1]);
                this.graph.getGraph().removeEdge(src, dest);
                this.getContentPane().removeAll();
                DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeXpos, this.nodeYpos, this.path);
                this.add(m);
                this.repaint();
                this.revalidate();
            } catch (Exception E) {
                JOptionPane.showMessageDialog(null, "Invalid, enter again");
            }
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
            this.drawArrowLine(g, x1, y1, x2, y2, 25, 8);

        }
        if (this.path != null) {
            int size = this.path.size();
            LinkedList<NodeData> tmp = new LinkedList<>();
            for (int t = 0; t < size; ++t) {
                tmp.add(this.path.get(t));
            }
            for (int x = 0; x < size; ++x) {
                g.setColor(Color.RED);
                NodeData curr_node1 = tmp.pop();
                x1 = nodeXpos[curr_node1.getKey()];
                y1 = nodeYpos[curr_node1.getKey()];
                if (tmp.isEmpty()) {
                    break;
                }
                NodeData curr_node2 = tmp.peek();
                if (graph.getGraph().getEdge(curr_node1.getKey(), curr_node2.getKey()) != null) {
                    x2 = nodeXpos[curr_node2.getKey()];
                    y2 = nodeYpos[curr_node2.getKey()];
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }
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


    }

    private void drawArrowLine(Graphics g1, int x1, int y1, int x2, int y2, int d, int h) {
        Graphics2D g2 = (Graphics2D) g1;

        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;


        int[] x_points = {x2 + 2, (int) xm, (int) xn};
        int[] y_points = {y2 + 2, (int) ym, (int) yn};
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.BLACK);
        g2.drawLine(x1, y1, x2, y2);
        g2.setColor(Color.cyan);
        g2.fillPolygon(x_points, y_points, 3);
        g2.setColor(Color.black);
        g2.drawPolygon(x_points, y_points, 3);


    }
}
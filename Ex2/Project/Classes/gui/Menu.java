package gui;

import Main.*;
import api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Menu extends JFrame {

    private JPanel Screen = new JPanel(new GridLayout(6, 1, 0, 0));
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
        this.ShowGraph.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "ShowGraph", null);
            }
        });
        this.ShortestPathDist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "ShortestPathDist", null);
            }
        });
        this.ShortestPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "ShortestPath", null);
            }
        });
        this.Center.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "Center", null);
            }
        });
        this.TSP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph_algo, "TSP", null);
            }
        });
    }

    private void setColor() {
        ShowGraph.setForeground(Color.RED);
        ShortestPathDist.setForeground(Color.RED);
        ShortestPath.setForeground(Color.RED);
        Center.setForeground(Color.RED);
        TSP.setForeground(Color.RED);
    }

    private void setSize() {
        ShowGraph.setSize(220, 30);
        ShortestPathDist.setSize(220, 30);
        ShortestPath.setSize(220, 30);
        Center.setSize(220, 30);
        TSP.setSize(220, 30);
    }

    private void Addons() {
        Screen.add(ShowGraph);
        Screen.add(ShortestPathDist);
        Screen.add(ShortestPath);
        Screen.add(Center);
        Screen.add(TSP);
    }

}

// SecondFrame.java

/**
 * This class is responsible for the second frame layout, it will pop up the new graphs using the algorithms.
 */
class SecondFrame extends JFrame implements ActionListener {
    private DirectedWeightedGraphAlgorithms graph;
    private HashMap<Integer, Integer> nodeX_Scale; // key loc, x value
    private HashMap<Integer, Integer> nodeY_Scale; // key loc, y value
    private Dimension screenSize;
    private Toolkit toolkit;
    private LinkedList<NodeData> path;
    private String operation; // what to activate.

    private JMenuBar Menu_Bar = new JMenuBar();
    private JButton Add_Node = new JButton("Add Node"); // create
    private JButton Remove_Node = new JButton("Remove Node"); // working > Fix to make it
    private JButton Save = new JButton("Save Graph"); // create
    private JButton Load = new JButton("Load Graph");
    private JButton Remove_Edge = new JButton("Remove Edge"); // create
    private JButton Add_Edge = new JButton("Connect Edge"); // create

    public SecondFrame(DirectedWeightedGraphAlgorithms graph, String operation, LinkedList<NodeData> path) {
        this.graph = graph;
        this.operation = operation;
        this.path = path;
        this.nodeX_Scale = new HashMap<>();
        this.nodeY_Scale = new HashMap<>();
        this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.operations();
        this.update_x_y_pos();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    private void MenuBar() {
        this.Menu_Bar.add(this.Add_Node);
        this.Menu_Bar.add(this.Remove_Node);
        this.Menu_Bar.add(this.Remove_Edge);
        this.Menu_Bar.add(this.Add_Edge);
        this.Menu_Bar.add(this.Load);
        this.Menu_Bar.add(this.Save);

    }

    private void operations() {
        if (this.operation.equals("ShowGraph")) {
            DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeX_Scale, this.nodeY_Scale, this.path);
            this.setSize(this.screenSize.width, this.screenSize.height);
            if (this.path == null) { // only add menu when showing graph.
                this.MenuBar();
                this.setJMenuBar(Menu_Bar);
                this.Remove_Node.addActionListener(this::actionPerformed);
                this.Remove_Edge.addActionListener(this::actionPerformed);
                this.Add_Node.addActionListener(this::actionPerformed);
                this.Add_Edge.addActionListener(this::actionPerformed);
                this.Save.addActionListener(this::actionPerformed);
                this.Load.addActionListener(this::actionPerformed);


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

    //TODO: Update varibales
    private void update_x_y_pos() {
        NodeData curr_node;
        GeoLocation curr_geo;
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE, maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
        double x, y;
        Iterator nodes = this.graph.getGraph().nodeIter();
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

        double X_Scaling = this.screenSize.width / (maxX - minX) * 0.8;
        double Y_Scaling = this.screenSize.height / (maxY - minY) * 0.8;

        nodes = this.graph.getGraph().nodeIter();
        while (nodes.hasNext()) {
            curr_node = (NodeData) nodes.next();
            curr_geo = curr_node.getLocation();
            x = (curr_geo.x() - minX) * X_Scaling;
            y = (curr_geo.y() - minY) * Y_Scaling;

            this.nodeX_Scale.put(curr_node.getKey(), (int) x);
            this.nodeY_Scale.put(curr_node.getKey(), (int) y);

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
                this.nodeX_Scale.remove(id_int);
                this.nodeY_Scale.remove(id_int);
                this.update_x_y_pos();
                DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeX_Scale, this.nodeY_Scale, this.path);
                this.add(m);
                this.repaint();
                this.revalidate();
            }
        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, "Invalid, enter again", "", JOptionPane.ERROR_MESSAGE);
        }

        if (e.getSource() == this.Remove_Edge) {
            try {
                String id = JOptionPane.showInputDialog(null, "Insert Edge 'src,dest' ");
                String[] items = id.split(",");
                int src = Integer.parseInt(items[0]);
                int dest = Integer.parseInt(items[1]);
                this.graph.getGraph().removeEdge(src, dest);
                this.getContentPane().removeAll();
                DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeX_Scale, this.nodeY_Scale, this.path);
                this.add(m);
                this.repaint();
                this.revalidate();
            } catch (Exception E) {
                JOptionPane.showMessageDialog(null, "Invalid, enter again");
            }
        }
        if (e.getSource() == this.Add_Node) {
            try {
                String id = JOptionPane.showInputDialog(null, "Insert 'ID,x,y' ");
                String[] items = id.split(",");
                int ID = Integer.parseInt(items[0]);
                double x = Double.parseDouble(items[1]);
                double y = Double.parseDouble(items[2]);
                GeoLocation geo_loc = new Geo_Location(x, y, 0);
                NodeData new_node = new Node_data(ID, (Geo_Location) geo_loc);
                this.graph.getGraph().addNode(new_node);
                this.nodeX_Scale.put(ID, (int) x);
                this.nodeY_Scale.put(ID, (int) y);
                this.update_x_y_pos();
                this.getContentPane().removeAll();
                DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeX_Scale, this.nodeY_Scale, this.path);
                this.add(m);
                this.repaint();
                this.revalidate();
            } catch (Exception E) {
                JOptionPane.showMessageDialog(null, "Invalid, enter again");
            }
        }
        if (e.getSource() == this.Add_Edge) {
            try {
                String id = JOptionPane.showInputDialog(null, "Insert 'ID1,ID2,Weight' ");
                String[] items = id.split(",");
                int ID1 = Integer.parseInt(items[0]);
                int ID2 = Integer.parseInt(items[1]);
                double weight = Double.parseDouble(items[2]);
                this.graph.getGraph().connect(ID1, ID2, weight);
                this.update_x_y_pos();
                this.getContentPane().removeAll();
                DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeX_Scale, this.nodeY_Scale, this.path);
                this.add(m);
                this.repaint();
                this.revalidate();
            } catch (Exception E) {
                JOptionPane.showMessageDialog(null, "Invalid, enter again");
            }
        }
        if (e.getSource() == this.Save) {
            JFileChooser fc = new JFileChooser();
            try {
                fc.setCurrentDirectory(new File("./data"));
            } catch (Exception E) {
            }
            fc.setDialogTitle("Saving File");
            int selection = fc.showSaveDialog(null);
            if (selection == JFileChooser.APPROVE_OPTION) {
                String filepath = fc.getSelectedFile().getAbsolutePath();
                if (!this.graph.save(filepath)) {
                    JOptionPane.showMessageDialog(null, "Did not saved successfully try again");
                }
            }
        }
        if (e.getSource() == this.Load) {
            JFileChooser fc = new JFileChooser();
            try {
                fc.setCurrentDirectory(new File("./data"));
            } catch (Exception E) {
            }
            fc.setDialogTitle("Loading File");
            int selection = fc.showOpenDialog(null);
            if (selection == JFileChooser.APPROVE_OPTION) {
                String filepath = fc.getSelectedFile().getPath();
                this.graph = new DW_Graph_Algo();
                this.graph.load(filepath);
                this.getContentPane().removeAll();
                this.update_x_y_pos();
                DisplayGraphics m = new DisplayGraphics(this.graph, this.nodeX_Scale, this.nodeY_Scale, this.path);
                this.add(m);
                this.repaint();
                this.revalidate();
            }
        }
    }
}

class DisplayGraphics extends Canvas {
    private DirectedWeightedGraphAlgorithms graph;
    private HashMap<Integer, Integer> nodeXpos; // key loc, x value
    private HashMap<Integer, Integer> nodeYpos; // key loc, y value
    private LinkedList<NodeData> path;

    public DisplayGraphics(DirectedWeightedGraphAlgorithms graph, HashMap<Integer, Integer> nodeXpos, HashMap<Integer, Integer> nodeYpos, LinkedList<NodeData> path) {
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
            x1 = this.nodeXpos.get(edge.getSrc());
            y1 = this.nodeYpos.get(edge.getSrc());
            x2 = this.nodeXpos.get(edge.getDest());
            y2 = this.nodeYpos.get(edge.getDest());
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
                x1 = this.nodeXpos.get(curr_node1.getKey());
                y1 = this.nodeYpos.get(curr_node1.getKey());
                if (tmp.isEmpty()) {
                    break;
                }
                NodeData curr_node2 = tmp.peek();
                if (graph.getGraph().getEdge(curr_node1.getKey(), curr_node2.getKey()) != null) {
                    x2 = this.nodeXpos.get(curr_node2.getKey());
                    y2 = this.nodeYpos.get(curr_node2.getKey());
                    g.drawLine(x1, y1, x2, y2);
                }
            }
        }
        int i, j;
        i = j = 20;
        Iterator<NodeData> nodes = this.graph.getGraph().nodeIter();
        while (nodes.hasNext()) {
            NodeData curr_node = nodes.next();
            g.setColor(Color.BLACK);
            g.fillOval(this.nodeXpos.get(curr_node.getKey()) - 8, this.nodeYpos.get(curr_node.getKey()) - 8, i, j);
            g.setColor(Color.MAGENTA);
            if (curr_node.getKey() < 10) {
                g.drawString(String.valueOf(curr_node.getKey()), this.nodeXpos.get(curr_node.getKey()), this.nodeYpos.get(curr_node.getKey()) + 8);
            } else {
                g.drawString(String.valueOf(curr_node.getKey()), this.nodeXpos.get(curr_node.getKey()) - 4, this.nodeYpos.get(curr_node.getKey()) + 8);
            }
        }
    }

    /**
     * I took this method from stackoverflow.
     */
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
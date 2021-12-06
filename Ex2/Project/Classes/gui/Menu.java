package gui;

import api.DirectedWeightedGraphAlgorithms;
import api.NodeData;

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

    private DirectedWeightedGraphAlgorithms graph;

    public Menu() {
        this.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER)); // center the buttons.
        this.ActionListener();
        this.setColor();
        this.Addons();
        this.setSize();
        Screen.setSize(500, 500);
        Screen.setVisible(true);
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
                new SecondFrame(graph);
            }
        });
        ShortestPath.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph);
            }
        });
        Center.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph);
            }
        });
        Tsp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new SecondFrame(graph);
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
class SecondFrame {
    private JFrame Frame = new JFrame("Graph");
    private DirectedWeightedGraphAlgorithms graph;

    public SecondFrame(DirectedWeightedGraphAlgorithms g) {
        this.Frame.setSize(500, 500);
        this.Frame.setVisible(true);
        this.graph = g;
        DisplayGraphics m = new DisplayGraphics();
        Frame.add(m);
        this.Frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }
}

class DisplayGraphics extends Canvas {

    public void paint(Graphics g) {
        g.drawString("Hello", 40, 40);
        setBackground(Color.WHITE);
        g.fillRect(130, 30, 100, 80);
        g.drawOval(30, 130, 50, 60);
        setForeground(Color.RED);
        g.fillOval(130, 130, 50, 60);
        g.drawArc(30, 200, 40, 50, 90, 60);
        g.fillArc(30, 130, 40, 50, 180, 40);

    }
}
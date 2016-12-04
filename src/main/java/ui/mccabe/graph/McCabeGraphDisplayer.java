package ui.mccabe.graph;

/**
 * Created by Peter on 04.12.2016.
 */
import edu.uci.ics.jung.algorithms.layout.ISOMLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class McCabeGraphDisplayer {

    private McCabeGraph graph;
    private Graph<Integer, String> g;
    private JFrame frame;
    public McCabeGraphDisplayer(InputStream in) {
        graph = new McCabeGraph(in);
        g = graph.fillGraph();
    }

    public McCabeGraphDisplayer(String codeStr) {
        this(new ByteArrayInputStream( codeStr.getBytes( StandardCharsets.UTF_8 ) ));
    }

    public McCabeGraphDisplayer(McCabeGraph graph) {
        this.graph = graph;
        g = graph.fillGraph();
    }

    public JPanel buildGraphPanel(){
        System.out.println("The graph g = " + g.toString());

        Layout<Integer, String> layout = new ISOMLayout(g);System.out.println(layout);
        layout.setSize(new Dimension(600,600));

        VisualizationViewer<Integer,String> vv =
                new VisualizationViewer<Integer,String>(layout);System.out.println(vv);
        vv.setPreferredSize(new Dimension(600,600));
        vv.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.CNTR);
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());

        DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
        gm.setMode(ModalGraphMouse.Mode.TRANSFORMING);
        vv.setGraphMouse(gm);

        return vv;
    }

    public void displayGraphInJFrame(){
        frame = new JFrame("McCabe Graph View");
        System.out.println(frame);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(buildGraphPanel());
        frame.pack();
        frame.setVisible(true);
    }

    public McCabeGraph getGraph() {
        return graph;
    }

    public void setGraph(McCabeGraph graph) {
        this.graph = graph;
    }

    public Graph<Integer, String> getG() {
        return g;
    }

    public void setG(Graph<Integer, String> g) {
        this.g = g;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }
}

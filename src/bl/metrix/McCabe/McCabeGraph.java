package bl.metrix.McCabe;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class McCabeGraph {
    private Map<Node, Map<String, Integer>> nodes = new HashMap<>();
    private int pointer = 0;
    private List<Node> predicates = new LinkedList<>();
    private Graph<Integer, String> g = new SparseMultigraph<Integer, String>();

    public McCabeGraph(InputStream in) {

        ClassOrInterfaceDeclaration declaration = null;
        try {
            // parse the file
            try {
                declaration = (ClassOrInterfaceDeclaration) JavaParser.parseBodyDeclaration(JavaParser.parse(in).toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<BodyDeclaration> l2 = declaration.getMembers();

        for (BodyDeclaration member: l2){
            MethodDeclaration m = (MethodDeclaration) member;
            this.collectPredicates(m.getBody().getChildrenNodes());
        }
    }

    public McCabeGraph(String codeStr) {
        this(new ByteArrayInputStream( codeStr.getBytes( StandardCharsets.UTF_8 ) ));
    }

    /*public Graph<Integer, String> getGraph() {
        return g;
    }*/


    public void collectPredicates(List<Node> codeNodes) {
        for (Node node : codeNodes) {
            if (node.getClass().equals(IfStmt.class) ||
                    node.getClass().equals(WhileStmt.class))
                predicates.add(node);

            if(node.getChildrenNodes().size() > 0) {
                collectPredicates(node.getChildrenNodes());
            }

        }
    }

    public Graph<Integer, String> fillGraph(){
        for (Node node : predicates) {
            this.appendNewNode(node);
        }

        return this.g;
    }

    public void appendNewNode(Node node){

        if (node.getClass() == IfStmt.class) {
            if ( ((IfStmt)node).getElseStmt() == null )
                appendIfNode(node);
            else
                appendIfElseNode(node);

        }else if (node.getClass() == WhileStmt.class || node.getClass() == ForStmt.class){
            appendWhileNode(node);
        }

    }



    public void appendIfElseNode(Node node) {

        Map<String, Integer> parentVertex = null;

        Integer[] newVertexex = new Integer[4];
        for (int i = 0; i < 4; i++) {
            pointer++;
            newVertexex[i] = pointer;
            g.addVertex(newVertexex[i]);
        }

        Node parentPrediacateNode = node.getParentNode().getParentNode();
        parentVertex = nodes.get(parentPrediacateNode);

        if (parentPrediacateNode.getClass() == IfStmt.class && ((IfStmt) node).getElseStmt() != null) {

            Node thenNode = ((IfStmt) parentPrediacateNode).getThenStmt();
            Node elseNode = ((IfStmt) parentPrediacateNode).getElseStmt();

            Node parentNode = node.getParentNode();
            if (thenNode != null && thenNode.equals(parentNode)) {
                String edgeTitle1 = parentVertex.get("then") + ":" + newVertexex[0];
                String edgeTitle2 = newVertexex[3] + ":" + parentVertex.get("end");
                g.removeEdge(parentVertex.get("then")+ ":" + parentVertex.get("end"));
                g.addEdge(edgeTitle1, parentVertex.get("then"), newVertexex[0], EdgeType.DIRECTED);
                g.addEdge(edgeTitle2, newVertexex[3], parentVertex.get("end"), EdgeType.DIRECTED);
                parentVertex.put("then", newVertexex[3]);
            } else if (elseNode != null && elseNode.equals(parentNode)) {
                String edgeTitle1 = parentVertex.get("else") + ":" + newVertexex[0];
                String edgeTitle2 = newVertexex[3] + ":" + parentVertex.get("end");
                g.removeEdge(parentVertex.get("else")+ ":" + parentVertex.get("end"));
                g.addEdge(edgeTitle1, parentVertex.get("else"), newVertexex[0], EdgeType.DIRECTED);
                g.addEdge(edgeTitle2, newVertexex[3], parentVertex.get("end"), EdgeType.DIRECTED);
                parentVertex.put("else", newVertexex[3]);
            }


        } else if (parentPrediacateNode.getClass() == WhileStmt.class) {
            String edgeTitle1 = parentVertex.get("trueStmt") + ":" + newVertexex[0];
            String edgeTitle2 = newVertexex[3] + ":" + parentVertex.get("start");
            g.removeEdge(parentVertex.get("trueStmt")+ ":" + parentVertex.get("start"));
            g.addEdge(edgeTitle1, parentVertex.get("trueStmt"), newVertexex[0], EdgeType.DIRECTED);
            g.addEdge(edgeTitle2, newVertexex[3], parentVertex.get("start"), EdgeType.DIRECTED);
            parentVertex.put("trueStmt", newVertexex[3]);
            g.removeEdge(parentVertex.get("start") + ":" + parentVertex.get("start"));

        }
        else if (parentPrediacateNode.getClass() == MethodDeclaration.class || parentPrediacateNode.getClass() == null){

            if (parentVertex != null) {
                String edgeTitle1 = parentVertex.get("lastVertex") + ":" + newVertexex[0];
                g.addEdge(edgeTitle1, parentVertex.get("lastVertex"), newVertexex[0], EdgeType.DIRECTED);
            }

            Map<String, Integer> main = new HashMap<>();
            main.put("lastVertex", newVertexex[3]);
            nodes.put(parentPrediacateNode, main);

        }

        g.addEdge(newVertexex[0] + ":" + newVertexex[1], newVertexex[0], newVertexex[1], EdgeType.DIRECTED);
        g.addEdge(newVertexex[0] + ":" + newVertexex[2], newVertexex[0], newVertexex[2], EdgeType.DIRECTED);
        g.addEdge(newVertexex[1] + ":" + newVertexex[3], newVertexex[1], newVertexex[3], EdgeType.DIRECTED);
        g.addEdge(newVertexex[2] + ":" + newVertexex[3], newVertexex[2], newVertexex[3], EdgeType.DIRECTED);

        Map<String, Integer> newNode = new HashMap<>();
        newNode.put("then", newVertexex[1]);
        newNode.put("else", newVertexex[2]);
        newNode.put("start", newVertexex[0]);
        newNode.put("end", newVertexex[3]);
        nodes.put(node, newNode);

    }



    public void appendIfNode(Node node) {

        Map<String, Integer> parentVertex = null;
        Node thenNode;
        Node elseNode;


        Integer[] newVertexex = new Integer[3];

        for (int i = 0; i < 3; i++) {
            pointer++;
            newVertexex[i] = pointer;
            g.addVertex(newVertexex[i]);
        }

        Node parentPrediacateNode = node.getParentNode().getParentNode();
        parentVertex = nodes.get(parentPrediacateNode);
        if (parentPrediacateNode.getClass() == IfStmt.class) {

            thenNode = ((IfStmt) parentPrediacateNode).getThenStmt();
            elseNode = ((IfStmt) parentPrediacateNode).getElseStmt();

            Node p = node.getParentNode();
            if (thenNode != null && thenNode.equals(p)) {
                String edgeTitle1 = parentVertex.get("then") + ":" + newVertexex[0];
                String edgeTitle2 = newVertexex[2] + ":" + parentVertex.get("end");
                g.removeEdge(parentVertex.get("then")+ ":" + parentVertex.get("end"));
                g.addEdge(edgeTitle1, parentVertex.get("then"), newVertexex[0], EdgeType.DIRECTED);
                g.addEdge(edgeTitle2, newVertexex[2], parentVertex.get("end"), EdgeType.DIRECTED);
                parentVertex.put("then", newVertexex[2]);
            } else if (elseNode != null && elseNode.equals(p)) {
                String edgeTitle1 = parentVertex.get("else") + ":" + newVertexex[0];
                String edgeTitle2 = newVertexex[2] + ":" + parentVertex.get("end");
                g.removeEdge(parentVertex.get("else")+ ":" + parentVertex.get("end"));
                g.addEdge(edgeTitle1, parentVertex.get("else"), newVertexex[0], EdgeType.DIRECTED);
                g.addEdge(edgeTitle2, newVertexex[2], parentVertex.get("end"), EdgeType.DIRECTED);
                parentVertex.put("else", newVertexex[2]);
            }

        } else if (parentPrediacateNode.getClass() == WhileStmt.class) {
            String edgeTitle1 = parentVertex.get("trueStmt") + ":" + newVertexex[0];
            String edgeTitle2 = newVertexex[2] + ":" + parentVertex.get("start");
            g.removeEdge(parentVertex.get("trueStmt")+ ":" + parentVertex.get("start"));
            g.addEdge(edgeTitle1, parentVertex.get("trueStmt"), newVertexex[0], EdgeType.DIRECTED);
            g.addEdge(edgeTitle2, newVertexex[2], parentVertex.get("start"), EdgeType.DIRECTED);
            parentVertex.put("trueStmt", newVertexex[2]);
            g.removeEdge(parentVertex.get("start") + ":" + parentVertex.get("start"));

        }
        else if (parentPrediacateNode.getClass() == MethodDeclaration.class || parentPrediacateNode.getClass() == null){

            if (parentVertex != null) {
                String edgeTitle1 = parentVertex.get("lastVertex") + ":" + newVertexex[0];
                g.addEdge(edgeTitle1, parentVertex.get("lastVertex"), newVertexex[0], EdgeType.DIRECTED);
            }

            Map<String, Integer> main = new HashMap<>();
            main.put("lastVertex", newVertexex[2]);
            nodes.put(parentPrediacateNode, main);


        }

        g.addEdge(newVertexex[0] + ":" + newVertexex[1], newVertexex[0], newVertexex[1], EdgeType.DIRECTED);
        g.addEdge(newVertexex[0] + ":" + newVertexex[2], newVertexex[0], newVertexex[2], EdgeType.DIRECTED);
        g.addEdge(newVertexex[1] + ":" + newVertexex[2], newVertexex[1], newVertexex[2], EdgeType.DIRECTED);


        Map<String, Integer> newNode = new HashMap<>();
        newNode.put("then", newVertexex[1]);
        newNode.put("start", newVertexex[0]);
        newNode.put("end", newVertexex[2]);
        nodes.put(node, newNode);

    }

    public void appendWhileNode(Node node) {

        Map<String, Integer> parentVertex = null;
        Node thenNode;
        Node elseNode;


        Integer[] newVertexex = new Integer[2];
        for (int i = 0; i < 2; i++){
            pointer++;
            newVertexex[i] = pointer;
            g.addVertex(newVertexex[i]);
        }

        Node parentPrediacateNode = node.getParentNode().getParentNode();
        parentVertex = nodes.get(parentPrediacateNode);
        if (parentPrediacateNode.getClass() == IfStmt.class) {

            thenNode = ((IfStmt) parentPrediacateNode).getThenStmt();
            elseNode = ((IfStmt) parentPrediacateNode).getElseStmt();

            Node p = node.getParentNode();
            if (thenNode != null && thenNode.equals(p)) {
                String edgeTitle1 = parentVertex.get("then") + ":" + newVertexex[0];
                String edgeTitle2 = newVertexex[1] + ":" + parentVertex.get("end");
                g.removeEdge(parentVertex.get("then")+ ":" + parentVertex.get("end"));
                g.addEdge(edgeTitle1, parentVertex.get("then"), newVertexex[0], EdgeType.DIRECTED);
                g.addEdge(edgeTitle2, newVertexex[1], parentVertex.get("end"), EdgeType.DIRECTED);
                parentVertex.put("then", newVertexex[1]);
            } else if (elseNode != null && elseNode.equals(p)) {
                String edgeTitle1 = parentVertex.get("else") + ":" + newVertexex[0];
                String edgeTitle2 = newVertexex[1] + ":" + parentVertex.get("end");
                g.removeEdge(parentVertex.get("else")+ ":" + parentVertex.get("end"));
                g.addEdge(edgeTitle1, parentVertex.get("else"), newVertexex[0], EdgeType.DIRECTED);
                g.addEdge(edgeTitle2, newVertexex[1], parentVertex.get("end"), EdgeType.DIRECTED);
                parentVertex.put("else", newVertexex[1]);
            }

        } else if (parentPrediacateNode.getClass() == WhileStmt.class) {
            String edgeTitle1 = parentVertex.get("trueStmt") + ":" + newVertexex[0];
            String edgeTitle2 = newVertexex[1] + ":" + parentVertex.get("start");
            g.removeEdge(parentVertex.get("trueStmt")+ ":" + parentVertex.get("start"));
            g.addEdge(edgeTitle1, parentVertex.get("trueStmt"), newVertexex[0], EdgeType.DIRECTED);
            g.addEdge(edgeTitle2, newVertexex[1], parentVertex.get("start"), EdgeType.DIRECTED);
            parentVertex.put("trueStmt", newVertexex[1]);
            g.removeEdge(parentVertex.get("start") + ":" + parentVertex.get("start"));
        }
        else if (parentPrediacateNode.getClass() == MethodDeclaration.class || parentPrediacateNode.getClass() == null){

            if (parentVertex != null) {
                String edgeTitle1 = parentVertex.get("lastVertex") + ":" + newVertexex[0];
                g.addEdge(edgeTitle1, parentVertex.get("lastVertex"), newVertexex[0], EdgeType.DIRECTED);
            }

            Map<String, Integer> main = new HashMap<>();
            main.put("lastVertex", newVertexex[1]);
            nodes.put(parentPrediacateNode, main);

        }

        g.addEdge(newVertexex[0] + ":" + newVertexex[0], newVertexex[0], newVertexex[0], EdgeType.DIRECTED);
        g.addEdge(newVertexex[0] + ":" + newVertexex[1], newVertexex[0], newVertexex[1], EdgeType.DIRECTED);


        Map<String, Integer> newNode = new HashMap<>();
        newNode.put("trueStmt", newVertexex[0]);
        newNode.put("start", newVertexex[0]);
        newNode.put("end", newVertexex[1]);
        nodes.put(node, newNode);

    }

    public Map<Node, Map<String, Integer>> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Node, Map<String, Integer>> nodes) {
        this.nodes = nodes;
    }

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public List<Node> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<Node> predicates) {
        this.predicates = predicates;
    }

    public Graph<Integer, String> getG() {
        return g;
    }

    public void setG(Graph<Integer, String> g) {
        this.g = g;
    }

}

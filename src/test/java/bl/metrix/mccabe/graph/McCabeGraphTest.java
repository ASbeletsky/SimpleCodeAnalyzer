package bl.metrix.mccabe.graph;


import bl.metrix.McCabe.McCabeGraph;
import bl.metrix.McCabe.McCabeGraphDisplayer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class McCabeGraphTest {

    String text1 = "public class Program {\n" +
            "    public static void main(String[] args) {\n" +
            "        int a = 3;\n" +
            "        if (a == 1) {\n" +
            "            while (true) {\n" +
            "\n" +
            "                if (true) {\n" +
            "                }\n" +
            "\n" +
            "\n" +
            "            }\n" +
            "        } else {\n" +
            "            if (true) {\n" +
            "            }\n" +
            "        }\n" +
            "    }}";



    @Test
    public void testCollectPredicateNodes(){
        McCabeGraph graph = new McCabeGraph(text1);
        int actualPredicatesCount = graph.getPredicates().size();
        int expectedPredicatesCount = 4;
        assertEquals(expectedPredicatesCount, actualPredicatesCount);
    }

    @Test
    public void testAppendIfElseNode(){
        String text = "public class Program {\n" +
                "    public static void main(String[] args) {\n" +
                "        if (true){\n" +
                "            \n" +
                "        }\n" +
                "        else{\n" +
                "            \n" +
                "        }}}";
        McCabeGraph graph = new McCabeGraph(text);
        graph.fillGraph();
        int actualVertexCount = graph.getG().getVertexCount();
        int expectedVertexCount = 4;
        assertEquals(expectedVertexCount, actualVertexCount);


    }

    @Test
    public void testAppendIfNode(){
        String text = "public class Program {\n" +
                "    public static void main(String[] args) {\n" +
                "        if (true) {\n" +
                "\n" +
                "        }\n" +
                "    }}";
        McCabeGraph graph = new McCabeGraph(text);
        int actualVertexCount = graph.fillGraph().getVertexCount();
        int expectedVertexCount = 3;
        assertEquals(expectedVertexCount, actualVertexCount);
    }

    @Test
    public void testAppendWhileNode(){
        String text = "public class Program {\n" +
                "    public static void main(String[] args) {\n" +
                "        while (true) {\n" +
                "        }\n" +
                "    }}";
        McCabeGraph graph = new McCabeGraph(text);
        int actualVertexCount = graph.fillGraph().getVertexCount();
        int expectedVertexCount = 2;
        assertEquals(expectedVertexCount, actualVertexCount);
    }

    @Test
    public void testBuildGraphPanel(){

        McCabeGraphDisplayer graph = new McCabeGraphDisplayer(text1);
        assertNotNull(graph.buildGraphPanel());
    }

    @Test
    public void testDisplayGraphInFrame(){
        McCabeGraphDisplayer graph = new McCabeGraphDisplayer(text1);
        assertNotNull(graph.buildGraphPanel());
        graph.displayGraphInJFrame();
        assertNotNull(graph.getFrame());
    }

}

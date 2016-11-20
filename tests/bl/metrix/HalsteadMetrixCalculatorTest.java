package bl.metrix;

import bl.metrix.halstead.IHalsteadMetrixCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixClassCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixMethodCalculator;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticMethod;
import bl.model.impl.AnalyticClass;
import bl.model.impl.AnalyticMethod;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by asus-user on 09.10.2016.
 */
public class HalsteadMetrixCalculatorTest {
    @Test
    public void calculateForClass() throws Exception {
        String src = "final class OutClass extends DummyClass {\n" +
                "    @Override\n" +
                "    public void run() {\n" +
                "\n" +
                "    }\n" +
                "}";
        IAnalyticClass analyticClass = new AnalyticClass(src);
        IHalsteadMetrixCalculator<IAnalyticClass> calculator = new HalsteadMetrixClassCalculator(analyticClass);

        int programVocabulary = calculator.getProgramVocabulary();
        int programLength = calculator.getProgramLength();
        double programVolume = calculator.getProgramVolume();
    }

    @Test
    public void calculateForMethod() throws Exception {
        String src = "int method() {\n" +
                "        try {\n" +
                "            int x = (int) (1000. * x);\n" +
                "            if(x > 10 && x < 100) {\n" +
                "                if(x == 11) {\n" +
                "                    System.out.println(\"start\");\n" +
                "                } else if(x == 99) {\n" +
                "                    System.out.println(\"end\");\n" +
                "                } else if(x % 2 == 0){\n" +
                "                    outFor : for(int i=0; i<x; i++) {\n" +
                "                        for(int j=i; j >= 0; j--) {\n" +
                "                            if(x % i == x % j) {\n" +
                "                                System.out.println(\"catch!\");\n" +
                "                                break outFor;\n" +
                "                            } else if(i == j / 2) {\n" +
                "                                return x + i > j ? i : j;\n" +
                "                            }\n" +
                "                        }\n" +
                "                    }\n" +
                "                } else {\n" +
                "                    switch (x) {\n" +
                "                        case 15:\n" +
                "                        case 17:\n" +
                "                            return 17;\n" +
                "                        case 19:\n" +
                "                            return -19;\n" +
                "                        default: return x;\n" +
                "                    }\n" +
                "                }\n" +
                "            } else return -x;\n" +
                "        } finally {\n" +
                "            System.out.println(\"finally\");\n" +
                "        }\n" +
                "        return Integer.MIN_VALUE;\n" +
                "    }";
        IAnalyticMethod analyticMethod = new AnalyticMethod(src);
        IHalsteadMetrixCalculator<IAnalyticMethod> calculator = new HalsteadMetrixMethodCalculator(analyticMethod);

        int programVocabulary = calculator.getProgramVocabulary();
        int programLength = calculator.getProgramLength();
        double programVolume = calculator.getProgramVolume();
    }
}
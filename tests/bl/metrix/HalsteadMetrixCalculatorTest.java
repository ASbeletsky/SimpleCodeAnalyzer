package bl.metrix;

import bl.metrix.halstead.IHalsteadMetrixCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixClassCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixFileCalculator;
import bl.metrix.halstead.calculators.HalsteadMetrixMethodCalculator;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;
import bl.model.IAnalyticMethod;
import bl.model.impl.AnalyticClass;
import bl.model.impl.AnalyticFile;
import bl.model.impl.AnalyticMethod;
import com.github.javaparser.ParseException;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.io.IOException;

/**
 * Created by Anton on 09.10.2016.
 */
public class HalsteadMetrixCalculatorTest {
    @Test
    public void calculateForFile() throws IOException, ParseException {
        //Arrange
        File file = new File("./tests/DummyClass.java");
        IAnalyticFile analyticFile = new AnalyticFile(file);

        //Act
        IHalsteadMetrixCalculator<IAnalyticFile> calculator = new HalsteadMetrixFileCalculator(analyticFile);
        calculator.calculateMetrix();

        //Assert
        Assert.assertEquals(8, calculator.getNumberOfUniqueOperands());
        Assert.assertEquals(146, calculator.getNumberOfOperands());
        Assert.assertEquals(13, calculator.getNumberOfUniqueOperators());
        Assert.assertEquals(64, calculator.getNumberOfOperators());
        Assert.assertEquals(21, calculator.getProgramVocabulary());
        Assert.assertEquals(210, calculator.getProgramLength());
        Assert.assertEquals(922.38, calculator.getProgramVolume(), 0.5);
    }

    @Test
    public void calculateForClass() throws Exception {
        //Arrange
        String src = "final class OutClass extends DummyClass {\n" +
                "    int a = 0;\n" +
                "\n" +
                "    public void run() {\n" +
                "\n" +
                "    }\n" +
                "}";
        IAnalyticClass analyticClass = new AnalyticClass(src);

        //Act
        IHalsteadMetrixCalculator<IAnalyticClass> calculator = new HalsteadMetrixClassCalculator(analyticClass);
        calculator.calculateMetrix();

        //Assert
        Assert.assertEquals(5, calculator.getNumberOfUniqueOperands());
        Assert.assertEquals(5, calculator.getNumberOfOperands());
        Assert.assertEquals(2, calculator.getNumberOfUniqueOperators());
        Assert.assertEquals(2, calculator.getNumberOfOperators());
        Assert.assertEquals(7, calculator.getProgramVocabulary());
        Assert.assertEquals(7, calculator.getProgramLength());
        Assert.assertEquals(19.65, calculator.getProgramVolume(), 0.5);
    }

    @Test
    public void calculateForMethod() throws Exception {
        //Arrange
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

        //Act
        IHalsteadMetrixCalculator<IAnalyticMethod> calculator = new HalsteadMetrixMethodCalculator(analyticMethod);
        calculator.calculateMetrix();

        //Assert
        Assert.assertEquals(6, calculator.getNumberOfUniqueOperands());
        Assert.assertEquals(66, calculator.getNumberOfOperands());
        Assert.assertEquals(8, calculator.getNumberOfUniqueOperators());
        Assert.assertEquals(40, calculator.getNumberOfOperators());
        Assert.assertEquals(14, calculator.getProgramVocabulary());
        Assert.assertEquals(106, calculator.getProgramLength());
        Assert.assertEquals(403.57, calculator.getProgramVolume(), 0.5);
    }
}
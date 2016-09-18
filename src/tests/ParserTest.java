package tests;

import bl.Utils;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;
import bl.model.impl.Parser;
import bl.model.modifier.IAccessModifier;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Peter on 14.09.2016.
 */
public class ParserTest {
    private Path filePath;
    private String fileContent;

    @Before
    public void before() throws Exception {
        fileContent = Utils.pathToString(filePath = Paths.get("src/tests/DummyClass.java"));
    }


    @Test
    public void parse() throws Exception {
        IAnalyticFile analyticFile = Parser.parse(filePath.toFile());

                            //expected, actual
        Assert.assertEquals("file name", "DummyClass.java", analyticFile.getName());
        Assert.assertEquals("package name", "tests", analyticFile.getPackage());
        Assert.assertEquals("top-level classes count", 2, analyticFile.getClasses().size());


        IAnalyticClass outClass = analyticFile.getClasses().get(1);
        Assert.assertEquals("second toop-level class name", "OutClass", outClass.getName());
        Assert.assertEquals("superclass", "DummyClass", outClass.getSuperclass());
        Assert.assertEquals("inserted classes count" ,0, outClass.getInsertedClasses().size());
        Assert.assertEquals("interfaces count", 0, outClass.getInterfaces().size());
        Assert.assertEquals("is final", true, outClass.isFinal());
        Assert.assertEquals("is abstract", false, outClass.isAbstract());
        Assert.assertEquals("is static", false, outClass.isStatic());
        Assert.assertEquals("access modifier", IAccessModifier.Visibility.PACKAGE_LOCAL, outClass.getAccessModifier());

        IAnalyticClass dummyClass = analyticFile.getClasses().get(0);
        Assert.assertEquals("class name", "DummyClass", dummyClass.getName());
        Assert.assertEquals("superclass name", "java.lang.Object", dummyClass.getSuperclass());
        Assert.assertEquals("has interface", true, dummyClass.getInterfaces().contains("Runnable"));
        Assert.assertEquals("has interface", true, dummyClass.getInterfaces().contains("java.io.Serializable"));
        Assert.assertEquals("has interface", true, dummyClass.getInterfaces().contains("java.io.Externalizable"));
        Assert.assertEquals("interfaces count", 3, dummyClass.getInterfaces().size());
        Assert.assertEquals("is final", false, dummyClass.isFinal());
        Assert.assertEquals("is abstract", true, dummyClass.isAbstract());
        Assert.assertEquals("is static", false, dummyClass.isStatic());
        Assert.assertEquals("access modifier", IAccessModifier.Visibility.PUBLIC, dummyClass.getAccessModifier());
        Assert.assertEquals("inserted classes count", 1, dummyClass.getInsertedClasses().size());

        IAnalyticClass insertedClass = dummyClass.getInsertedClasses().get(0);
        Assert.assertEquals("class name", "InClass", insertedClass.getName());
        Assert.assertEquals("superclass name", "DummyClass", insertedClass.getSuperclass());
        Assert.assertEquals("has interface", true, insertedClass.getInterfaces().contains("Cloneable"));
        Assert.assertEquals("has interface", true, insertedClass.getInterfaces().contains("Runnable"));
        Assert.assertEquals("has interface", true, insertedClass.getInterfaces().contains("java.util.Collection"));
        Assert.assertEquals("interfaces count", 3, insertedClass.getInterfaces().size());
        Assert.assertEquals("is final", false, insertedClass.isFinal());
        Assert.assertEquals("is abstract", true, insertedClass.isAbstract());
        Assert.assertEquals("is static", true, insertedClass.isStatic());
        Assert.assertEquals("access modifier", IAccessModifier.Visibility.PRIVATE, insertedClass.getAccessModifier());
        Assert.assertEquals("inserted classes count", 0, insertedClass.getInsertedClasses().size());
    }
}
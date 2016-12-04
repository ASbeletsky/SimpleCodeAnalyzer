package bl.metrix.mccabe.service.impl;

import bl.metrix.mccabe.service.CompilerService;
import bl.metrix.mccabe.service.SyntaxTreeService;
import org.junit.Assert;
import org.junit.Test;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.*;
import java.util.Optional;

public class CyclomaticComplexityServiceTest {

    private CyclomaticComplexityService sut = new CyclomaticComplexityService();
    private SyntaxTreeService syntaxTreeService = new SyntaxTreeServiceImpl();
    private CompilerService compilerService = new CompilerServiceImpl();

    @Test
    public void cyclomaticComplexityCompiledSource() throws IOException {

        InputStream is = new BufferedInputStream(new FileInputStream("src/test/resources/DummyClass.class"));
        ClassNode node = syntaxTreeService.buildSyntaxTreeForClass(is);

        long complexity = sut.calculateClassComplexity(node);

        Assert.assertEquals(26, complexity);
    }

    @Test
    public void cyclomaticComplexityJavaSource() throws IOException {

        String path = "src/test/resources/DummyClass.java";
        File compiled = compilerService.compileJavaSource(new File(path));
        InputStream is = new BufferedInputStream(new FileInputStream(compiled));
        ClassNode node = syntaxTreeService.buildSyntaxTreeForClass(is);

        long complexity = sut.calculateClassComplexity(node);

        System.out.println(String.format("Complexity of %s is %s", path, complexity));
        Assert.assertEquals(26, complexity);
    }

    @Test
    public void cyclomaticComplexityJavaSource2() throws IOException {

        String path = "src/test/resources/DummyWrapper.java";
        File compiled = compilerService.compileJavaSource(new File(path));
        InputStream is = new BufferedInputStream(new FileInputStream(compiled));
        ClassNode node = syntaxTreeService.buildSyntaxTreeForClass(is);

        long complexity = sut.calculateClassComplexity(node);

        System.out.println(String.format("Complexity of %s is %s", path, complexity));
        Assert.assertEquals(5, complexity);
    }

    @Test
    public void cyclomaticComplexityJavaClassMethod() throws IOException {

        String path = "src/test/resources/DummyWrapper.java";
        File compiled = compilerService.compileJavaSource(new File(path));
        InputStream is = new BufferedInputStream(new FileInputStream(compiled));
        ClassNode classNode = syntaxTreeService.buildSyntaxTreeForClass(is);
        Optional<MethodNode> methodNode = syntaxTreeService.getMethodFromClass(classNode, "insertion_procedure");

        long complexity = methodNode.isPresent() ? sut.calculateMethodComplexity(methodNode.get()) : 0;

        System.out.println(String.format("Complexity of %s is %s", path, complexity));
        Assert.assertEquals(4, complexity);
    }

}

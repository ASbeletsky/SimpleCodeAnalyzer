package bl.metrix.mccabe.service;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public interface SyntaxTreeService {

    ClassNode buildSyntaxTreeForClass(InputStream inputStream) throws IOException;

    Optional<MethodNode> getMethodFromClass(ClassNode classNode, String methodName) throws  IOException;

}

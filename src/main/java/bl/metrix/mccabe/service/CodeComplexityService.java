package bl.metrix.mccabe.service;

import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

public interface CodeComplexityService {

    long calculateClassComplexity(ClassNode source);

    long calculateMethodComplexity(MethodNode source);
}

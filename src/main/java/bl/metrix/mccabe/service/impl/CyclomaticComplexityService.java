package bl.metrix.mccabe.service.impl;

import bl.metrix.mccabe.service.CodeComplexityService;
import org.objectweb.asm.tree.*;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CyclomaticComplexityService implements CodeComplexityService {

    @Override
    public long calculateClassComplexity(ClassNode node) {
        return node.methods.stream()
                .mapToLong(method -> calculateMethodComplexity((MethodNode) method))
                .sum();
    }

    @Override
    public long calculateMethodComplexity(MethodNode node) {
        return 1 + getJump(node)
                + getTryCatch(node)
                + getExceptions(node)
                + getTotalCaseCount(node);
    }

    private long getJump(MethodNode method) {
        return toStream(method.instructions.iterator())
                .filter(instruction -> {
                    AbstractInsnNode node = (AbstractInsnNode) instruction;
                    return ((node instanceof JumpInsnNode) && (node.getOpcode() != 167));
                }).count();
    }

    private int getTotalCaseCount(MethodNode method) {
        return toStream(method.instructions.iterator())
                .filter(instruction -> {
                    AbstractInsnNode node = (AbstractInsnNode) instruction;
                    return (node instanceof TableSwitchInsnNode);
                }).mapToInt(switchNode -> {
                    TableSwitchInsnNode switchIns = (TableSwitchInsnNode) switchNode;
                    return switchIns.labels.size();
                }).sum();
    }

    private int getTryCatch(MethodNode method) {
        return method.tryCatchBlocks.size();
    }

    private int getExceptions(MethodNode method) {
        return method.exceptions.size();
    }

    private <T> Stream<T> toStream(Iterator<T> iterator) {
        int characteristics = Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED;

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, characteristics), false);
    }
}

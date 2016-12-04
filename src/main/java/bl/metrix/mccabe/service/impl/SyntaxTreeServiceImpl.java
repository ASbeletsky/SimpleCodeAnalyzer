package bl.metrix.mccabe.service.impl;

import bl.metrix.mccabe.service.SyntaxTreeService;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.stream.Stream;

import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;

public class SyntaxTreeServiceImpl implements SyntaxTreeService {

    @Override
    public ClassNode buildSyntaxTreeForClass(InputStream inputStream) throws IOException {
        ClassReader reader = new ClassReader(inputStream);
        ClassNode node = new ClassNode();
        reader.accept(node, ClassReader.SKIP_DEBUG);

        return node;
    }

    @Override
    public Optional<MethodNode> getMethodFromClass(ClassNode classNode, String methodName) throws IOException {
        return toStream((Iterator<MethodNode>)classNode.methods.iterator())
                .filter(node -> node.name.equals(methodName))
                .findFirst();
    }

    //TODO: to separate file
    private <T> Stream<T> toStream(Iterator<T> iterator) {
        int characteristics = Spliterator.DISTINCT | Spliterator.SORTED | Spliterator.ORDERED;

        return stream(spliteratorUnknownSize(iterator, characteristics), false);
    }
}

package bl.metrix.halstead.calculators;

import bl.model.IAnalyticClass;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Anton on 09.10.2016.
 */
public class HalsteadMetrixClassCalculator extends HalsteadMetrixCalculatorBase<IAnalyticClass> {
    public HalsteadMetrixClassCalculator(IAnalyticClass analyticClass) {
        super(analyticClass);
    }

    @Override
    protected List<Node> getAnalyticAbstractTree() {
        Stream<Node> classMethodsAbstractTree = this.analyticUnit.getMethods().stream().map(method -> (Node)method.getAbstractSynaxTree());
        Stream<Node> classFieldsAbstractTree = this.analyticUnit.getFields().stream().map(field -> (Node)field.getAbstractSynaxTree());

        return Stream.concat(classMethodsAbstractTree, classFieldsAbstractTree).collect(Collectors.toList());
    }
}

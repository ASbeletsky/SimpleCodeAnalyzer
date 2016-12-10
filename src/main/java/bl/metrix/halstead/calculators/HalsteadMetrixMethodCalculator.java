package bl.metrix.halstead.calculators;

import bl.model.IAnalyticMethod;
import com.github.javaparser.ast.Node;

import java.util.List;

/**
 * Created by Anton on 09.10.2016.
 */
public class HalsteadMetrixMethodCalculator extends HalsteadMetrixCalculatorBase<IAnalyticMethod> {
    public HalsteadMetrixMethodCalculator(IAnalyticMethod method) {
        super(method);
    }

    @Override
    protected List<Node> getAnalyticAbstractTree() {
        return analyticUnit.getAbstractSynaxTree().getBody().getChildrenNodes();
    }
}

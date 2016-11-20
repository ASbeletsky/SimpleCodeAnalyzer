package bl.metrix.halstead.calculators;

import bl.model.IAnalyticMethod;

import com.github.javaparser.ast.NamedNode;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.Statement;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anton on 09.10.2016.
 */
public class HalsteadMetrixMethodCalculator extends HalsteadMetrixCalculatorBase<IAnalyticMethod> {
    public  HalsteadMetrixMethodCalculator(IAnalyticMethod method){
        super(method);
    }

    @Override
    protected List<Node> getAnalyticAbstractTree() {
        return analyticUnit.getAbstractSynaxTree().getBody().getChildrenNodes();
    }
}

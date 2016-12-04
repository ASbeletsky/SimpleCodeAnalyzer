package bl.metrix.halstead.calculators;

import bl.model.IAnalyticFile;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.Node;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Anton 09.10.2016.
 */
public class HalsteadMetrixFileCalculator extends HalsteadMetrixCalculatorBase<IAnalyticFile> {
    public HalsteadMetrixFileCalculator(IAnalyticFile analyticFile) throws ParseException {
        super(analyticFile);
    }

    @Override
    protected List<Node> getAnalyticAbstractTree() {
        List<Node> fileClassesAbstractTree = this.analyticUnit.getClasses().stream()
                .map(_class -> (Node) _class.getAbstractSynaxTree())
                .collect(Collectors.toList());

         return  fileClassesAbstractTree;
    }
}

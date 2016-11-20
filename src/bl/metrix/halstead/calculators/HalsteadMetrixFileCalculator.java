package bl.metrix.halstead.calculators;

import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;
import com.github.javaparser.ParseException;

/**
 * Created by Anton 09.10.2016.
 */
public class HalsteadMetrixFileCalculator extends HalsteadMetrixCalculatorBase<IAnalyticFile> {
    private IAnalyticFile analyticFile;
    public HalsteadMetrixFileCalculator(IAnalyticFile analyticFile) throws ParseException {
        super();
        this.analyticFile = analyticFile;
    }

    @Override
    public int calculateNumberOfUniqueOperands() {
        return 0;
    }

    @Override
    public int calculateNumberOfOperands() {
        return 0;
    }

    @Override
    public int calculateNumberOfUniqueOperators() {
        return 0;
    }

    @Override
    public int calculateNumberOfOperators() {
        return 0;
    }
}

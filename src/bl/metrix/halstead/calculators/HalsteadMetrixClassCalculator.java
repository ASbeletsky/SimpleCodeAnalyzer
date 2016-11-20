package bl.metrix.halstead.calculators;

import bl.metrix.halstead.IHalsteadMetrixCalculator;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticMethod;
import com.github.javaparser.ParseException;

/**
 * Created by Anton on 09.10.2016.
 */
public class HalsteadMetrixClassCalculator extends HalsteadMetrixCalculatorBase<IAnalyticClass> {
    private HalsteadMetrixMethodCalculator methodCalculator;
    private IAnalyticClass analyticUnit;
    public HalsteadMetrixClassCalculator(IAnalyticClass analyticClass) throws ParseException {
        super();
        this.analyticUnit = analyticClass;
    }

    @Override
    public int calculateNumberOfUniqueOperands(){
        int numberOfUniqueOperands = 0;
        for(IAnalyticMethod method : analyticUnit.getMethods()){
            IHalsteadMetrixCalculator<IAnalyticMethod> methodCalculator = new HalsteadMetrixMethodCalculator(method);
            numberOfUniqueOperands += methodCalculator.calculateNumberOfUniqueOperands();
        }

        return  numberOfUniqueOperands;
    }

    @Override
    public int calculateNumberOfOperands(){
        int numberOfOperands = 0;
        for(IAnalyticMethod method : analyticUnit.getMethods()){
            IHalsteadMetrixCalculator<IAnalyticMethod> methodCalculator = new HalsteadMetrixMethodCalculator(method);
            numberOfOperands += methodCalculator.calculateNumberOfOperands();
        }

        return  numberOfOperands;
    }

    @Override
    public int calculateNumberOfUniqueOperators(){
        int numberOfUniqueOperators = 0;
        for(IAnalyticMethod method : analyticUnit.getMethods()){
            IHalsteadMetrixCalculator<IAnalyticMethod> methodCalculator = new HalsteadMetrixMethodCalculator(method);
            numberOfUniqueOperators += methodCalculator.calculateNumberOfUniqueOperators();
        }

        return  numberOfUniqueOperators;
    }

    @Override
    public int calculateNumberOfOperators(){
        int numberOfOperators = 0;
        for(IAnalyticMethod method : analyticUnit.getMethods()){
            IHalsteadMetrixCalculator<IAnalyticMethod> methodCalculator = new HalsteadMetrixMethodCalculator(method);
            numberOfOperators += methodCalculator.calculateNumberOfOperators();
        }

        return  numberOfOperators;
    }
}

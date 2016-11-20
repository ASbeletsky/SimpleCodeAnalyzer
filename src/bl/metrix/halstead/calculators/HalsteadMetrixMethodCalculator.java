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
    private HashMap<String, ArrayList<Integer>> operandOnLineNumbers = new HashMap<String, ArrayList<Integer>>();
    private HashMap<String, ArrayList<Integer>> operatorOnLineNumbers = new HashMap<String, ArrayList<Integer>>();
    private IAnalyticMethod analyticUnit;

    public  HalsteadMetrixMethodCalculator(IAnalyticMethod method){
        super();
        this.analyticUnit = method;
        this.collectOperandsAndOperators(analyticUnit.getAbstractSynaxTree().getBody().getChildrenNodes());
    }

    private void collectOperandsAndOperators(List<Node> codeNodes) {
        for (Node node : codeNodes) {
            if (node.toString() != null && super.operandsExpressions.contains(node.getClass())) {
                this.addOperand(node.getClass().getName(), node.getBeginLine());
            }

            if(super.operatorsExpressions.contains(node.getClass())  ){
                  this.addOperator(node.getClass().getName(), node.getBeginLine());
            }

            if(node.getChildrenNodes().size() > 0) {
                collectOperandsAndOperators(node.getChildrenNodes());
            }
        }
    }

    private void addOperand(String operandName, int operandlineNumber){
        this.addToCollection(operandName, operandlineNumber, operandOnLineNumbers);
    }

    private void addOperator(String operatorName, int operatorlineNumber){
        this.addToCollection(operatorName, operatorlineNumber, operatorOnLineNumbers);
    }

    private void addToCollection(String name, int lineNumber, HashMap<String, ArrayList<Integer>> collection) {
        ArrayList<Integer> setOfLineNum;

        if (name.length() == 0)
            return;
        if (!collection.containsKey(name)) {
            setOfLineNum = new ArrayList<Integer>();
            setOfLineNum.add(lineNumber);
            collection.put(name, setOfLineNum);
        } else {
            setOfLineNum = collection.get(name);
            setOfLineNum.add(lineNumber);
            collection.put(name, setOfLineNum);
        }
    }

    private  int countItems(HashMap<String, ArrayList<Integer>> itemsMap){
        return itemsMap.values().stream().mapToInt(List::size).sum();
    }

    private  int countUniqueItems(HashMap<String, ArrayList<Integer>> itemsMap){
        return  itemsMap.size();
    }

    @Override
    public int calculateNumberOfUniqueOperands(){
        return this.countUniqueItems(this.operandOnLineNumbers);
    }

    @Override
    public int calculateNumberOfOperands(){
        return this.countItems(this.operandOnLineNumbers);
    }

    @Override
    public int calculateNumberOfUniqueOperators(){
        return this.countUniqueItems(this.operatorOnLineNumbers);
    }

    @Override
    public int calculateNumberOfOperators(){
        return this.countItems(this.operatorOnLineNumbers);
    }
}

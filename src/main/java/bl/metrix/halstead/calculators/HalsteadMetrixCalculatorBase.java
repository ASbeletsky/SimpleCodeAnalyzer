package bl.metrix.halstead.calculators;

import bl.metrix.halstead.IHalsteadMetrixCalculator;
import bl.model.IAnalytic;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeWithModifiers;
import com.github.javaparser.ast.body.ModifierSet;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.VoidType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Anton on 09.10.2016.
 */
public abstract class HalsteadMetrixCalculatorBase<TAnalytic extends IAnalytic>  implements IHalsteadMetrixCalculator<TAnalytic> {
    protected TAnalytic analyticUnit;

    protected List<Type> operandsExpressions = new ArrayList<Type>();
    protected List<Type> operatorsExpressions = new ArrayList<Type>();

    protected HashMap<String, ArrayList<Integer>> operandOnLineNumbers = new HashMap<String, ArrayList<Integer>>();
    protected HashMap<String, ArrayList<Integer>> operatorOnLineNumbers = new HashMap<String, ArrayList<Integer>>();

    protected int numberOfUniqueOperands;
    protected int numberOfOperands;
    protected int numberOfUniqueOperators;
    protected int numberOfOperators;

    protected HalsteadMetrixCalculatorBase(TAnalytic analyticUnit){
        this.analyticUnit = analyticUnit;

        operandsExpressions.add(NameExpr.class);
        operandsExpressions.add(VariableDeclaratorId.class);
        operandsExpressions.add(PrimitiveType.class);
        operandsExpressions.add(CharLiteralExpr.class);
        operandsExpressions.add(StringLiteralExpr.class);
        operandsExpressions.add(BooleanLiteralExpr.class);
        operandsExpressions.add(NullLiteralExpr.class);
        operandsExpressions.add(IntegerLiteralExpr.class);
        operandsExpressions.add(DoubleLiteralExpr.class);
        operandsExpressions.add(LongLiteralExpr.class);
        operandsExpressions.add(VoidType.class);

        operatorsExpressions.add(BinaryExpr.class);
        operatorsExpressions.add(AssignExpr.class);
        operatorsExpressions.add(ClassExpr.class);
        operatorsExpressions.add(ForStmt.class);
        operatorsExpressions.add(ForeachStmt.class);
        operatorsExpressions.add(IfStmt.class);
        operatorsExpressions.add(ReturnStmt.class);
        operatorsExpressions.add(SwitchStmt.class);
        operatorsExpressions.add(ForStmt.class);
        operatorsExpressions.add(TryStmt.class);
        operatorsExpressions.add(UnaryExpr.class);
        operatorsExpressions.add(SuperExpr.class);
        operatorsExpressions.add(ContinueStmt.class);
        operatorsExpressions.add(VariableDeclarator.class);
    }

    protected abstract List<Node> getAnalyticAbstractTree();

    protected void collectOperandsAndOperators(List<Node> codeNodes) {
        for (Node node : codeNodes) {
            if (node.getRange().begin.line > 0){
                if(this.operandsExpressions.contains(node.getClass())) {
                    this.addOperand(node.getClass().getName(), node.getBeginLine());
                }

                if(node instanceof NodeWithModifiers) {
                    int modifiers = ((NodeWithModifiers) node).getModifiers();
                    if(modifiers > 0) {
                        this.addOperator(ModifierSet.getAccessSpecifier(modifiers).toString(), node.getBeginLine());
                    }
                }

                if(this.operatorsExpressions.contains(node.getClass())  ){
                    this.addOperator(node.getClass().getName(), node.getBeginLine());
                }
            }

            if(node.getChildrenNodes().size() > 0) {
                collectOperandsAndOperators(node.getChildrenNodes());
            }
        }
    }

    protected void addOperand(String operandName, int operandlineNumber){
        this.addToCollection(operandName, operandlineNumber, operandOnLineNumbers);
    }

    protected void addOperator(String operatorName, int operatorlineNumber){
        this.addToCollection(operatorName, operatorlineNumber, operatorOnLineNumbers);
    }

    protected void addToCollection(String name, int lineNumber, HashMap<String, ArrayList<Integer>> collection) {
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

    protected  int countItems(HashMap<String, ArrayList<Integer>> itemsMap){
        return itemsMap.values().stream().mapToInt(List::size).sum();
    }

    protected  int countUniqueItems(HashMap<String, ArrayList<Integer>> itemsMap){
        return  itemsMap.size();
    }

    public int getNumberOfUniqueOperands(){
        return this.countUniqueItems(this.operandOnLineNumbers);
    }

    public int getNumberOfOperands(){
        return this.countItems(this.operandOnLineNumbers);
    }

    public int getNumberOfUniqueOperators(){
        return this.countUniqueItems(this.operatorOnLineNumbers);
    }

    public int getNumberOfOperators(){
        return this.countItems(this.operatorOnLineNumbers);
    }

    public int getProgramVocabulary(){
        this.numberOfUniqueOperands = this.getNumberOfUniqueOperands();
        this.numberOfUniqueOperators = this.getNumberOfUniqueOperators();

        return this.numberOfUniqueOperands + this.numberOfUniqueOperators;
    }

    public int getProgramLength(){
        this.numberOfOperands = this.getNumberOfOperands();
        this.numberOfOperators = this.getNumberOfOperators();

        return this.numberOfOperands + this.numberOfOperators;
    }

    public double getProgramVolume(){
        return this.getProgramLength() * (Math.log(this.getProgramVocabulary()) / Math.log(2));
    }

    public double getErrorsCount() {
        return this.getProgramVolume()/3000;
    }

    public void calculateMetrix(){
        this.collectOperandsAndOperators(this.getAnalyticAbstractTree());
    }
}

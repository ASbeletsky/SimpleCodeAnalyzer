package bl.metrix.halstead.calculators;

import bl.metrix.halstead.IHalsteadMetrixCalculator;
import bl.model.IAnalytic;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.NamedNode;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.body.VariableDeclaratorId;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.PrimitiveType;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton on 09.10.2016.
 */
public abstract class HalsteadMetrixCalculatorBase<TAnalytic extends IAnalytic>  implements IHalsteadMetrixCalculator<TAnalytic> {
    protected List<Type> operandsExpressions = new ArrayList<Type>();
    protected List<Type> operatorsExpressions = new ArrayList<Type>();

    protected int numberOfUniqueOperands;
    protected int numberOfOperands;
    protected int numberOfUniqueOperators;
    protected int numberOfOperators;

    protected HalsteadMetrixCalculatorBase(){
        operandsExpressions.add(NamedNode.class);
        operandsExpressions.add(VariableDeclaratorId.class);
        operandsExpressions.add(PrimitiveType.class);
        operandsExpressions.add(CharLiteralExpr.class);
        operandsExpressions.add(StringLiteralExpr.class);
        operandsExpressions.add(BooleanLiteralExpr.class);
        operandsExpressions.add(NullLiteralExpr.class);
        operandsExpressions.add(IntegerLiteralExpr.class);
        operandsExpressions.add(DoubleLiteralExpr.class);
        operandsExpressions.add(LongLiteralExpr.class);

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

    public abstract int calculateNumberOfUniqueOperands();
    public abstract int calculateNumberOfOperands();
    public abstract int calculateNumberOfUniqueOperators();
    public abstract int calculateNumberOfOperators();

    public int getProgramVocabulary(){
        if(this.numberOfUniqueOperands == 0){
            this.numberOfUniqueOperands = this.calculateNumberOfUniqueOperands();
        }
        if(this.numberOfUniqueOperators == 0){
            this.numberOfUniqueOperators = this.calculateNumberOfUniqueOperators();
        }

        return this.numberOfUniqueOperands + this.numberOfUniqueOperators;
    }

    public int getProgramLength(){
        if(this.numberOfOperands == 0){
            this.numberOfOperands = this.calculateNumberOfOperands();
        }
        if(this.numberOfOperators == 0){
            this.numberOfOperators = this.calculateNumberOfOperators();
        }

        return this.numberOfOperands + this.numberOfOperators;
    }

    public double getProgramVolume(){
        return this.getProgramLength() * (Math.log(this.getProgramVocabulary()) / Math.log(2));
    }
}

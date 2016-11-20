package bl.model.impl;

import bl.model.IAnalytic;
import bl.model.IAnalyticProperty;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.NameExpr;

/**
 * Created by Peter on 14.09.2016.
 */
public class AnalyticProperty extends AnalyticBase<Parameter>  implements IAnalyticProperty{

    public AnalyticProperty(Parameter parameter){
        super(parameter);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public AccessSpecifier getAccessModifier() {
        return null;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

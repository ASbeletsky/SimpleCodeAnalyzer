package bl.model.impl;

import bl.model.IAnalytic;
import bl.model.IAnalyticMethod;
import bl.model.IAnalyticProperty;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public class AnalyticMethod extends AnalyticBase<MethodDeclaration> implements IAnalyticMethod {
    private final List<IAnalyticProperty> parameters = new ArrayList<>();

    public AnalyticMethod(String source) throws ParseException {
        this((MethodDeclaration) JavaParser.parseClassBodyDeclaration(source));
    }

    private void fillMethodParams(){
       for (Parameter parameter : this.codeBlock.getParameters()){
           parameters.add(new AnalyticProperty(parameter));
       }
    }

    public AnalyticMethod(MethodDeclaration declaration){
        super(declaration);
    }

    @Override
    public String getName() {
        return this.codeBlock.getName();
    }

    @Override
    public String getResultType() {
        return this.codeBlock.getType().toString();
    }

    @Override
    public List<IAnalyticProperty> getParameters() {
        return parameters;
    }

     @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

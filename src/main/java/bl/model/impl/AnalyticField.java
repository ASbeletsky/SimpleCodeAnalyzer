package bl.model.impl;

import bl.model.IAnalyticField;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.FieldDeclaration;

/**
 * Created by asus-user on 20.11.2016.
 */
public class AnalyticField extends AnalyticBase<FieldDeclaration> implements IAnalyticField {

    public AnalyticField(FieldDeclaration field){
        super(field);
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public AccessSpecifier getAccessModifier() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}

package bl.model;

import bl.model.modifier.IAccessModifier;
import bl.model.modifier.IFinalModifier;
import bl.model.modifier.IStaticModifier;
import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.NameExpr;
import com.sun.istack.internal.Nullable;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticField extends IAnalytic<FieldDeclaration> {
    @Nullable
    String getValue();

    AccessSpecifier getAccessModifier();
}

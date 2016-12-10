package bl.model;

import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.FieldDeclaration;

import javax.annotation.Nullable;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticField extends IAnalytic<FieldDeclaration> {
    @Nullable
    String getValue();

    AccessSpecifier getAccessModifier();
}

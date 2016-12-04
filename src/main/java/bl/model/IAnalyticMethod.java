package bl.model;

import bl.model.modifier.*;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.sun.deploy.panel.IProperty;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticMethod extends IAnalytic<MethodDeclaration> {
    @NotNull
    String getResultType();

    @NotNull
    List<IAnalyticParameter> getParameters();
}

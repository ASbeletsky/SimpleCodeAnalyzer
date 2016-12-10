package bl.model;

import com.github.javaparser.ast.body.MethodDeclaration;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticMethod extends IAnalytic<MethodDeclaration> {
    @Nonnull
    String getResultType();

    @Nonnull
    List<IAnalyticParameter> getParameters();
}

package bl.model;

import com.github.javaparser.ast.AccessSpecifier;
import com.github.javaparser.ast.body.Parameter;

import javax.annotation.Nullable;

/**
 * Created by Anton on 20.11.2016.
 */
public interface IAnalyticParameter extends IAnalytic<Parameter> {
    @Nullable
    String getValue();

    AccessSpecifier getAccessModifier();
}

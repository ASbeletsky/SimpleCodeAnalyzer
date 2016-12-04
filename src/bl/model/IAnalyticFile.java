package bl.model;

import com.github.javaparser.ast.CompilationUnit;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticFile extends IAnalytic<CompilationUnit> {
    @NotNull
    List<IAnalyticClass> getClasses();
}

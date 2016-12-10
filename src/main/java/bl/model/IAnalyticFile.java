package bl.model;

import com.github.javaparser.ast.CompilationUnit;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticFile extends IAnalytic<CompilationUnit> {
    @Nonnull
    List<IAnalyticClass> getClasses();
}

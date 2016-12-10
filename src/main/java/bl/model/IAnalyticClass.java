package bl.model;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticClass extends IAnalytic<ClassOrInterfaceDeclaration> {
    @Nonnull
    List<IAnalyticField> getFields();

    @Nonnull
    List<IAnalyticMethod> getMethods();

    @Nonnull
    List<IAnalyticClass> getInnerClasses();

    @Nonnull
    IAnalyticClass getSuperclass();

    @Nonnull
    List<IAnalyticClass> getInterfaces();

    @Nonnull
    List<IAnalyticClass> getDependencies();

    //TODO: annotations
}

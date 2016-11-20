package bl.model;

import bl.model.modifier.IAbstractModifier;
import bl.model.modifier.IAccessModifier;
import bl.model.modifier.IFinalModifier;
import bl.model.modifier.IStaticModifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticClass extends IAnalytic<ClassOrInterfaceDeclaration> {
    @NotNull
    List<IAnalyticField> getFields();

    @NotNull
    List<IAnalyticMethod> getMethods();

    @NotNull
    List<IAnalyticClass> getInnerClasses();

    @NotNull
    IAnalyticClass getSuperclass();

    @NotNull
    List<IAnalyticClass> getInterfaces();

    @NotNull
    List<IAnalyticClass> getDependencies();

    //TODO: annotations
}

package bl.model;

import bl.model.modifier.*;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticClass extends IAnalytic, IAccessModifier, IAbstractModifier, IFinalModifier, IStaticModifier, IGenericModifier {
    @NotNull
    List<IAnalyticProperty> getProperties();

    @NotNull
    List<IAnalyticMethod> getMethods();

    @NotNull
    List<IAnalyticClass> getInsertedClasses();

    @NotNull
    String getSuperclass();

    @NotNull
    List<String> getInterfaces();

    @NotNull
    List<String> getDependencies();

    //TODO: annotations
}

package bl.model;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticClass extends IAnalytic {
    Visibility getVisibility();

    boolean isFinal();

    boolean isStatic();

    boolean isAbstract();

    @NotNull
    List<IAnalyticProperty> getProperties();

    @NotNull
    List<IAnalyticMethod> getMethods();

    @NotNull
    List<IAnalyticClass> getInsertedClasses();

    @NotNull
    IAnalyticClass getSuperclass();

    @NotNull
    List<IAnalyticClass> getInterfaces();

    //TODO: annotations
}

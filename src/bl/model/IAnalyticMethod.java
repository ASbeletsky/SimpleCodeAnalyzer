package bl.model;

import com.sun.deploy.panel.IProperty;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticMethod extends IAnalytic {
    Visibility getVisibility();

    boolean isStatic();

    boolean isFinal();

    @NotNull
    String getResultType();

    @NotNull
    List<IProperty> getParameters();
}

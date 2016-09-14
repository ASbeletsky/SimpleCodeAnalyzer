package bl.model;

import com.sun.istack.internal.Nullable;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticProperty extends IAnalytic {
    Visibility getVisibility();

    boolean isFinal();

    boolean isStatic();

    @Nullable
    String getValue();
}

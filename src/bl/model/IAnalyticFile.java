package bl.model;

import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticFile extends IAnalytic {
    @NotNull
    List<IAnalyticClass> getClasses();

    @NotNull
    String getPackage();
}

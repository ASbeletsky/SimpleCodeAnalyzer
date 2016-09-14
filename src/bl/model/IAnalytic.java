package bl.model;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalytic extends Comparable<IAnalytic> {
    @NotNull
    String getName();
    @Nullable
    String getSrcCode();
}

package bl.model;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalytic<TCodeBlock> extends Comparable<IAnalytic<TCodeBlock>> {
    @NotNull
    String getName();
    @Nullable
    String getSourceCode();
    @NotNull
    TCodeBlock getAbstractSynaxTree();
}

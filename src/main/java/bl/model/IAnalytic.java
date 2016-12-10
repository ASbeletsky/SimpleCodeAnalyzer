package bl.model;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalytic<TCodeBlock> extends Comparable<IAnalytic<TCodeBlock>> {
    @Nonnull
    String getName();

    @Nullable
    String getSourceCode();

    @Nonnull
    TCodeBlock getAbstractSynaxTree();
}

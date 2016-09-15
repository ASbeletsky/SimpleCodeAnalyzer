package bl.model;

import bl.model.modifier.IAccessModifier;
import bl.model.modifier.IFinalModifier;
import bl.model.modifier.IStaticModifier;
import com.sun.istack.internal.Nullable;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticProperty extends IAnalytic, IAccessModifier, IFinalModifier, IStaticModifier {
    @Nullable
    String getValue();
}

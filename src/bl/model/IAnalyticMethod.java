package bl.model;

import bl.model.modifier.IAbstractModifier;
import bl.model.modifier.IAccessModifier;
import bl.model.modifier.IFinalModifier;
import bl.model.modifier.IStaticModifier;
import com.sun.deploy.panel.IProperty;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticMethod extends IAnalytic, IAccessModifier, IAbstractModifier, IFinalModifier, IStaticModifier {
    @NotNull
    String getResultType();

    @NotNull
    List<IProperty> getParameters();
}

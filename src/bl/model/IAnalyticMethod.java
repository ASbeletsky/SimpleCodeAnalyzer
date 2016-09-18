package bl.model;

import bl.model.modifier.*;
import com.sun.deploy.panel.IProperty;
import com.sun.istack.internal.NotNull;

import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public interface IAnalyticMethod extends
        IAnalytic,
        IAccessModifier,
        IAbstractModifier,
        IFinalModifier,
        IStaticModifier,
        ISynchronizedModifier,
        IGenericModifier {

    @NotNull
    String getResultType();

    @NotNull
    List<IProperty> getParameters();
}

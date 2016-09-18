package bl.model.impl;

import bl.model.IAnalytic;
import bl.model.IAnalyticMethod;
import com.sun.deploy.panel.IProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
class AnalyticMethod implements IAnalyticMethod {
    private final String src;
    private final List<IProperty> parameters = new ArrayList<>();

    public AnalyticMethod(String src) {
        this.src = src;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getSrcCode() {
        return null;
    }

    @Override
    public Visibility getAccessModifier() {
        return null;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean isAbstract() {
        return false;
    }

    @Override
    public boolean isSynchronized() {
        return false;
    }

    @Override
    public String getResultType() {
        return null;
    }

    @Override
    public List<IProperty> getParameters() {
        return parameters;
    }

    @Override
    public String getGenericSrc() {
        return null;
    }

    @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

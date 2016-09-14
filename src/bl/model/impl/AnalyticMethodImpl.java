package bl.model.impl;

import bl.model.IAnalytic;
import bl.model.IAnalyticMethod;
import bl.model.Visibility;
import com.sun.deploy.panel.IProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
class AnalyticMethodImpl implements IAnalyticMethod {
    private final String src;
    private final List<IProperty> parameters = new ArrayList<>();

    public AnalyticMethodImpl(String src) {
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
    public Visibility getVisibility() {
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
    public String getResultType() {
        return null;
    }

    @Override
    public List<IProperty> getParameters() {
        return parameters;
    }

    @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

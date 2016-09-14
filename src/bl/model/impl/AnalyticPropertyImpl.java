package bl.model.impl;

import bl.model.IAnalytic;
import bl.model.IAnalyticProperty;
import bl.model.Visibility;

/**
 * Created by Peter on 14.09.2016.
 */
class AnalyticPropertyImpl implements IAnalyticProperty {
    private final String src;

    public AnalyticPropertyImpl(String src) {
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
    public boolean isFinal() {
        return false;
    }

    @Override
    public boolean isStatic() {
        return false;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

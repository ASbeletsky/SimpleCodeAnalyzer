package bl.model.impl;

import bl.model.IAnalytic;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
class AnalyticFileImpl implements IAnalyticFile {
    private final String src;
    private final List<IAnalyticClass> classes = new ArrayList<>();

    public AnalyticFileImpl(String src) {
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
    public List<IAnalyticClass> getClasses() {
        return classes;
    }

    @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

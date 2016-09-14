package bl.model.impl;

import bl.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
class AnalyticClassImpl implements IAnalyticClass {
    private final String src;
    private final List<IAnalyticProperty> properties = new ArrayList<>();
    private final List<IAnalyticMethod> methods = new ArrayList<>();
    private final List<IAnalyticClass> insertedClasses = new ArrayList<>();
    private final List<IAnalyticClass> interfaces = new ArrayList<>();
    private IAnalyticClass superclass;


    public AnalyticClassImpl(String src) {
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
    public boolean isAbstract() {
        return false;
    }

    @Override
    public List<IAnalyticProperty> getProperties() {
        return properties;
    }

    @Override
    public List<IAnalyticMethod> getMethods() {
        return methods;
    }

    @Override
    public List<IAnalyticClass> getInsertedClasses() {
        return insertedClasses;
    }

    @Override
    public IAnalyticClass getSuperclass() {
        return superclass;
    }

    @Override
    public List<IAnalyticClass> getInterfaces() {
        return interfaces;
    }

    @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

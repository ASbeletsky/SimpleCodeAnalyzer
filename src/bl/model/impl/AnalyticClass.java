package bl.model.impl;

import bl.model.*;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Peter on 14.09.2016.
 */
class AnalyticClass implements IAnalyticClass {
    static final Pattern SIGNATURE_PATTERN = Pattern.compile("([^;}]*)[\\s]+class[\\s]+([^<\\s]+)[\\s]*([^{]*)[\\s]*\\{");
    private static final int PART_FIRST_MODIFIERS = 1;
    private static final int PART_SECOND_CLASS_NAME = 2;
    private static final int PART_THIRD = 3;

    private final String src;
    private final String name;
    private final Visibility accessModifier;
    private final boolean isFinal, isStatic, isAbstract;
    private final String genericSrc;
    private String superclass;
    private final List<IAnalyticProperty> properties = new ArrayList<>();
    private final List<IAnalyticMethod> methods = new ArrayList<>();
    private final List<IAnalyticClass> insertedClasses = new ArrayList<>();
    private final List<String> interfaces = new ArrayList<>();
    private final List<String> dependencies = new ArrayList<>();


    public AnalyticClass(String src) {
        this.src = src;
        this.name = parseName(src);
        this.accessModifier = parseVisibility(src);
        this.isFinal = parseFinalModifier(src);
        this.isStatic = parseStaticModifier(src);
        this.isAbstract = parseAbstractModifier(src);
        this.superclass = parseSuperClass(src);
        this.properties.addAll(parseProperties(src));
        this.methods.addAll(parseMethods(src));
        this.insertedClasses.addAll(parseInsertedClasses(src));
        this.interfaces.addAll(parseInterfaces(src));
        this.dependencies.addAll(parseDependencies(src));
        this.genericSrc = parseGeneric(src);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSrcCode() {
        return src;
    }

    @Override
    public Visibility getAccessModifier() {
        return accessModifier;
    }

    @Override
    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    @Override
    public boolean isAbstract() {
        return isAbstract;
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
    public String getSuperclass() {
        return superclass;
    }

    @Override
    public List<String> getInterfaces() {
        return interfaces;
    }

    @Override
    public List<String> getDependencies() {
        return dependencies;
    }

    @Nullable
    @Override
    public String getGenericSrc() {
        return genericSrc;
    }

    @Override
    public int compareTo(IAnalytic o) {
        if(o instanceof AnalyticClass) {
            return this.name.compareTo(((AnalyticClass) o).name);
        } else return -1;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof AnalyticClass && ((AnalyticClass) obj).name.equals(name);
    }

    static String parseName(String src) {
        return getSignaturePart(src, PART_SECOND_CLASS_NAME);
    }

    static Visibility parseVisibility(String src) {
        String visibilityPart = getSignaturePart(src, PART_FIRST_MODIFIERS);
        if(visibilityPart.contains("public")) {
            return Visibility.PUBLIC;
        } else if(visibilityPart.contains("private")) {
            return Visibility.PRIVATE;
        } else return Visibility.PACKAGE_LOCAL;
    }

    static boolean parseFinalModifier(String src) {
        return getSignaturePart(src, PART_FIRST_MODIFIERS).contains("final");
    }

    static boolean parseStaticModifier(String src) {
        return getSignaturePart(src, PART_FIRST_MODIFIERS).contains("static");
    }

    static boolean parseAbstractModifier(String src) {
        return getSignaturePart(src, PART_FIRST_MODIFIERS).contains("abstract");
    }

    static String parseSuperClass(String src) {
        Matcher matcher = Pattern
                .compile("extends[\\s]+([^;<>\\s]+)[\\s]+")
                .matcher(getSignaturePart(src, PART_THIRD));
        if(matcher.find()) {
            return matcher.group(1);
        } else return Object.class.getName();
    }

    static List<IAnalyticProperty> parseProperties(String src) {
        return Collections.emptyList();
    }

    static List<IAnalyticMethod> parseMethods(String src) {
        return Collections.emptyList();
    }

    static List<String> parseInterfaces(String src) {
        List<String> result = new ArrayList<>();
        Matcher matcher = Pattern
                .compile("implements[\\s]+([^{}<>]+)")
                .matcher(getSignaturePart(src, PART_THIRD));
        if(matcher.find()) {
            String allInterfaces = matcher.group(1);
            if(allInterfaces.contains(",")) {
                String interfaces[] = allInterfaces.split(",");
                for(String i : interfaces) {
                    result.add(i.trim());
                }
            } else result.add(allInterfaces.trim());
        }
        return result;
    }

    static List<String> parseDependencies(String src) {
        return Collections.emptyList();
    }

    static List<IAnalyticClass> parseInsertedClasses(String src) {
        Matcher matcher = SIGNATURE_PATTERN.matcher(src);
        matcher.find();

        return AnalyticFile.parseClasses(src.substring(matcher.end()));
    }

    @Nullable
    static String parseGeneric(String src) {
        return null;
    }

    private static String getSignaturePart(String src, int partNum) {
        Matcher matcher = SIGNATURE_PATTERN.matcher(src);
        //noinspection ResultOfMethodCallIgnored
        matcher.find();
        return matcher.group(partNum);
    }

}

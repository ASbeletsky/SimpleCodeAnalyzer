package bl.model.impl;

import bl.model.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.sun.deploy.util.ArrayUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public class AnalyticClass extends AnalyticBase<TypeDeclaration> implements IAnalyticClass {
    private final List<IAnalyticProperty> properties = new ArrayList<>();
    private final List<IAnalyticMethod> methods = new ArrayList<>();
    private final List<IAnalyticClass> insertedClasses = new ArrayList<>();
    private final List<IAnalyticClass> interfaces = new ArrayList<>();
    private final List<IAnalyticClass> dependencies = new ArrayList<>();
    private IAnalyticClass superclass;

    public AnalyticClass(String src) throws ParseException {
        this((TypeDeclaration) JavaParser.parseBodyDeclaration(src));
    }

    public AnalyticClass(TypeDeclaration declaration){
        super(declaration);
        this.fillMembers();
    }

    private void fillMembers(){
        List<BodyDeclaration> classMembers =  this.codeBlock.getMembers();
        for (BodyDeclaration member: classMembers){
            if (member instanceof MethodDeclaration){
                IAnalyticMethod method = new AnalyticMethod((MethodDeclaration)member);
                this.methods.add(method);
            }
        }
    }

    @Override
    public String getName() {
        return this.codeBlock.getName();
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
    public List<IAnalyticClass> getDependencies() {
        return dependencies;
    }

    @Override
    public int compareTo(IAnalytic o) {
        return 0;
    }
}

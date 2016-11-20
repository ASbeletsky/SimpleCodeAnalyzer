package bl.model.impl;

import bl.model.*;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.TypeDeclarationStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.sun.deploy.util.ArrayUtil;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public class AnalyticClass extends AnalyticBase<ClassOrInterfaceDeclaration> implements IAnalyticClass {
    private final List<IAnalyticField> fields = new ArrayList<>();
    private final List<IAnalyticMethod> methods = new ArrayList<>();
    private final List<IAnalyticClass> innerClasses = new ArrayList<>();
    private final List<IAnalyticClass> interfaces = new ArrayList<>();
    private final List<IAnalyticClass> dependencies = new ArrayList<>();
    private IAnalyticClass superclass;

    public AnalyticClass(String src) throws ParseException {
        this((ClassOrInterfaceDeclaration) JavaParser.parseBodyDeclaration(src));
    }

    public AnalyticClass(ClassOrInterfaceDeclaration declaration){
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

            if (member instanceof FieldDeclaration){
                IAnalyticField field = new AnalyticField((FieldDeclaration)member);
                this.fields.add(field);
            }

            if (member instanceof ClassOrInterfaceDeclaration){
                IAnalyticClass innerClass = new AnalyticClass((ClassOrInterfaceDeclaration)member);
                this.innerClasses.add(innerClass);
            }
        }
    }

    @Override
    public String getName() {
        return this.codeBlock.getName();
    }

    @Override
    public List<IAnalyticField> getFields(){
        return this.fields;
    }
    @Override
    public List<IAnalyticMethod> getMethods() {
        return methods;
    }

    @Override
    public List<IAnalyticClass> getInnerClasses() {
        return this.innerClasses;
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

}

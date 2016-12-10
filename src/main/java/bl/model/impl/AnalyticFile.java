package bl.model.impl;

import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public class AnalyticFile extends AnalyticBase<CompilationUnit> implements IAnalyticFile {
    private final List<IAnalyticClass> classes = new ArrayList<>();

    public AnalyticFile(String source) throws ParseException {
        super(source);
        this.scanClassesInFile();
    }

    public AnalyticFile(File file) throws IOException, ParseException {
        super(new FileInputStream(file));
        this.scanClassesInFile();
    }

    private void scanClassesInFile() {
        for (TypeDeclaration declaration : super.codeBlock.getTypes()) {
            if (declaration instanceof ClassOrInterfaceDeclaration) {
                AnalyticClass analyticClass = new AnalyticClass((ClassOrInterfaceDeclaration) declaration);
                this.classes.add(analyticClass);
            }
        }
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public List<IAnalyticClass> getClasses() {
        return classes;
    }
}

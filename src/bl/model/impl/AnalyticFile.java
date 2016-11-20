package bl.model.impl;

import bl.model.IAnalytic;
import bl.model.IAnalyticClass;
import bl.model.IAnalyticFile;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.TypeDeclaration;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 14.09.2016.
 */
public class AnalyticFile extends AnalyticBase<CompilationUnit> implements IAnalyticFile {
    private final List<IAnalyticClass> classes = new ArrayList<>();

    private void scanClassesInFile(){
        for(TypeDeclaration declaration: super.codeBlock.getTypes()){
            AnalyticClass analyticClass = new AnalyticClass(declaration);
            this.classes.add(analyticClass);
        }
    }

    public AnalyticFile(String source) throws ParseException {
        super(source);
        this.scanClassesInFile();
    }

    public  AnalyticFile(File file) throws IOException, ParseException {
        super(new FileInputStream(file));
        this.scanClassesInFile();
    }

    @Override
    public String getName() {
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

package bl.model.impl;

import bl.model.IAnalytic;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Anton on 19.11.2016.
 */
public abstract class AnalyticBase<TCodeBlock extends Node> implements IAnalytic<TCodeBlock> {
    protected String source;
    protected TCodeBlock codeBlock;

    public AnalyticBase(String source) throws ParseException {
        this(new ByteArrayInputStream(source.getBytes()));
    }

    public  AnalyticBase(InputStream sourceSream) throws ParseException {
        this.codeBlock = (TCodeBlock) JavaParser.parse(sourceSream);
        this.source = this.codeBlock.toString();
    }

    public  AnalyticBase(TCodeBlock codeBlock){
        this.codeBlock = codeBlock;
        this.source = codeBlock.toString();
    }

    public TCodeBlock getAbstractSynaxTree(){
        return codeBlock;
    }

    public abstract String getName();

    public String getSourceCode(){
        return  this.source;
    }
}

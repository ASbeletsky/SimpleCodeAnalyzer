package bl.metrix.mccabe.service.impl;

import bl.metrix.mccabe.service.CompilerService;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;

public class CompilerServiceImpl implements CompilerService {
    private JavaCompiler compiler;

    public CompilerServiceImpl() {
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    @Override
    public File compileJavaSource(File file) {
        System.out.println(String.format("Compiling %s ..", file.getName()));
        int status = compiler.run(null, null, null, file.getPath());

        if (status == 0)
            return new File(file.getAbsolutePath().replace(".java", ".class"));
        throw new IllegalStateException("unable to compile " + file.getName());
    }
}

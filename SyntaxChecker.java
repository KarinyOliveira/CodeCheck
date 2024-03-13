import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

public class SyntaxChecker {

    public static void main(String[] args) {
        String javaCode = "public class Test { public static void main(String[] args) { System.out.println(\"Hello, World!\"); } }";
        boolean isSyntaxCorrect = checkSyntax(javaCode);
        if (isSyntaxCorrect) {
            System.out.println("Syntax is correct.");
        } else {
            System.out.println("Syntax is incorrect.");
        }
    }

    public static boolean checkSyntax(String javaCode) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaFileObject file = new StringJavaFileObject("Test", javaCode);
        Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(file);
        JavaCompiler.CompilationTask task = compiler.getTask(null, null, diagnostics, null, null, compilationUnits);

        return task.call();
    }

    static class StringJavaFileObject extends SimpleJavaFileObject {
        private final String code;

        StringJavaFileObject(String className, String code) {
            super(URI.create("string:///" + className.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return code;
        }
    }
}

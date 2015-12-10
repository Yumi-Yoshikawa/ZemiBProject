import org.eclipse.jdt.core.dom.CompilationUnit;

public class ZemiB {

	public static void main(final String[] args) {

		final CompilationUnit unit = ZemiBASTVisitor.createAST(args[0]);
		final ZemiBASTVisitor visitor = new ZemiBASTVisitor();
		unit.accept(visitor);
	}
}

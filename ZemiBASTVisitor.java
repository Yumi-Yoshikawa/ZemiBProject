import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.internal.core.dom.NaiveASTFlattener;

public class ZemiBASTVisitor extends NaiveASTFlattener {

	static public CompilationUnit createAST(final String file) {

		try {

			final List<String> lines = Files.readAllLines(Paths.get(file),
					Charset.forName("UTF-8"));
			final String text = String.join(System.lineSeparator(), lines);

			System.out.println("---------- text starts ---------");
			System.out.println(text);
			System.out.println("---------- text ends -----------");

			final ASTParser parser = ASTParser.newParser(AST.JLS4);
			parser.setSource(text.toCharArray());
			return (CompilationUnit) parser.createAST(null);

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public boolean visit(SimpleName node) {
		System.out.println("SimpleName: " + node.getIdentifier());
		return super.visit(node);
	}
}

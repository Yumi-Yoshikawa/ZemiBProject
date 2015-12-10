package tsukada;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LOC、SLOC、コメントの行数を計算する
 *
 * @author tsukada keishi
 *
 */
public class LocAndCommentCalculator {

	private final int loc;
	private final int sloc;
	private final int commentLine;

	/**
	 *
	 * @param inputfile
	 *            LOC、SLOC、コメントの行数を計算する対象のファイル
	 * @throws IOException
	 *             ファイルのioに失敗した場合。 LOC,SLOC,コメントの行数の値は-1になる
	 */
	public LocAndCommentCalculator(final String inputfile) throws IOException{
		File file = new File(inputfile);

		byte[] b = new byte[(int) file.length()];
		FileInputStream fi;

		String str;
		String str2;
		fi = new FileInputStream(file);
		try {
			fi.read(b);
			str = new String(b);
			str2 = new String(b);
			fi.close();
		} catch (IOException e) {
			this.loc = -1;
			this.sloc = -1;
			this.commentLine = -1;
			e.printStackTrace();
			fi.close();
			throw e;
		}

		this.loc = getLastLineNumber(str);

		Pattern p;
		Matcher m;

//SLOCの計算
		p = Pattern.compile("/\\*.*?\\*/| |\t|");
		m = p.matcher(str);
		str = m.replaceAll("");

		p = Pattern.compile("/\\*(.|\\s)*?\\*/");
		m = p.matcher(str);
		str = m.replaceAll("\n");

		p = Pattern.compile("//.*");
		m = p.matcher(str);
		str = m.replaceAll("");

		if(str.startsWith("\n")){
			str = str.substring(1);
		}
		if(str.endsWith("\n")){
			str = str.substring(0,str.length()-1);
		}

		p = Pattern.compile("\n\n");
		m = p.matcher(str);
		while(m.find()){
			str = m.replaceFirst("\n");
			p = Pattern.compile("\n\n");
			m = p.matcher(str);
		}

		this.sloc = getLastLineNumber(str);


//コメント行の計算
		p = Pattern.compile(".*/\\*(.|\\s)*?\\*/.*\n| |\t|");
		m = p.matcher(str2);
		str2 = m.replaceAll("");

		p = Pattern.compile("\n.*/\\*(.|\\s)*?\\*/.*");
		m = p.matcher(str2);
		str2 = m.replaceAll("");

		p = Pattern.compile(".*//.*\n");
		m = p.matcher(str2);
		str2 = m.replaceAll("");

		p = Pattern.compile("\n.*//.*");
		m = p.matcher(str2);
		str2 = m.replaceAll("");

		this.commentLine = loc - getLastLineNumber(str2);

	}

	/**
	 * LOC(コメント、空行含むすべての行数)を返す
	 *
	 * @return LOC
	 */
	public int getLOC() {
		return loc;
	}

	//
	/**
	 * SLOC(コメント、空行を含まない行数)を返す
	 *
	 * @return SLOC
	 */
	public int getSLOC() {
		return sloc;
	}

	//
	/**
	 * コメントがある行の行数を返す
	 *
	 * @return コメントがある行の行数
	 */
	public int getLineOfComment() {
		return commentLine;
	}

	private int getLastLineNumber(String str) throws IOException {
		BufferedReader br = new BufferedReader(new StringReader(str));
		int i = 1;
		int c;
		while ((c = br.read()) != -1) {
			if (c == '\n') {
				i++;
			}
		}
		return i;
	}
}

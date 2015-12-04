package tsukada;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
* LOCとSLOCを計算し出力する
* @author tsukada keishi
*
*/
public class LOC{

	private final int loc;
	private final int sloc;
	/**
	 *
	 * @param inputfile LOC、SLOCを計算する対象のファイル
	 * @throws IOException ファイルのioに失敗した場合。
	 * LOC,SLOCの値は-1に設定される
	 */
	public LOC(final String inputfile) throws IOException{
		File file = new File(inputfile);

		byte[] b = new byte[(int) file.length()];
		FileInputStream fi;

		String	str;
		fi = new FileInputStream(file);
		try {
			fi.read(b);
			str = new String(b);
			fi.close();
		} catch (IOException e) {
			this.loc = -1;
			this.sloc = -1;
			e.printStackTrace();
			fi.close();
			throw e;
		}

		this.loc = str.split("\n").length;

		Pattern p = Pattern.compile("/\\*(.|\\s)*?\\*/");
		Matcher m = p.matcher(str);
		str = m.replaceAll("");

		p = Pattern.compile(" ");
		m = p.matcher(str);
		str = m.replaceAll("");

		p = Pattern.compile("\t");
		m = p.matcher(str);
		str = m.replaceAll("");

		p = Pattern.compile("//.*");
		m = p.matcher(str);
		str = m.replaceAll("");



		p = Pattern.compile("\n\n");
		m = p.matcher(str);
		while(m.find()){
			str = m.replaceFirst("\n");
			p = Pattern.compile("\n\n");
			m = p.matcher(str);
		}

		this.sloc = str.split("\n").length;
	}
	/**
	 * LOC(コメント、空行含むすべての行数)を返す
	 * @return LOC
	 */
	public int getLOC(){
		return loc;
	}

	//
	/**
	 * SLOC(コメント、空行を含まない行数)を返す
	 * @return SLOC
	 */
	public int getSLOC(){
		return sloc;
	}
}

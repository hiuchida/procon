import java.io.PrintWriter;

public class Util {
	public static String getInPath(String fname) {
		String inpath = System.getenv("IN_PATH");
		return inpath + "/csv/" + fname;
	}

	public static String getOutPath(String fname) {
		return "./csv/" + fname;
	}

	public static String getDumpPath(int lineNo) {
		return "./dump/dump" + lineNo + ".txt";
	}

	public static String[] getSubCols(String[] cols, int idx) {
		String[] ary = new String[cols.length - idx];
		for (int i = 0; i < ary.length; i++) {
			ary[i] = cols[idx + i];
		}
		return ary;
	}

	public static void dumpTableInfo(PrintWriter pw, String name, int maxCol, int maxRow) {
		pw.println("name:" + name);
		pw.println("maxCol:" + maxCol);
		pw.println("maxRow:" + maxRow);
	}

	public static void dumpFieldInfo(PrintWriter pw, int colidx, String val) {
		pw.println("flds[" + colidx + "]:" + val);
	}

	public static void dumpDataInfo(PrintWriter pw, int rowidx, int colidx, String val) {
		pw.println("tbl[" + rowidx + "][" + colidx + "]:" + val);
	}

}

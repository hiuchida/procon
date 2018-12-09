import java.io.PrintWriter;
import java.util.List;

public class VM {
	public static final int ST = 0;
	public static final int SS = 1;
	public static final int SR = 2;
	public static final int SG = 3;
	public static final int MS = 4;
	public static final int MR = 5;
	public static final int MG = 6;
	public static final int ED = 7;
	public static final int UNKNOWN = 99;
	private List<String> list;
	private PrintWriter _out;

	public VM(List<String> list, PrintWriter pw) {
		this.list = list;
		this._out = pw;
	}

	public void execute() {
		BigArrayIF logic = null;
		int lineNo = 0;
		for (String line : list) {
			lineNo++;
			if (line.startsWith("#"))
				continue;
			String[] cols = split(line);
			int cmdNo = getCmdNo(cols[0]);
			switch (cmdNo) {
			case ST:
				if (logic != null)
					throw new RuntimeException("logic != null");
				logic = st(cols[1]);
				if (logic == null)
					throw new RuntimeException("logic == null");
				// ok(lineNo, cmdNo, cols[1]);
				break;
			case UNKNOWN:
				err(lineNo, 99, 0, line);
				break;
			default:
				if (logic == null)
					throw new RuntimeException("logic == null");
				if (logic.validate(lineNo, line, cmdNo, cols)) {
					String resp = logic.execute(lineNo, line, cmdNo, cols);
					if (resp != null)
						ok(lineNo, cmdNo, resp);
				}
				break;
			}
		}
	}

	private String[] split(String line) {
		String[] cols = line.split(" ");
		String[] ary = new String[10];
		for (int i = 0; i < cols.length; i++)
			ary[i] = cols[i];
		for (int i = cols.length; i < ary.length; i++)
			ary[i] = "";
		return ary;
	}

	private int getCmdNo(String cmd) {
		switch (cmd) {
		case "st":
			return ST;
		case "ss":
			return SS;
		case "sr":
			return SR;
		case "sg":
			return SG;
		case "ms":
			return MS;
		case "mr":
			return MR;
		case "mg":
			return MG;
		case "ed":
			return ED;
		}
		return UNKNOWN;
	}

	private BigArrayIF st(String param) {
		switch (param) {
		case "3": // for development
			return new ArrayImpl(this, 3);
		case "7":
			return new ArrayImpl(this, 7);
		case "12":
			return new RangeListImpl(this, 12);
		}
		return null;
	}

	public void ok(int lineNo, int cmdNo, String s) {
		pln(String.format("L%04d I%02d00 %s", lineNo, cmdNo, s));
	}

	public void err(int lineNo, int cmdNo, int errNo, String s) {
		pln(String.format("L%04d E%02d%02d %s", lineNo, cmdNo, errNo, s));
	}

	public VM pln(String s) {
		_out.println(s);
		return this;
	}

}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws Exception {
		Main q = new Main();
		q.analize();
		q.showResult();
	}

	private char[] buf;
	private int rows = 24;
	private int columns = 80;

	public Main() {
		initBuffer();
	}

	public void analize() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String line = br.readLine();
			int n = Integer.parseInt(line);
			for (int i = 0; i < n; i++) {
				line = br.readLine();
				if (isComment(line)) {
					continue;
				}
				String str = analizeKeyword(line);
				if (str != null && str.length() > 1) {
					str = str.substring(1, str.length() - 1);
					setString(getRow(line), getColumn(line), str);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showResult() {
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < columns; x++) {
				if (buf[y * columns + x] != 0) {
					System.out.print(buf[y * columns + x]);
				}
			}
			System.out.println();
		}
	}

	private boolean isComment(String line) {
		return "A*".equals(substring(line, 5, 7));
	}

	private String analizeKeyword(String line) {
		String str = substring(line, 44).trim();
		if (str.startsWith("DSPSIZ")) {
			String[] params = getParameters(str);
			rows = Integer.parseInt(params[0]);
			columns = Integer.parseInt(params[1]);
			initBuffer();
			return null;
		}
		if (str.startsWith("DSPATR")) {
			String[] params = getParameters(str);
			String ch = ".";
			if ("UL".equals(params[0])) {
				ch = "_";
			}
			String val = "";
			int len = getLength(line);
			for (int i = 0; i < len; i++) {
				val += ch;
			}
			setString(getRow(line), getColumn(line), val);
			return null;
		}
		return str;
	}

	private String[] getParameters(String keyword) {
		String params = keyword.substring(keyword.indexOf("(") + 1);
		params = params.substring(0, params.length() - 1);
		return params.split(" ");
	}

	private int getRow(String line) {
		String row = substring(line, 38, 41).trim();
		try {
			return Integer.parseInt(row);
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	private int getColumn(String line) {
		String column = substring(line, 41, 44).trim();
		try {
			return Integer.parseInt(column);
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	private int getLength(String line) {
		String len = substring(line, 29, 34).trim();
		try {
			return Integer.parseInt(len);
		} catch (NumberFormatException e) {
		}
		return 0;
	}

	private void setString(int row, int column, String str) {
		int start = (row - 1) * columns + column - 1;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			int index = start + count++;
			if (index >= rows * columns) {
				index -= rows * columns;
			}
			if (str.charAt(i) >= 0x100) {
				buf[index] = str.charAt(i);
				buf[index + 1] = 0;
				count++;
			} else {
				buf[index] = str.charAt(i);
			}
		}
	}

	private void initBuffer() {
		buf = new char[rows * columns];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = ' ';
		}
	}

	private String substring(String line, int start, int end) {
		return line.substring(start, end);
	}

	private String substring(String line, int start) {
		return line.substring(start);
	}
}

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

public class Table {
	String name;
	int maxCol;
	int maxRow;
	private String[] flds;
	private Set<String> fldSet = new HashSet<>();
	private String[][] tbl;

	public Table(String name, int maxCol, int maxRow) {
		this.name = name;
		this.maxCol = maxCol;
		this.maxRow = maxRow;
		this.flds = new String[maxCol];
		this.tbl = new String[maxRow][maxCol];
	}

	public void setTable(Table intbl) {
		setFields(intbl.flds);
		for (int row = 0; row < intbl.maxRow; row++) {
			setRecord(row, intbl.tbl[row]);
		}
	}

	public void setField(int idx, String fldName) {
		if (fldSet.contains(fldName))
			throw new RuntimeException("Table.setField: not unique fldName=" + fldName);
		fldSet.add(fldName);
		this.flds[idx] = fldName;
	}
	
	public String getField(int idx) {
		return this.flds[idx];
	}

	public String[] getFields() {
		return this.flds;
	}

	public void setFields(String[] fields) {
		for (int i = 0; i < fields.length; i++) {
			setField(i, fields[i]);
		}
	}

	public int getFieldIdx(String fldName) {
		for (int i = 0; i < flds.length; i++) {
			if (fldName.equals(flds[i])) {
				return i;
			}
		}
		throw new RuntimeException("Table.getFieldIdx: not found fldName=" + fldName);
	}

	public String getValue(int rowno, int colno) {
		return this.tbl[rowno][colno];
	}

	public int getIntValue(int rowno, int colno) {
		return AnswerUtil.parseInt(getValue(rowno, colno));
	}

	public void setValue(int rowno, int colno, String value) {
		this.tbl[rowno][colno] = value;
	}

	public void setIntValue(int rowno, int colno, int value) {
		setValue(rowno, colno, String.valueOf(value));
	}

	public String[] getRecord(int rowno) {
		return this.tbl[rowno];
	}

	public void setRecord(int rowno, String[] fields) {
		for (int i = 0; i < fields.length; i++) {
			this.tbl[rowno][i] = fields[i];
		}
	}

	public void dump(PrintWriter pw) {
		Util.dumpTableInfo(pw, name, maxCol, maxRow);
		for (int col = 0; col < flds.length; col++) {
			Util.dumpFieldInfo(pw, col, flds[col]);
		}
		for (int row = 0; row < maxRow; row++) {
			for (int col = 0; col < maxCol; col++) {
				Util.dumpDataInfo(pw, row, col, tbl[row][col]);
			}
		}
	}

	public void write(PrintWriter pw) {
		for (int i = 0; i < flds.length; i++) {
			if (i > 0)
				pw.print(",");
			pw.print(flds[i]);
		}
		pw.println();
		for (int row = 0; row < maxRow; row++) {
			for (int col = 0; col < maxCol; col++) {
				if (col > 0)
					pw.print(",");
				pw.print(tbl[row][col]);
			}
			pw.println();
		}
	}

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class VM {
	private List<String> list;
	private TableManager tblmgr = TableManager.getInstance();

	public VM(List<String> list) {
		this.list = list;
	}

	public void execute() throws IOException {
		int lineNo = 0;
		for (String line : list) {
			lineNo++;
			if (line.startsWith("#"))
				continue;
			String[] cols = line.split(" ");
			String cmdStr = cols[0].toUpperCase();
			String tblname = cols[1];
			String[] params = Util.getSubCols(cols, 2);
			Command cmd = Command.valueOf(Command.class, cmdStr);
			switch (cmd) {
			case READ:
				read(tblname, params);
				break;
			case WRITE:
				write(tblname, params);
				break;
			case APPEND_COL:
				append_col(tblname, params);
				break;
			case SUBSTR:
				substr(tblname, params);
				break;
			case CONCAT1:
			case CONCAT2:
				concat(tblname, params);
				break;
			case SELECT_COL:
				select_col(tblname, params);
				break;
			case SELECT_ROW:
				select_row(tblname, params);
				break;
			case COUNT1:
			case COUNT2:
				aggregate(tblname, params, Aggregate.COUNT);
				break;
			case MIN1:
			case MIN2:
				aggregate(tblname, params, Aggregate.MIN);
				break;
			case MAX1:
			case MAX2:
				aggregate(tblname, params, Aggregate.MAX);
				break;
			default:
				throw new RuntimeException("VM.execute: not impl cmd=" + cmd);
			}
			dump(lineNo, tblname);
		}
	}

	private void dump(int lineNo, String tblname) throws IOException {
		String txtpath = Util.getDumpPath(lineNo);
		try (PrintWriter pw = new PrintWriter(new FileWriter(txtpath))) {
			Table tbl = tblmgr.get(tblname);
			tbl.dump(pw);
		} finally {
		}
	}

	private void read(String tblname, String[] params) throws IOException {
		String csvname = params[0];
		String csvpath = Util.getInPath(csvname);
		try (BufferedReader br = new BufferedReader(new FileReader(csvpath))) {
			List<String> list = new ArrayList<>();
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				list.add(line);
			}
			String header = list.get(0);
			String[] fields = header.split(",");
			Table tbl = new Table(tblname, fields.length, list.size() - 1);
			tbl.setFields(fields);
			for (int i = 1; i < list.size(); i++) {
				fields = list.get(i).split(",");
				tbl.setRecord(i - 1, fields);
			}
			tblmgr.set(tblname, tbl);
		} finally {
		}
	}

	private void write(String tblname, String[] params) throws IOException {
		String csvname = params[0];
		String csvpath = Util.getOutPath(csvname);
		try (PrintWriter pw = new PrintWriter(new FileWriter(csvpath))) {
			Table tbl = tblmgr.get(tblname);
			tbl.write(pw);
		} finally {
		}
	}

	private void append_col(String outtblname, String[] params) {
		String intblname = params[0];
		String outcolname = params[1];
		String conststr = params[2];
		Table intbl = tblmgr.get(intblname);
		Table outtbl = new Table(outtblname, intbl.maxCol + 1, intbl.maxRow);
		outtbl.setTable(intbl);
		outtbl.setField(intbl.maxCol, outcolname);
		for (int row = 0; row < intbl.maxRow; row++) {
			outtbl.setValue(row, intbl.maxCol, conststr);
		}
		tblmgr.set(outtblname, outtbl);
	}

	private void substr(String outtblname, String[] params) {
		String intblname = params[0];
		String outcolname = params[1];
		String incolname = params[2];
		String fromidx = params[3];
		String toidx = params[4];
		Table intbl = tblmgr.get(intblname);
		Table outtbl = new Table(outtblname, intbl.maxCol + 1, intbl.maxRow);
		outtbl.setTable(intbl);
		outtbl.setField(intbl.maxCol, outcolname);
		int idx = intbl.getFieldIdx(incolname);
		for (int row = 0; row < intbl.maxRow; row++) {
			String val = intbl.getValue(row, idx);
			int from = AnswerUtil.parseInt(fromidx);
			int to = AnswerUtil.parseInt(toidx);
			val = val.substring(from, to + 1);
			outtbl.setValue(row, intbl.maxCol, val);
		}
		tblmgr.set(outtblname, outtbl);
	}

	private void concat(String outtblname, String[] params) {
		String intblname = params[0];
		String outcolname = params[1];
		String[] colnames = Util.getSubCols(params, 2);
		Table intbl = tblmgr.get(intblname);
		Table outtbl = new Table(outtblname, intbl.maxCol + 1, intbl.maxRow);
		outtbl.setTable(intbl);
		outtbl.setField(intbl.maxCol, outcolname);
		int idxes[] = AnswerUtil.getColumnIndexes(intbl, colnames);
		for (int row = 0; row < intbl.maxRow; row++) {
			StringBuilder sb = new StringBuilder();
			for (int col = 0; col < idxes.length; col++) {
				sb.append(intbl.getValue(row, idxes[col]));
			}
			outtbl.setValue(row, intbl.maxCol, sb.toString());
		}
		tblmgr.set(outtblname, outtbl);
	}

	private void select_row(String outtblname, String[] params) {
		String intblname = params[0];
		String colname = params[1];
		String cmptype = params[2];
		String cond = params[3];
		Table intbl = tblmgr.get(intblname);
		List<Integer> list = new ArrayList<>();
		int idx = intbl.getFieldIdx(colname);
		for (int row = 0; row < intbl.maxRow; row++) {
			String val = intbl.getValue(row, idx);
			if (cmptype.equals("$0") && val.equals(cond)
					|| cmptype.equals("$1") && val.startsWith(cond)
					|| cmptype.equals("$2") && val.endsWith(cond)
					|| cmptype.equals("$3") && val.indexOf(cond) >= 0) {
				list.add(row);
			}
		}
		Table outtbl = new Table(outtblname, intbl.maxCol, list.size());
		outtbl.setFields(intbl.getFields());
		for (int i = 0; i < list.size(); i++) {
			int row = list.get(i);
			outtbl.setRecord(i, intbl.getRecord(row));
		}
		tblmgr.set(outtblname, outtbl);
	}

	private void select_col(String outtblname, String[] params) {
		String intblname = params[0];
		String[] colnames = Util.getSubCols(params, 1);
		Table intbl = tblmgr.get(intblname);
		Table outtbl = new Table(outtblname, colnames.length, intbl.maxRow);
		outtbl.setFields(colnames);
		int idxes[] = AnswerUtil.getColumnIndexes(intbl, colnames);
		for (int col = 0; col < idxes.length; col++) {
			for (int row = 0; row < intbl.maxRow; row++) {
				outtbl.setValue(row, col, intbl.getValue(row, idxes[col]));
			}
		}
		tblmgr.set(outtblname, outtbl);
	}

	private void aggregate(String outtblname, String[] params, Aggregate ope) {
		String intblname = params[0];
		String outcolname = params[1];
		String sumcolname = params[2];
		String[] colnames = Util.getSubCols(params, 3);
		int maxCol = colnames.length + 1;
		Table intbl = tblmgr.get(intblname);
		AggregateTable st = new AggregateTable();
		int idx0 = intbl.getFieldIdx(sumcolname);
		int idxes[] = AnswerUtil.getColumnIndexes(intbl, colnames);
		for (int row = 0; row < intbl.maxRow; row++) {
			String val = intbl.getValue(row, idx0);
			String[] pks = AnswerUtil.getIndexValues(intbl, row, idxes);
			st.add(ope, pks, val);
		}
		Table outtbl = new Table(outtblname, maxCol, st.getMaxRow());
		outtbl.setField(0, outcolname);
		for (int col = 0; col < idxes.length; col++) {
			outtbl.setField(col + 1, colnames[col]);
		}
		List<AggregateTable.Record> list = st.getList();
		for (int row = 0; row < list.size(); row++) {
			AggregateTable.Record r = list.get(row);
			outtbl.setValue(row, 0, r.getVal());
			for (int col = 0; col < colnames.length; col++) {
				outtbl.setValue(row, col + 1, r.pks[col]);
			}
		}
		tblmgr.set(outtblname, outtbl);
	}

}

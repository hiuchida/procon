public class AnswerUtil {
	public static String getCompositeKey(String[] pks) {
		StringBuilder sb = new StringBuilder();
		sb.append(pks[0]);
		for (int i = 1; i < pks.length; i++) {
			sb.append(",").append(pks[i]);
		}
		return sb.toString();
	}

	public static int parseInt(String s) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return 0;
		}
	}

	public static int[] getColumnIndexes(Table tbl, String[] colnames) {
		int idxes[] = new int[colnames.length];
		for (int col = 0; col < colnames.length; col++) {
			idxes[col] = tbl.getFieldIdx(colnames[col]);
		}
		return idxes;
	}

	public static String[] getIndexValues(Table tbl, int row, int[] idxes) {
		String[] vals = new String[idxes.length];
		for (int col = 0; col < idxes.length; col++) {
			vals[col] = tbl.getValue(row, idxes[col]);
		}
		return vals;
	}

}

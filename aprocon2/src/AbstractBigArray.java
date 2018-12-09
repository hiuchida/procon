public abstract class AbstractBigArray implements BigArrayIF {
	protected int _keta;
	protected VM _vm;

	public abstract void ss(long idx, int val);

	public abstract int sg(long idx);

	public AbstractBigArray(int keta, VM vm) {
		_keta = keta;
		_vm = vm;
	}

	public boolean validate(int lineNo, String line, int cmdNo, String[] cols) {
		int rc;
		switch (cmdNo) {
		case VM.SS:
			rc = checkParams(cols, new int[] { _keta, 2 });
			if (rc > 0) {
				err(lineNo, cmdNo, rc, line);
				return false;
			}
			break;
		case VM.SR:
		case VM.SG:
			rc = checkParams(cols, new int[] { _keta });
			if (rc > 0) {
				err(lineNo, cmdNo, rc, line);
				return false;
			}
			break;
		case VM.MS:
			rc = checkParams(cols, new int[] { _keta, _keta, 2 });
			if (rc > 0) {
				err(lineNo, cmdNo, rc, line);
				return false;
			}
			break;
		case VM.MR:
		case VM.MG:
			rc = checkParams(cols, new int[] { _keta, _keta });
			if (rc > 0) {
				err(lineNo, cmdNo, rc, line);
				return false;
			}
			break;
		case VM.ED:
			break;
		}
		return true;
	}

	public String execute(int lineNo, String line, int cmdNo, String[] cols) {
		long k1;
		long k2;
		int v;
		int[] va;
		long c;
		boolean slog = false;
		switch (cmdNo) {
		case VM.SS:
			k1 = plong(cols[1]);
			v = pint(cols[2]);
			ss(k1, v);
			if (slog)
				return "1";
			return null;
		case VM.SR:
			k1 = plong(cols[1]);
			sr(k1);
			if (slog)
				return "1";
			return null;
		case VM.SG:
			k1 = plong(cols[1]);
			v = sg(k1);
			return formatIdx(k1) + formatVal(v);
		case VM.MS:
			k1 = min(plong(cols[1]), plong(cols[2]));
			k2 = max(plong(cols[1]), plong(cols[2]));
			v = pint(cols[3]);
			ms(k1, k2, v);
			if (slog)
				return "" + (k2 - k1 + 1);
			return null;
		case VM.MR:
			k1 = min(plong(cols[1]), plong(cols[2]));
			k2 = max(plong(cols[1]), plong(cols[2]));
			mr(k1, k2);
			if (slog)
				return "" + (k2 - k1 + 1);
			return null;
		case VM.MG:
			k1 = min(plong(cols[1]), plong(cols[2]));
			k2 = max(plong(cols[1]), plong(cols[2]));
			va = mg(k1, k2);
			return dump(k1, va);
		case VM.ED:
			c = ed();
			return "" + c;
		}
		return null;
	}

	public void sr(long idx) {
		ss(idx, -1);
	}

	public void mr(long idx1, long idx2) {
		long idxf = min(idx1, idx2);
		long idxt = max(idx1, idx2);
		ms(idxf, idxt, -1);
	}

	public int[] mg(long idx1, long idx2) {
		long idxf = min(idx1, idx2);
		long idxt = max(idx1, idx2);
		idxt = min(idxf + 19, idxt);
		int[] ary = new int[(int) (idxt - idxf + 1)];
		for (int i = 0; i <= idxt - idxf; i++)
			ary[i] = sg(i + idxf);
		return ary;
	}

	private int checkParams(String[] cols, int[] max) {
		int rc = 0;
		int mask = 1;
		for (int i = 0; i < max.length; i++) {
			if (!checkLength(cols[i + 1], 1, max[i]))
				rc |= mask;
			else if (!checkCharCode(cols[i + 1]))
				rc |= mask;
			mask *= 2;
		}
		return rc;
	}

	private boolean checkLength(String v, int min, int max) {
		if (min <= v.length() && v.length() <= max)
			return true;
		return false;
	}

	private boolean checkCharCode(String v) {
		for (char ch : v.toCharArray()) {
			if (ch < '0' || ch > '9')
				return false;
		}
		return true;
	}

	private String formatIdx(long idx) {
		String f = String.format("%%0%dd ", _keta);
		return String.format(f, idx);
	}

	private String formatVal(int val) {
		if (val < 0)
			return "--";
		else
			return String.format("%02d", val);
	}

	private String dump(long idx, int[] ary) {
		StringBuilder sb = new StringBuilder();
		sb.append(formatIdx(idx));
		for (int i = 0; i < ary.length; i++) {
			sb.append(formatVal(ary[i]));
			if (i < ary.length - 1)
				sb.append(" ");
		}
		return sb.toString();
	}

	protected void ok(int lineNo, int cmdNo, String s) {
		_vm.ok(lineNo, cmdNo, s);
	}

	protected void err(int lineNo, int cmdNo, int errNo, String s) {
		_vm.err(lineNo, cmdNo, errNo, s);
	}

	protected void pln(String s) {
		_vm.pln(s);
	}

	protected int pint(String s) {
		return Integer.parseInt(s);
	}

	protected long plong(String s) {
		return Long.parseLong(s);
	}

	protected long max(long a, long b) {
		return Math.max(a, b);
	}

	protected long min(long a, long b) {
		return Math.min(a, b);
	}

}

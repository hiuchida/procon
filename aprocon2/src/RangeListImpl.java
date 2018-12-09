import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RangeListImpl extends AbstractBigArray {
	public class Range implements Comparable<Range> {
		long lt;
		long rt;
		int val;

		public Range(long lt, long rt, int val) {
			this.lt = lt;
			this.rt = rt;
			this.val = val;
		}

		public String toString() {
			return "(" + lt + ", " + rt + ", " + val + ")";
		}

		@Override
		public int compareTo(Range that) {
			return Long.compare(this.lt, that.lt);
		}
	}

	private List<Range> list;

	public RangeListImpl(VM vm, int keta) {
		super(keta, vm);
		list = new ArrayList<>();
	}

	public void ss(long idx, int val) {
		ms(idx, idx, val);
	}

	public int sg(long idx) {
		for (Range r : list) {
			if (r.lt <= idx && idx <= r.rt)
				return r.val;
		}
		return -1;
	}

	public void ms(long idx1, long idx2, int val) {
		long idxf = min(idx1, idx2);
		long idxt = max(idx1, idx2);
		delete(idxf, idxt);
		int idxlt = srchLeft(idxf);
		int idxrt = srchRight(idxt);
		if (idxlt == -1 && idxrt == Integer.MAX_VALUE) {
			insert(idxf, idxt, val);
		} else if (idxlt != idxrt) {
			if (idxlt != -1) {
				Range rlt = list.get(idxlt);
				rlt.rt = idxf - 1;
			}
			if (idxrt != Integer.MAX_VALUE) {
				Range rrt = list.get(idxrt);
				rrt.lt = idxt + 1;
			}
			insert(idxf, idxt, val);
		} else {
			Range r = list.get(idxlt);
			list.remove(idxlt);
			insert(r.lt, idxf - 1, r.val);
			insert(idxt + 1, r.rt, r.val);
			insert(idxf, idxt, val);
		}
		sortRangeList();
	}

	public long ed() {
		long c = 0;
		for (int i = 0; i < list.size(); i++) {
			Range r = list.get(i);
			if (r.val >= 0)
				c += r.rt - r.lt + 1;
		}
		return c;
	}

	private void insert(long idxf, long idxt, int val) {
		if (val >= 0) {
			Range r = new Range(idxf, idxt, val);
			list.add(r);
		}
	}

	private void delete(long idxf, long idxt) {
		for (int i = 0; i < list.size(); i++) {
			Range r = list.get(i);
			if (idxf <= r.lt && r.rt <= idxt) {
				list.remove(i);
				i--;
			}
		}
	}

	private int srchLeft(long idxf) {
		for (int i = 0; i < list.size(); i++) {
			Range r = list.get(i);
			if (r.lt <= idxf && idxf <= r.rt)
				return i;
		}
		return -1;
	}

	private int srchRight(long idxt) {
		for (int i = list.size() - 1; i >= 0; i--) {
			Range r = list.get(i);
			if (r.lt <= idxt && idxt <= r.rt)
				return i;
		}
		return Integer.MAX_VALUE;
	}

	private void sortRangeList() {
		Collections.sort(list);
		for (int i = 0; i < list.size() - 1; i++) {
			Range r1 = list.get(i);
			Range r2 = list.get(i + 1);
			if (r1.rt >= r2.lt) {
				printRangeList();
				throw new RuntimeException("Internal Error");
			}
			if (r1.rt + 1 == r2.lt && r1.val == r2.val) {
				r1.rt = r2.rt;
				list.remove(i + 1);
				i--;
			}
		}
	}

	private void printRangeList() {
		pln(list.toString());
	}

}

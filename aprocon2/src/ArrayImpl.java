import java.util.Arrays;

public class ArrayImpl extends AbstractBigArray {
	private byte[] buf;

	public ArrayImpl(VM vm, int keta) {
		super(keta, vm);
		int max = 1;
		while (keta-- > 0)
			max *= 10;
		buf = new byte[max];
		Arrays.fill(buf, (byte) -1);
	}

	public void ss(long idx, int val) {
		buf[(int) idx] = (byte) val;
	}

	public int sg(long idx) {
		return buf[(int) idx];
	}

	public void ms(long idx1, long idx2, int val) {
		long idxf = min(idx1, idx2);
		long idxt = max(idx1, idx2);
		for (int i = (int) idxf; i <= idxt; i++)
			ss(i, val);
	}

	public long ed() {
		long c = 0;
		for (long i = 0; i < buf.length; i++) {
			if (sg(i) >= 0)
				c++;
		}
		return c;
	}

}

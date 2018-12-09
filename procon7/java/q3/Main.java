import java.util.BitSet;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Main {
	public static final long VALUE_MAX = 100000L;
	public static final int TRY_MAX = 100;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt();
		int b = sc.nextInt();
		System.out.println(solve(a, b));
	}

	public static int solve(int from, int to) {
		if (from == to) {
			return 0;
		}
		BitSet memo = new BitSet();
		Set<Integer> s1 = new HashSet<>();
		s1.add(from);
		for (int i = 1; i <= TRY_MAX; i++) {
			Set<Integer> s2 = new HashSet<>();
			for (int num : s1) {
				if (memo.get(num)) {
					continue;
				}
				memo.set(num);
				long x = num * 2L;
				long y = num / 2L;
				long z = num + 1L;
				if (x == to || y == to || z == to) {
					return i;
				}
				for (long val : new long[] { x, y, z }) {
					if (val <= VALUE_MAX) {
						s2.add((int) val);
					}
				}
			}
			s1 = s2;
		}
		return -1;
	}
}

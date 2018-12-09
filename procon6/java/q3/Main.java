import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		long S = System.currentTimeMillis();
		try {
			solve();
		} finally {
			long G = System.currentTimeMillis();
			System.err.println((G - S) + "ms");
		}
	}

	private static void solve() {
		Scanner sc = new Scanner(System.in);
		int A = sc.nextInt();
		sc.nextLine();
		List<String> x = readList(sc, A);
		int B = sc.nextInt();
		sc.nextLine();
		List<String> y = readList(sc, B);
		int C = sc.nextInt();
		sc.nextLine();
		List<String> z = readList(sc, C);
		int K = sc.nextInt();
		sc.nextLine();
		String S = sc.nextLine();
		List<String> s = split(S, x);
		String T = sc.nextLine();
		List<String> t = split(T, y);

		int decS = toDecimal(s, A, x);
		int decT = toDecimal(t, B, y);

		int sum = decS + decT;

		StringBuilder U = new StringBuilder();
		while (sum > 0) {
			U.insert(0, z.get((int) (sum % C)));
			sum /= C;
		}
		int cWidth = z.get(0).length();
		while (U.length() < K * cWidth) {
			U.insert(0, z.get(0));
		}
		System.out.println(U);
	}

	private static int toDecimal(List<String> tokens, int n, List<String> list) {
		int res = 0;
		for (String token : tokens) {
			int idx = list.indexOf(token);
			res = res * n + idx;
		}
		return res;
	}

	private static List<String> split(String str, List<String> list) {
		List<String> res = new ArrayList<>();
		int len = list.get(0).length();
		for (int i = 0; i < str.length(); i += len) {
			res.add(str.substring(i, i + len));
		}
		return res;
	}

	private static List<String> readList(Scanner sc, int n) {
		List<String> l = new ArrayList<>();
		for (int i = 0; i < n; ++i) {
			l.add(sc.next());
		}
		sc.nextLine();
		return l;
	}
}

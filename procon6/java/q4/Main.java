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
		long A = sc.nextLong();
		long B = sc.nextLong();
		long K = sc.nextLong();

		long a = (A >= 0 ? (A + K - 1) / K : A / K);
		long b = (B >= 0 ? (B + K - 1) / K : B / K);
		long ans = K * ((b - 1) * b / 2 - (a - 1) * a / 2);
		System.out.println(ans);
	}
}

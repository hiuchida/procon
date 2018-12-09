import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int N = sc.nextInt();
		int K = sc.nextInt();
		int[] a = new int[N];
		for (int i = 0; i < N; ++i) {
			a[i] = sc.nextInt();
		}

		int left = -1;
		int right = Integer.MAX_VALUE / 2;
		while (right - left > 1) {
			int x = (left + right) / 2;

			int sum = 0;
			int numIntervals = 1;
			boolean existBigger = false;
			for (int i = 0; i < N; ++i) {
				if (a[i] > x) {
					existBigger = true;
					break;
				}
				if (sum + a[i] > x) {
					++numIntervals;
					sum = 0;
				}
				sum += a[i];
			}
			if (!existBigger && numIntervals <= K) {
				right = x;
			} else {
				left = x;
			}
		}

		System.out.println(right);
	}
}

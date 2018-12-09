import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		long S = System.currentTimeMillis();
		try {
			solve();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			long G = System.currentTimeMillis();
			System.err.println((G - S) + "ms");
		}
	}

	private static void solve() throws IOException {
		Scanner sc = new Scanner(System.in);
		String str = sc.nextLine();
		Deque<Character> q = new ArrayDeque<>();
		for (int i = 0; i < str.length(); ++i) {
			char ch = str.charAt(i);
			if (q.peek() == null) {
				q.push(ch);
			} else {
				char top = q.peek();
				if (top == 'G' && ch == 'C' || top == 'C' && ch == 'P' || top == 'P' && ch == 'G') {
					q.pop();
				} else {
					q.push(ch);
				}
			}
		}
		System.out.println(q.size());
	}
}

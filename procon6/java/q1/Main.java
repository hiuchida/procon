import java.io.IOException;
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
		char gengo = str.charAt(0);
		int jYear = Integer.parseInt(str.substring(1));
		int year;
		switch (gengo) {
			case 'M':
				year = 1868 + jYear - 1;
				break;
			case 'T':
				year = 1912 + jYear - 1;
				break;
			case 'S':
				year = 1926 + jYear - 1;
				break;
			case 'H':
				year = 1989 + jYear - 1;
				break;
			case 'X':
				year = 2019 + jYear - 1;
				break;
			default:
				throw new RuntimeException("Invalid gengo '" + gengo + "'.");
		}
		System.out.println(year);
	}
}

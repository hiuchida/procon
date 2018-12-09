import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	static Map<String, String> map = new HashMap<>();

	static {
		map.put("S", "ABCHJK");
		map.put("L", "ABCDEFGHJK");
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String x = sc.next();
		int a = sc.nextInt();
		int b = sc.nextInt();
		char col = map.get(x).charAt(b - 1);
		System.out.println("" + a + col);
	}
}

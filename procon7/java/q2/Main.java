import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	static Map<Character, Integer> map = new HashMap<>();

	static {
		map.put('I', 1);
		map.put('V', 5);
		map.put('X', 10);
		map.put('L', 50);
		map.put('C', 100);
		map.put('D', 500);
		map.put('M', 1000);
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		String s = sc.nextLine();
		int prev = 0;
		int ans = 0;
		int sign = 1;
		for (int i = s.length() - 1; i >= 0; --i) {
			char ch = s.charAt(i);
			int num = map.get(ch);
			if (num > prev) {
				sign = 1;
			} else if (num < prev) {
				sign = -1;
			}
			ans += num * sign;
			prev = num;
		}
		System.out.println(ans);
	}
}

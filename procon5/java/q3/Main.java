import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = br.readLine();
		int n = Integer.parseInt(line);
		line = br.readLine();
		String[] c = line.split(" ");

		HashMap<String,Integer> map = new HashMap<String,Integer>();
		for (int i = 0; i < n; i++) {
			Integer cnt = map.get(c[i]);
			if (cnt == null) {
				map.put(c[i], new Integer(1));
			} else {
				map.put(c[i], new Integer(cnt.intValue() + 1));
			}
			String ret = check(map, n, i);
			if (ret != null) {
				System.out.println(ret);
				return;
			}
		}
		System.out.println("TIE");
	}

	private static String check(HashMap<String,Integer> map, int n, int i) {
		List<Map.Entry<String,Integer>> entries = 
				new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String,Integer>>() {
			public int compare(Entry<String,Integer> entry1, Entry<String,Integer> entry2) {
				return ((Integer)entry2.getValue()).compareTo((Integer)entry1.getValue());
			}
		});
		Entry<String,Integer> top1 = entries.get(0);
		Entry<String,Integer> top2 = null;
		if (entries.size() == 1) {
			if (i + 1 > n / 2) {
				return top1.getKey() + " " + (i + 1);
			}
		} else {
			top2 = entries.get(1);
			if (top1.getValue().intValue() >= top2.getValue().intValue() + (n - i)) {
				return top1.getKey() + " " + (i + 1);
			}
		}
		return null;
	}

}

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
class key_rec {
	public int no;
	public char flag;
 
	public key_rec(String k) {
		no = k.charAt(0) - '1';
		flag = k.charAt(1);
	}
}
 
class MyRecComparator implements Comparator<String[]> {
	public List<key_rec> key;
 
	public MyRecComparator(String[] k) {
		key = new ArrayList<key_rec>();
		for (String i : k) {
			key.add(new key_rec(i));
		}
	}
 
	public int compare(String[] obj1, String[] obj2) {
		for (key_rec k : key) {
			int c = obj1[k.no].compareTo(obj2[k.no]);
			if (c != 0 ) {
				if (k.flag == 'A') {
					return c;
				} else {
					return c * -1;
				}
			}
		}
		return 0;
	}
}
 
public class Main {
 
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
		String sort_key = br.readLine();
		String[] keys = sort_key.split(" ");
 
		String line;
		List<String[]> mt_file = new ArrayList<String[]>();
		while ((line = br.readLine()) != null) {
			mt_file.add(line.split(" "));
		}
 
		Collections.sort(mt_file, new MyRecComparator(keys));
 
		PrintWriter pw = new PrintWriter(System.out);
		for (String[] r : mt_file) {
			pr_rec(pw, r);
		}
		pw.flush();
	}
 
	private static void pr_rec(PrintWriter pw, String[] n) {
		for (int i = 0 ; i < n.length ; i++) {
			if (i != 0) {
				pw.print(" ");
			}
			pw.print(n[i]);
		}
		pw.println();
	}
}

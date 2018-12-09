import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
class Rec {
	public int no;
	public String line;
	public String sort;
	
	public Rec(String[] keys, int no, String line) {
		this.no = no;
		this.line = line;
		String[] data = line.split(" ");
		StringBuilder sb = new StringBuilder();
		for (String k : keys) {
			int i = k.charAt(0) - '1';
			if (k.charAt(1) == 'A')
				sb.append(String.format("%-30s", data[i]));
			else
				sb.append(String.format("%-30s", reverse(data[i])));
		}
		this.sort = sb.toString();
	}
	
	private String reverse(String s) {
		StringBuilder sb = new StringBuilder();
		for (char ch : s.toCharArray()) {
			sb.append('Z' - ch + 'A');
		}
		return sb.toString();
	}
}
 
class MyRecComparator implements Comparator<Rec> {
	public int compare(Rec obj1, Rec obj2) {
		return obj1.sort.compareTo(obj2.sort);
	}
}
 
public class Main {
 
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
		String sort_key = br.readLine();
		String[] keys = sort_key.split(" ");
 
		String line;
		List<Rec> mt_file = new ArrayList<Rec>();
		for (int i = 0 ; (line = br.readLine()) != null ; i++) {
			Rec r = new Rec(keys, i, line);
			mt_file.add(r);
		}
 
		Collections.sort(mt_file, new MyRecComparator());
 
		PrintWriter pw = new PrintWriter(System.out);
		for (Rec r : mt_file) {
			pw.println(r.line);
		}
		pw.flush();
	}
 
}

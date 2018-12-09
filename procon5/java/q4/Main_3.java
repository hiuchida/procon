import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
 
public class Main {
 
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
		String line = br.readLine();
		int N = Integer.parseInt(line);
 
		List<String[]> tr_file = new ArrayList<String[]>();
		for (int i = 0 ; i < N ; i++) {
			line = br.readLine();
			String[] c = line.split(" ");
			tr_file.add(c);
		}
 
		SortedMap<String,String[]> mt_file = new TreeMap<String,String[]>();
		while ((line = br.readLine()) != null) {
			String[] c = line.split(" ");
			mt_file.put(c[0], c);
		}
 
		List<String[]> reject = new ArrayList<String[]>();
		for (Iterator<String[]> i = tr_file.iterator(); i.hasNext();) {
			String[] c = i.next();
			if (mt_file.get(c[1]) != null) {
				if (c[0].equals("U")) {
					mt_file.get(c[1])[1] = c[2];
					mt_file.get(c[1])[2] = c[3];
				} else if (c[0].equals("D")) {
					mt_file.remove(c[1]);
				} else {
					reject.add(c);
				}
			} else {
				if (c[0].equals("I")) {
					String[] d = new String[3];
					d[0] = c[1];
					d[1] = c[2];
					d[2] = c[3];
					mt_file.put(c[1], d);
				} else {
					reject.add(c);
				}
			}
		}
 
		Collection<String[]> mt_file_ite = mt_file.values();
		PrintWriter pw = new PrintWriter(System.out);
		for (Iterator<String[]> i = mt_file_ite.iterator(); i.hasNext();) {
			pr_rec(pw, i.next());
		}
 
		for (Iterator<String[]> i = reject.iterator(); i.hasNext();) {
			pr_rec(pw, i.next());
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


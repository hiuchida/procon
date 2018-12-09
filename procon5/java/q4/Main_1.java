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
 
		List<String> tr_file = new ArrayList<String>();
		for (int i = 0 ; i < N ; i++) {
			line = br.readLine();
			tr_file.add(line);
		}
 
		SortedMap<String,String> mt_file = new TreeMap<String,String>();
		while ((line = br.readLine()) != null) {
			String[] c = line.split(" ");
			mt_file.put(c[0], line);
		}
 
		List<String> reject = new ArrayList<String>();
		for (Iterator<String> i = tr_file.iterator(); i.hasNext();) {
			String tr = i.next();
			String[] c = tr.split(" ");
			if (mt_file.get(c[1]) != null) {
				if (c[0].equals("U")) {
					mt_file.put(c[1], tr.substring(2));
				} else if (c[0].equals("D")) {
					mt_file.remove(c[1]);
				} else {
					reject.add(tr);
				}
			} else {
				if (c[0].equals("I")) {
					mt_file.put(c[1], tr.substring(2));
				} else {
					reject.add(tr);
				}
			}
		}

		Collection<String> mt_file_ite = mt_file.values();
		PrintWriter pw = new PrintWriter(System.out);
		for (Iterator<String> i = mt_file_ite.iterator(); i.hasNext();) {
			pw.println(i.next());
		}

		for (Iterator<String> i = reject.iterator(); i.hasNext();) {
			pw.println(i.next());
		}
		pw.flush();
	}
 
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static boolean _bElapsed = true;

	public static void main(String[] args) throws IOException {
		long start = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter pw = new PrintWriter(System.out);

		List<String> list = new ArrayList<>();
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			list.add(line);
		}
		VM vm = new VM(list, pw);
		vm.execute();

		pw.flush();
		long end = System.currentTimeMillis();
		if (_bElapsed)
			System.err.println((end - start) + "ms");
	}
}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FixED {
	public static void main(String[] args) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				int idx = line.indexOf(" I0700 0");
				int len = line.length();
				if (idx == 5 && idx + 8 < len) {
					System.err.println("FixED: " + line);
					while (line.length() > 13 && line.charAt(12) == '0') {
						line = line.substring(0, 12) + line.substring(13);
					}
				}
				System.out.println(line);
			}
		} finally {
		}
	}

}

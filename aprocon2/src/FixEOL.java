import java.io.BufferedInputStream;
import java.io.IOException;

public class FixEOL {
	public static void main(String[] args) throws IOException {
		int pre = 0;
		boolean bFound = false;
		try (BufferedInputStream bis = new BufferedInputStream(System.in)) {
			while (true) {
				int b = bis.read();
				if (b < 0)
					break;
				if (pre != '\r' && b == '\n') {
					System.out.println();
					bFound = true;
				} else
					System.out.print((char) b);
				pre = b;
			}
		} finally {
		}
		if (bFound)
			System.err.println("FixEOL found LF");
	}

}

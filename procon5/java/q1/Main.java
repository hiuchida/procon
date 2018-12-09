import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
 
public class Main {
 
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
		try {
 
			String in1 = br.readLine();
			String first = "";
			String center = "";
			String last = "";
			String out = in1;
 
			int len = in1.length();
			int hlen = len / 2;
			if(len > 3){
				first = in1.substring(0,hlen);
				int mod = len % 2;
				if(mod > 0){
					center = in1.substring(hlen,hlen+1);
					last = in1.substring(hlen + 1);
				}else{
					last = in1.substring(hlen);
				}
				if(first.length() > 1){
					first = first.substring(1) + first.substring(0, 1);
				}
				int lastLen = last.length();
				if(lastLen > 1){
					last = last.substring(lastLen-1) + last.substring(0, lastLen-1);
				}
				out = first+center+last;
			}
 
			System.out.println(out);
 
		} catch (Exception e) {
			System.exit(0);
		}
    }
}
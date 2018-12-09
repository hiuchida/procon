import java.io.*;
import java.util.*;
 
public class Main {
	public static Scanner scanner = new Scanner(System.in);
 
    public static void main(String[] args) throws IOException {
        List<String> inputs = scanLineNTimes(2);
 
        String progression = inputs.get(1);
        int n = Integer.parseInt(inputs.get(0));
 
        List<Long> results = method1(progression, n);
        for(int i = 0; i < results.size(); i++) {
            System.out.println(results.get(i));
        }
    }
 
    private static List<Long> method1(String progression, int n) {
        List<Long> results = new ArrayList<>();
        do {
            Object[] result = search(progression, n);
            if(result != null) {
                progression = (String) result[0];
                results.add(Long.parseLong((String) result[1]));
            } else {
                progression = "";
            }
        } while(progression.length() > n);
        if(progression.length() > 0) {
            results.add(Long.parseLong(progression));
        }
        return results;
    }
 
    private static Object[] search(String progression, int n) {
        for(int i = 9; i > 0; i--) {
            List<String> list = new ArrayList<>();
            int index = progression.length() - n;
            while ((index = progression.lastIndexOf(48 + i, index)) > -1) {
                list.add(progression.substring(index, index + n));
                index--;
            }
            if(list.size() == 0) continue;
            list.sort(new Comparator<String>() {
                @Override
                public int compare(String o1, String o2) {
                    Long num1 = new Long(o1);
                    Long num2 = new Long(o2);
                    return num2.compareTo(num1);
                }
            });
            StringBuilder sb = new StringBuilder(progression);
            sb.delete(progression.indexOf(list.get(0)), progression.indexOf(list.get(0)) + list.get(0).length());
            return new Object[]{sb.toString(), list.get(0)};
        }
        return new Object[]{"", new Long(progression).toString()};
    }
 
    public static List<String> scanLineNTimes(int n) {
        List<String> lines = new ArrayList<String>();
        for(int i = 0; i < n; i++){
            lines.add(scanner.nextLine());
        }
        return lines;
    }
}
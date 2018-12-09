import java.util.*;

public class Main {
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        List<String> inputs = scanLineNTimes(2);
        List<Object[]> entries = new ArrayList<>();
        for (String pair : inputs.get(0).split(",")) {
            String[] temp = pair.split("\\|");
            Object[] entry = new Object[]{temp[0], Integer.parseInt(temp[1])};
            entries.add(entry);
        }

        entries.sort((o1, o2) -> {
            Integer val1 = (Integer) o1[1];
            Integer val2 = (Integer) o2[1];
            return val2.compareTo(val1);
        });

        String str = inputs.get(1);
        List<Integer[]> matches = new ArrayList<>();
        int value = 0;
        for (Object[] entry : entries) {
            String word = (String) entry[0];
            Integer wordValue = (Integer) entry[1];

            int idx;
            int prevIdx = -1;
            while ((idx = str.indexOf(word, prevIdx + 1)) > -1) {
                prevIdx = idx;
                final int temp = idx;
                if (matches.stream().anyMatch(match -> ((temp + word.length() - 1 >= match[0]) && (temp + word.length() - 1 <= match[0] + match[1] - 1)) ||
                        (temp >= match[0]) && (temp <= match[0] + match[1] - 1))) {
                    continue;
                }
                value += wordValue;
                matches.add(new Integer[]{idx, word.length()});
            }
        }

        System.out.println(value);
    }

    public static List<String> scanLineNTimes(int n) {
        List<String> lines = new ArrayList<String>();
        for (int i = 0; i < n; i++) {
            lines.add(scanner.nextLine());
        }
        return lines;
    }
}

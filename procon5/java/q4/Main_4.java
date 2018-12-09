import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Main {

	static final Boolean debugFlag = false;
	static final Boolean stopWatch = false;

	public static void main(String[] args) throws IOException {
		if (debugFlag) {
			System.setIn(Main.class.getResourceAsStream("test.txt"));
		}

		ProconScan proScan = new ProconScan();
		Integer tranNum = proScan.scanInteger1();
		List<TransactionData> tranList = new ArrayList<>(tranNum);
		List<TransactionData> masterRecord = new ArrayList<>(300000);
		HashMap<String, Integer> masterMap = new HashMap<>();

		long start = System.currentTimeMillis();
		List<Long> timeEndList = new ArrayList<>();

		for (int tranCnt = 0; tranCnt < tranNum; tranCnt++) {
			proScan.scan();
			tranList.add(new TransactionData(proScan.nextString(), proScan.nextString(), proScan.nextString(),
					proScan.nextString(), true, false));
		}

		if (stopWatch) { //1
			timeEndList.add(System.currentTimeMillis());
		}

		Integer tranSize = tranList.size();
		TransactionData tranData;
		String[] buffer;
		Integer masterCnt = 0;

		while (proScan.scan() != null) {
			buffer = proScan.getStringList();
			masterRecord.add(new TransactionData("", buffer[0], buffer[1], buffer[2], false, false));
			masterMap.put(buffer[0], masterCnt);
			masterCnt++;
		}

		if (stopWatch) { //2
			timeEndList.add(System.currentTimeMillis());
		}
		Integer selectNum;
		String typeStr;
		for (int cnt = 0; cnt < tranSize; cnt++) {
			tranData = tranList.get(cnt);
			if ((selectNum = masterMap.get(tranData.getCode())) != null) {
				typeStr = tranData.getType();
				switch (typeStr) {
				case "D":
					if (!masterRecord.get(selectNum).getFlag()) {
						tranData.setReject(false);
						masterRecord.get(selectNum).setFlag(true);
					}
					break;
				case "U":
					if (!masterRecord.get(selectNum).getFlag()) {
						tranData.setReject(false);
						masterRecord.set(selectNum, tranData);
					}
					break;
				case "I":
					if (masterRecord.get(selectNum).getFlag()) {
						tranData.setReject(false);
						masterRecord.set(selectNum, tranData);
					}
					break;
				default:
					break;
				}
			} else {
				typeStr = tranData.getType();
				switch (typeStr) {
				case "I":
					masterRecord.add(tranData);
					masterMap.put(tranData.getCode(), masterCnt);
					masterCnt++;
					tranData.setReject(false);
					break;
				default:
					break;
				}
			}
		}

		if (stopWatch) { //3
			timeEndList.add(System.currentTimeMillis());
		}

		masterRecord.sort(new Comparator<TransactionData>() {
			@Override
			public int compare(TransactionData t1, TransactionData t2) {
				Long num1 = Long.valueOf(t1.getCode());
				Long num2 = Long.valueOf(t2.getCode());
				return num1.compareTo(num2);
			}
		});

		if (stopWatch) {
			timeEndList.add(System.currentTimeMillis());
		}
		PrintWriter out = new PrintWriter(System.out);
		StringBuilder sBuilder = new StringBuilder();
		masterRecord.forEach(elemnt -> {
			if (!elemnt.getFlag()) {
				sBuilder.setLength(0);
				sBuilder.append(elemnt.getCode());
				sBuilder.append(" ");
				sBuilder.append(elemnt.getName());
				sBuilder.append(" ");
				sBuilder.append(elemnt.getPrice());
				out.println(sBuilder);
			}
		});

		if (stopWatch) {
			timeEndList.add(System.currentTimeMillis());
		}

		for (int cnt = 0; cnt < tranList.size(); cnt++) {
			tranData = tranList.get(cnt);
			if (tranData.getReject()) {
				sBuilder.setLength(0);
				sBuilder.append(tranData.getType());
				sBuilder.append(" ");
				sBuilder.append(tranData.getCode());
				sBuilder.append(" ");
				sBuilder.append(tranData.getName());
				sBuilder.append(" ");
				sBuilder.append(tranData.getPrice());
				out.println(sBuilder);
			}
		}
		out.flush();

		if (stopWatch) {
			timeEndList.forEach(Element -> {
				System.out.println(Element - start + "ms");
			});
		}
	}
}

class TransactionData {
	private String type;
	private String code;
	private String name;
	private String price;
	private Boolean reject;
	private Boolean flag;

	public TransactionData(String type, String code, String name, String price, Boolean reject, Boolean flag) {
		super();
		this.type = type;
		this.code = code;
		this.name = name;
		this.price = price;
		this.reject = reject;
		this.flag = flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Boolean getReject() {
		return reject;
	}

	public void setReject(Boolean reject) {
		this.reject = reject;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

}

class ProconScan {
	private BufferedReader br;
	private String inLine;
	private String[] inString;
	private Integer idx;
	private String splitChar;
	private InputStream is;

	public ProconScan() {
		br = new BufferedReader(new InputStreamReader(System.in));
		idx = 0;
		splitChar = " ";
	}

	public ProconScan(String split) {
		br = new BufferedReader(new InputStreamReader(System.in));
		idx = 0;
		splitChar = split;
	}

	public void proconEnd() throws IOException {
		br.close();
	}

	public String scan() throws IOException {
		idx = 0;
		inLine = br.readLine();
		if (inLine == null) {
			return null;
		}
		inString = inLine.split(splitChar, 0);
		return inLine;
	}

	public void scanByteStart() {
		is = System.in;
	}

	public int nextByte() throws IOException {
		return is.read();
	}

	public String nextLine() throws IOException {
		inLine = br.readLine();
		return inLine;
	}

	public Integer scanInteger1() throws IOException {
		scan();
		return Integer.parseInt(inString[0]);
	}

	public Float scanFloat1() throws IOException {
		scan();
		return Float.parseFloat(inString[0]);
	}

	public Double scanDouble1() throws IOException {
		scan();
		return Double.parseDouble(inString[0]);
	}

	public long scanLong1() throws IOException {
		scan();
		return Long.parseLong(inString[0]);
	}

	public String scanString1() throws IOException {
		scan();
		return inString[0];
	}

	public String backLine() {
		return inLine;
	}

	public String nextString() {
		return inString[idx++];
	}

	public String[] getStringList() {
		return inString;
	}

	public Integer nextInteger() {
		Integer num;
		try {
			num = Integer.parseInt(inString[idx++]);
		} catch (NumberFormatException e) {
			num = null;
		}
		return num;
	}

	public Float nextFloat() {
		Float num;
		try {
			num = Float.parseFloat(inString[idx++]);
		} catch (NumberFormatException e) {
			num = null;
		}
		return num;
	}

	public Double nextDouble() {
		Double num;
		try {
			num = Double.parseDouble(inString[idx++]);
		} catch (NumberFormatException e) {
			num = null;
		}
		return num;
	}

	public Float[] getFloat() {
		Float[] num = new Float[this.getLen()];

		for (int i = 0; i < this.getLen(); i++) {
			num[i] = Float.parseFloat(inString[i]);
		}

		return num;
	}

	public Integer getLen() {
		return inString.length;
	}

	public int[] getIntList() {
		int[] num = new int[this.getLen()];

		for (int i = 0; i < this.getLen(); i++) {
			num[i] = Integer.parseInt(inString[i]);
		}

		return num;
	}

	public long[] getLongList() {
		long[] num = new long[this.getLen()];

		for (int i = 0; i < this.getLen(); i++) {
			num[i] = Long.parseLong(inString[i]);
		}

		return num;
	}

	public double[] getDoubleList() {
		double[] num = new double[this.getLen()];

		for (int i = 0; i < this.getLen(); i++) {
			num[i] = Double.parseDouble(inString[i]);
		}

		return num;
	}

}
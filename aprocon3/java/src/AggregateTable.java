import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregateTable {
	class Record {
		Aggregate ope;
		String[] pks;
		private int numval;
		private String strval;
		
		Record(Aggregate ope, String[] pks) {
			this.ope = ope;
			this.pks = pks;
		}
		
		void add(String val) {
			switch (ope) {
			case COUNT:
				count(val);
				break;
			case MAX:
				max(val);
				break;
			case MIN:
				min(val);
				break;
			default:
				throw new RuntimeException("SummaryTable.add: unknown ope=" + ope);
			}
		}

		private void count(String val) {
			this.numval += val.length() > 0 ? 1 : 0;
		}

		private void max(String val) {
			if (this.strval == null) {
				this.strval = val;
			}
			if (this.compare(val) < 0 ) {
				this.strval = val;
			}
		}
		
		private void min(String val) {
			if (this.strval == null) {
				this.strval = val;
			}
			if (this.strval.length() == 0 || this.compare(val) > 0 ) {
				this.strval = val;
			}
		}
		
		String getVal() {
			switch (ope) {
			case COUNT:
				return Integer.toString(this.numval);
			case MAX:
			case MIN:
				return this.strval;
			default:
				throw new RuntimeException("SummaryTable.getVal: unknown ope=" + ope);
			}
		}
		
		private int compare(String val) {
			if (val.length() > 0) {
				return this.strval.compareTo(val);
			} else {
				return 0;
			}
		}
	}

	private List<Record> list = new ArrayList<>();
	private Map<String,Integer> map = new HashMap<>();
	
	public int getMaxRow() {
		return list.size();
	}
	
	public List<Record> getList() {
		return list;
	}
	
	public void add(Aggregate ope, String[] pks, String val) {
		String key = AnswerUtil.getCompositeKey(pks);
		Integer idx = map.get(key);
		if (idx == null) {
			list.add(new Record(ope, pks));
			idx = list.size() - 1;
			map.put(key, idx);
		}
		list.get(idx).add(val);
	}
	
}

import java.util.HashMap;
import java.util.Map;

public class TableManager {
	private static TableManager singleton = new TableManager();

	public static TableManager getInstance() {
		return singleton;
	}

	private Map<String, Table> map = new HashMap<>();

	public Table get(String name) {
		Table tbl = map.get(name);
		if (tbl == null)
			throw new RuntimeException("TableManager.get: name=" + name);
		return tbl;
	}

	public void set(String name, Table tbl) {
		map.put(name, tbl);
	}

}

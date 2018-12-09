public interface BigArrayIF {
	public boolean validate(int lineNo, String line, int cmdNo, String[] cols);

	public String execute(int lineNo, String line, int cmdNo, String[] cols);

	public void ss(long idx, int val);

	public void sr(long idx);

	public int sg(long idx);

	public void ms(long idxf, long idxt, int val);

	public void mr(long idxf, long idxt);

	public int[] mg(long idxf, long idxt);

	public long ed();

}

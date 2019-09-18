package tester;

public interface ScannerInterface {
	/**
	 * @return int from input
	 */
	public int getInt();
	
	/**
	 * @param min
	 * @return int from input greater then min
	 */
	public int getInt(int min);
	
	/**
	 * @return double from input
	 */
	public double getDouble();
	
	/**
	 * @param min
	 * @return double from input greater then min
	 */
	public double getDouble(double min);
	
	
}

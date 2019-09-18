package tester;

/**
 * @File DumbScanner.java
 * @Course JAC 444 SAB
 * @Author Vincent Terpstra
 * @ID     140665175
 * @Date   Jan 20, 2019
 * @Purpose Workshop X Task X
 * @Class DumbScanner
 * 		Fakes user input by returning values from an array of doubles
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */
public class DumbScanner implements ScannerInterface {
	DumbScanner(double[] inputs){
		this.inputs = inputs;
	}
	
	int idx = 0;
	double[] inputs;
	
	@Override
	public int getInt(int min){
		return getInt();
	}
	
	@Override
	public int getInt(){
		if(idx < inputs.length){
			System.out.println((int)inputs[idx]);
			return (int)inputs[idx++];
		} return 0;
	}
	
	@Override
	public double getDouble(double min){
		return getDouble();
	}
	@Override
	public double getDouble(){
		if(idx < inputs.length){
			System.out.println(inputs[idx]);
			return inputs[idx++];
		} return 0;
	}
}

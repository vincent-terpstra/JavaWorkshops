package matrixAddition;

/**
 * @File	Matrix.java
 * @Course	JAC 444 SAB
 * @Author	Vincent Terpstra
 * @ID     	140665175
 * @Date   	March 28, 2019
 * @Purpose Workshop 7 Task 2
 * @Class Matrix.java 
 * 		Implements Matrix addition using serial and parallel methods
 * @Declaration This assignment represents my own work in accordance with Seneca Academic Policy
 */

public class Matrix {
	/**
	 * Add the two Matrices in a serial algorithm
	 * @param mat1
	 * @param mat2
	 * @return mat1 + mat2
	 * @throws ArithmeticException
	 */
	public static double[][] serialAddMatrix(double[][] mat1, double[][] mat2) throws ArithmeticException {
		int rowLength = mat1.length;
		int colLength = mat1[0].length;
		if(mat2.length != rowLength || mat2[0].length != colLength)
			throw new ArithmeticException("Matrices must be equal sizes");
		double[][] result = new double[rowLength][colLength];
		for(int row = 0; row < rowLength; ++row)
			for(int col = 0; col < colLength; ++col)
				result[row][col] = mat1[row][col] + mat2[row][col];
		
		return result;
	}
	
	private static int rowIdx;
	private static synchronized int nextIdx(){
		return rowIdx++;
	}
	/**
	 * Add the two Matrices using threads
	 * @param mat1
	 * @param mat2
	 * @return mat1 + mat2
	 * @throws ArithmeticException
	 */
	public static double[][] parallelAddMatrix(final double[][] mat1, final double[][] mat2) throws ArithmeticException {
		int rowLength = mat1.length;
		int colLength = mat1[0].length;
		if(mat2.length != rowLength || mat2[0].length != colLength)
			throw new ArithmeticException("Matrices must be equal sizes");
		double[][] result = new double[rowLength][colLength];
		int numThreads = 10;
		Thread[] threads = new Thread[numThreads];
			rowIdx = 0;
		for(int i = 0; i < numThreads; i++){
			threads[i]=  new Thread(){
				int threadIdx = rowIdx++;
				@Override
				public void run(){
					do {
						double[] 
							add1 = mat1[threadIdx],
							add2 = mat2[threadIdx], 
							sum = result[threadIdx];
						for(int idx = 0; idx < sum.length; ++idx)
							sum[idx] = add1[idx] + add2[idx];
						threadIdx = nextIdx();	
					} while(threadIdx < mat1.length);
				}
			};
		}
		for(Thread th : threads){
			th.setPriority(Thread.MAX_PRIORITY);
			th.start();
		}
		try {
			for(Thread th : threads)
				th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * Fill a matrix with values
	 * @param mat
	 */
	public static void fill(double[][] mat){
		for(int r = 0; r < mat.length; r++)
			for(int c = 0; c < mat.length; c++){
				mat[r][c] = r * c;
			}
	}
	/**
	 * Check if two matrix's are equal
	 * @param mat1
	 * @param mat2
	 * @return mat1 = mat2
	 */
	public static boolean compare(double[][] mat1, double[][] mat2){
		for(int r = 0; r < mat1.length; r++)
			for(int c = 0; c < mat1.length; c++)
			if(Math.abs(mat1[r][r] - mat2[r][r]) > .00001) 
				return false;
		return true;
	}
	
	static final int MATRIX_SIZE = 2000;
	
	/**
	 * Tester Main method
	 * 		times serialAdd and parallelAdd
	 * 		ensures both results are equal;
	 * @param args
	 */
	public static void main(String[] args){
		double[][] 
			mat1 = new double[MATRIX_SIZE][MATRIX_SIZE], 
			mat2 = new double[MATRIX_SIZE][MATRIX_SIZE];
		
		fill(mat1);
		fill(mat2);
		
		double[][] res1, res2;
		try {
		 
		 long time0 = System.nanoTime();
		 	res1 = serialAddMatrix(mat1, mat2);
		 long time1 = System.nanoTime();
		 System.out.println("Serial   Add " + (time1 - time0) + " nanoseconds");
		 long time2 = System.nanoTime();
		 	res2 = parallelAddMatrix(mat1, mat2);
		 long time3 = System.nanoTime();
		 System.out.println("Parallel Add " + (time3 - time2)+ " nanoseconds");
		 if(!compare(res1,res2))System.out.println("Not Equal");;
		} catch(Exception ex){}
		
		
	}
	
}

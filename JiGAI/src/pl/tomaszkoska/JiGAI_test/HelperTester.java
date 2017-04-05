package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Util.Helper;

public class HelperTester {
	public static void runTest(){
		double[][] array = new double[][]{{1,2,3,4},
			{1,2,3,42},
			{12,2,43,4},
			{11,2,3,4},
			{1,21,3,4},
			{1,2,32,4},
			{1,2,3,33}};
			Helper.saveAsCSV(array, "D:\\test\\pozdro.csv");
	}
	public static void runTest2(){
		double [][] input1 = Helper.loadDataFromCSV("D:\\test\\genetics\\input\\input_cpi.csv");
		double [][] target1 = Helper.loadDataFromCSV("D:\\test\\genetics\\input\\target_cpi.csv");
		double [][] full = Helper.loadDataFromCSV("D:\\test\\genetics\\input\\cpi.csv");
		double [][] normFull = Helper.normalizeData(full);
		double [][] target2 = Helper.splitDataset(normFull, 1,true);
		double [][] input2 = Helper.splitDataset(normFull, 1,false);


//		Helper.printDataSet(input1);
		Helper.printDataSet(input1);
//		System.out.println("\n");
//		Helper.printDataSet(full);
		System.out.println("\n");
		System.out.println("\n");
		System.out.println("\n");
		Helper.printDataSet(input2);

	}
}

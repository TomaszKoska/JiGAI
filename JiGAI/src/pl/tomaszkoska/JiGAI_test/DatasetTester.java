package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Dataset.Dataset;

public class DatasetTester {
	public static double[][] XORinput
	= new double[][]{{1,1},{0,1},{1,0},{0,0}};

	public static double[][] XORtarget =new double[][]{{0,1},
			{1,0},
			{1,0},
			{0,1}};

	public static double[][] basic3Input
			= new double[][]{{1,1,1},{1,1,0},{1,0,1},{0,1,1},{1,0,0},{0,1,0},{0,0,1},{0,0,0}};

	public static double[][] basic3target =
			new double[][]{{1,1,1},{1,1,0},{1,0,1},{0,1,1},{1,0,0},{0,1,0},{0,0,1},{0,0,0}};

	public static double[][] someTarget =
					new double[][]{{6,1,1},{5,1,0},{4,0,1},{3,1,1},{2,0,0},{1,1,0},{0,0,1},{-1,0,0}};
	public static double[][] someInput =
							new double[][]{{9,1,1},{2,1,0},{9,0,1},{7,1,1},{9,0,0},{9,1,0},{0,0,1},{9,0,0}};


	public static void runTest(){
			Dataset d = new Dataset(XORtarget,XORinput);
			d.printXs();
			d.printYs();
			d.printWeights();

			Dataset d2 = new Dataset(someTarget,someInput);
			d2.printXs();
			d2.printYs();
			d2.printWeights();
			d2.normalizeAll();

			Dataset d3 = d2.getRandomSubset(20, true);
			d3.printXs();
			d3.printYs();
			d3.printWeights();

			d3 = d2.getRandomSubset(4, false);
			d3.printXs();
			d3.printYs();
			d3.printWeights();
	}
}

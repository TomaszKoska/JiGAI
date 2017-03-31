package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Base.NeuralNet;
import pl.tomaszkoska.JiGAI_Util.Helper;


public class LearningTest {
	public static double[][] XORinput
	= new double[][]{{1,1},{0,1},{1,0},{0,0}};

			public static double[][] XORtarget =new double[][]{{0,1},
			{1,0},
			{1,0},
			{0,1}};
	public static void runTest(){
		double[][] in = XORinput;
		double[][] tar = XORtarget;
		int[] architecture = new int[]{2,8,2};
		NeuralNet nn = new NeuralNet(architecture,in[0].length);
		nn.setActivationFunction("s");
		nn.setLearningRate(0.5);
		nn.setMomentum(0);
		nn.randomizeLayers();

//		System.out.println(nn.weightsToString());
		nn.fullPredict(in, tar);
		System.out.println(nn.getRMSE()[0] + " , " + nn.getRMSE()[1] + "\n");

		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + nn.getPrediction()[j][0]
					+", " + nn.getPrediction()[j][1]);
		}

		System.out.println("\n\n\n\n");

		for(int i=0;i<10;i++){
		nn.trainOneEpoch(in, tar,true);
		}

//		System.out.println(nn.weightsToString());
		nn.fullPredict(in, tar);
		System.out.println(nn.getRMSE()[0] + " , " + nn.getRMSE()[1]+ "\n");


		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + nn.getPrediction()[j][0]
					+", " + nn.getPrediction()[j][1]);
		}



//		double[][] binded = Helper.bindDataset(tar, in);
//
//		Helper.printDataSet(binded);
//		double[][] a = Helper.splitDataset(binded, 2, true);
//		double[][] b = Helper.splitDataset(binded, 2, false);
//		Helper.printDataSet(a);
//		System.out.println("\n");
//		Helper.printDataSet(b);
//		System.out.println("\n");
//		Helper.shuffleArray(b);
//		Helper.printDataSet(b);






	}
}

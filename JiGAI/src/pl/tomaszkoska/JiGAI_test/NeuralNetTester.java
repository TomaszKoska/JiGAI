package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Base.NeuralNet;
import pl.tomaszkoska.JiGAI_Dataset.Dataset;

public class NeuralNetTester {

	public static void runTest(){

		NeuralNet nn = new NeuralNet(new int[]{2,1}, 2);
		nn.setActivationFunction("l");
		nn.randomizeLayers();

		nn.getLayers()[0].getNeurons()[0].setWeights(new double[]{0.5,0.5});
		nn.getLayers()[0].getNeurons()[0].setBias(1);

		nn.getLayers()[0].getNeurons()[1].setWeights(new double[]{-0.5,-0.5});
		nn.getLayers()[0].getNeurons()[1].setBias(1);


		nn.getLayers()[1].getNeurons()[0].setWeights(new double[]{1,-1});
		nn.getLayers()[1].getNeurons()[0].setBias(1);


		double[][] dataSet = new double[][]{
			  { 0, 0 },
			  { 1, 0 },
			  { 0, 1 },
			  { 1, 1 },
		      { 1, -1 }
			};
			double[][] targetSet = new double[][]{
				  { 0 },
				  { 0 },
				  { 0 },
				  { 1 },
			      { -1 }
				};
			Dataset d = new Dataset(targetSet,dataSet);
			nn.fullPredict(d);


		System.out.println(nn.weightsToString());
		for (int i = 0; i < nn.getPrediction().length; i++) {
			String line = "";
			for (int j = 0; j < nn.getPrediction()[0].length; j++) {
				line += "," + nn.getPrediction()[i][j];
			}
			System.out.println(line);
		}
		for (int i = 0; i < nn.getError().length; i++) {
			String line = "";
			for (int j = 0; j < nn.getError()[0].length; j++) {
				line += "," + nn.getError()[i][j];
			}
			System.out.println(line);
		}
		System.out.println("");
		double[][] sqe = nn.getSquaredError();
		for (int i = 0; i < sqe.length; i++) {
			String line = "";
			for (int j = 0; j < sqe[0].length; j++) {
				line += "," + sqe[i][j];
			}
			System.out.println(line);
		}
		double[] rmse = nn.getRMSE();
		for (int i = 0; i < rmse.length; i++) {
			System.out.println("RMSE:" + rmse[i]);
		}

	}

}

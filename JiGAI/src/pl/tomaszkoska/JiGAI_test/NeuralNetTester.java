package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Base.NeuralNet;

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
			nn.fullPredict(dataSet,targetSet);


		System.out.println(nn.weightsToString());
		for (int i = 0; i < nn.getLastForecast().length; i++) {
			String line = "";
			for (int j = 0; j < nn.getLastForecast()[0].length; j++) {
				line += "," + nn.getLastForecast()[i][j];
			}
			System.out.println(line);
		}
		for (int i = 0; i < nn.getLastError().length; i++) {
			String line = "";
			for (int j = 0; j < nn.getLastError()[0].length; j++) {
				line += "," + nn.getLastError()[i][j];
			}
			System.out.println(line);
		}
		double[][] sqe = nn.getLastSquaredError();
		for (int i = 0; i < sqe.length; i++) {
			String line = "";
			for (int j = 0; j < sqe[0].length; j++) {
				line += "," + sqe[i][j];
			}
			System.out.println(line);
		}


	}

}

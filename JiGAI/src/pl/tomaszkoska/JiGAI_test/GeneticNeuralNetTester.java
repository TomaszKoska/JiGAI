package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class GeneticNeuralNetTester {

	public static void runTest(){

		GeneticNeuralNet nn = new GeneticNeuralNet(new int[]{2,1}, 2);
		nn.setActivationFunction("l");
		nn.randomizeLayers();
		System.out.println(nn.weightsToString());
		System.out.println(nn.getGenomeString());
	}

}

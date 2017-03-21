package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class GeneticNeuralNetTester {

	public static void runTest(){

		GeneticNeuralNet nn = new GeneticNeuralNet(new int[]{2,1}, 4);
		nn.setActivationFunction("l");
		nn.randomizeLayers();
		nn.codeGenome();



		String sampleGenome = "2.0,4.0,2.0,1.0,0.7342123044524114,0.7903036086326758,0.3303737007917005,3.0,3.0,3.0,0.9973944157950192,0.4689877235183111,-0.21486359985675807,0.8479467759422936,-0.5223565232347984,-0.0627892064727884,0.49049333064310385,0.03878506483964106,0.3424630128369546,0.0723687525360559";

		String[] strGenomeArray = sampleGenome.split(",");
		double[] genomeArray = new double[strGenomeArray.length];

		for (int i = 0; i < strGenomeArray.length; i++) {
			genomeArray[i] = Double.parseDouble(strGenomeArray[i]);
		}

		GeneticNeuralNet nn2 = new GeneticNeuralNet(genomeArray);

		System.out.println(nn);
		System.out.println(nn2);

		System.out.println(nn.weightsToString());
		System.out.println(nn2.weightsToString());

		System.out.println(nn.getGenomeString());
		System.out.println(nn2.getGenomeString());

	}

}

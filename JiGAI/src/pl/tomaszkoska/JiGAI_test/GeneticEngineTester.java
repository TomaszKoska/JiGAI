package pl.tomaszkoska.JiGAI_test;


import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class GeneticEngineTester {

	public static void runTest(){
		//define class of nns
		int numberOfInputs = 2;
		int[] neuronCounts = new int[]{2,1};

		GeneticEngine ge = new GeneticEngine(numberOfInputs,neuronCounts,0.05,1);

		ge.initialize();

		for(GeneticNeuralNet gnn : ge.getPopulation()){
			System.out.println(gnn.weightsToString());
		}

	}

}

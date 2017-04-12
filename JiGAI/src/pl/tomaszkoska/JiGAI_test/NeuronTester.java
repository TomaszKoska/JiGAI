package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Base.Neuron;

public class NeuronTester {

	public static void runTest(){
		Neuron testNeuron = new Neuron(5);
		testNeuron.setActivationFunctionBehaviour(new SigmoidActivationFunction());
		testNeuron.randomizeWeights(-1,1);

		System.out.println(testNeuron);
		System.out.println(testNeuron.processInput(new double[]{1,0,1,0,1},false));
		System.out.println(testNeuron.summingFunction(new double[]{1,0,0,0,1}));
		testNeuron.updateWeights(0.1,new double[]{0,0,0,0,-1});
		System.out.println(testNeuron);
	}
}

package pl.tomaszkoska.JiGAI_test;

import pl.tomaszkoska.JiGAI_Base.Neuron;

public class LearningTest {

	public static void runTest(){

		Neuron n = new Neuron(new double[]{0.5,1,1.5});
		n.setActivationFunction("s");
		System.out.println(n.processInput(new double[]{1,1,1}));
		System.out.println(n.getLastInput()[2]);
	}
}

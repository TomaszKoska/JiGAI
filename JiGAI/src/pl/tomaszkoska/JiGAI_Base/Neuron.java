package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_Activation.ActivationFunctionBehaviour;

public class Neuron {
/*If there is more data in data set than in neuron
 * (eg in neuron 5 weights, and 10 variables in dataset
 * neuron takes only the 5 first values from dataset*/


	double[] weights;
	ActivationFunctionBehaviour activationFunctionBehaviour;


	public Neuron(){

	}

	public Neuron(int NumberOfInputs,
			ActivationFunctionBehaviour activationFunctionBehaviour){

	}
	public Neuron(double weights,
			ActivationFunctionBehaviour activationFunctionBehaviour){

	}

	double summingFunction(double[] inputValues){

		return 0;
	}

	double processInpt(double[] inputValues){
		return activationFunctionBehaviour.compute((summingFunction(inputValues)));
	}

	public String toString(){
		return "";
	}

}

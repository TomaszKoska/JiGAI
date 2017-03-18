package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_Activation.ActivationFunctionBehaviour;
import pl.tomaszkoska.JiGAI_Activation.BinarySigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Exceptions.MyException;

public class Neuron {

	double[] weights;
	ActivationFunctionBehaviour activationFunctionBehaviour;


	public Neuron(int numberOfInputs){
		this.weights = new double[numberOfInputs];
		this.activationFunctionBehaviour = new BinarySigmoidActivationFunction(1, 0.5);
	}
	public Neuron(double[] weights){
		for (int i = 0; i < weights.length; i++) {
			this.weights[i] = weights[i];
		}
		this.activationFunctionBehaviour = new BinarySigmoidActivationFunction(1, 0.5);
	}

	public Neuron(int numberOfInputs,
			ActivationFunctionBehaviour activationFunctionBehaviour){
		this.weights = new double[numberOfInputs];
		this.activationFunctionBehaviour = new BinarySigmoidActivationFunction(1, 0.5);
	}

	public Neuron(double[] weights,
			ActivationFunctionBehaviour activationFunctionBehaviour){
		for (int i = 0; i < weights.length; i++) {
			this.weights[i] = weights[i];
		}
		this.activationFunctionBehaviour = new BinarySigmoidActivationFunction(1, 0.5);
	}

	public double summingFunction(double[] inputValues){
		double sum = 0;

		try {
			if(inputValues.length != weights.length){
				throw new MyException
				("Number of input variables do not match "
						+ "the number of weights for this neuron!");
			}
			for (int i = 0; i < weights.length; i++) {
				sum+= inputValues[i]*weights[i];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sum;
	}

	public void randomizeWeights(){
		for (int i = 0; i < weights.length; i++) {
			weights[i] = Math.random();
		}
	}

	public void randomizeWeights(double min, double max){
		for (int i = 0; i < weights.length; i++) {
			weights[i] = Math.random()*(max-min)+min;
		}
	}

	public double processInput(double[] inputValues){
			return activationFunctionBehaviour.compute((summingFunction(inputValues)));
	}

	public void updateWeights(double[] updatingInfo){
		try {
			if(updatingInfo.length != weights.length){
				throw new MyException
				("Number of updating information values do not match "
						+ "the number of weights for this neuron!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		for (int i = 0; i < weights.length; i++) {
			weights[i] = weights[i] + updatingInfo[i];
		}

	}

	public String toString(){
		String output ="";
		for (int i = 0; i < weights.length; i++) {
			output += weights[i];
			if (i!=weights.length-1) {
				output	+= " | ";
			}
		}
		output += "\n" + activationFunctionBehaviour.getName();
		return output;
	}
	public double[] getWeights() {
		return weights;
	}
	public void setWeights(double[] weights) {
		this.weights = weights;
	}
	public ActivationFunctionBehaviour getActivationFunctionBehaviour() {
		return activationFunctionBehaviour;
	}
	public void setActivationFunctionBehaviour(ActivationFunctionBehaviour activationFunctionBehaviour) {
		this.activationFunctionBehaviour = activationFunctionBehaviour;
	}


}

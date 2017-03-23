package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_Activation.ActivationFunctionBehaviour;
import pl.tomaszkoska.JiGAI_Activation.BinarySigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.HyperbolicTangentActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.LinearActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Exceptions.MyException;

public class Neuron {

	private double[] weights;
	private double bias;
	ActivationFunctionBehaviour activationFunctionBehaviour;


	public Neuron(int numberOfInputs){
		this.weights = new double[numberOfInputs];
		this.activationFunctionBehaviour = new SigmoidActivationFunction(1);

	}
	public Neuron(double[] weights){
		this.weights = new double[weights.length];
		for (int i = 0; i < weights.length; i++) {
			this.weights[i] = weights[i];
		}
		this.activationFunctionBehaviour = new SigmoidActivationFunction(1);

	}

	public Neuron(int numberOfInputs,
			ActivationFunctionBehaviour activationFunctionBehaviour){
		this.weights = new double[numberOfInputs];
		this.activationFunctionBehaviour = activationFunctionBehaviour;

	}

	public Neuron(double[] weights,
		ActivationFunctionBehaviour activationFunctionBehaviour){
		this.weights = new double[weights.length];
		for (int i = 0; i < weights.length; i++) {
			this.weights[i] = weights[i];
		}
		this.activationFunctionBehaviour = activationFunctionBehaviour;

	}

	public double summingFunction(double[] inputValues){
		double sum = 0;
//		System.out.println("inp Val: " +inputValues.length);
//		System.out.println("weights: " +weights.length);
		try {
			if(inputValues.length != weights.length){
				throw new MyException
				("Number of input variables does not match "
						+ "the number of weights for this neuron!");
			}
			for (int i = 0; i < weights.length; i++) {
				sum+= inputValues[i]*weights[i];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sum+bias;
	}

	public void randomizeWeights(){
		for (int i = 0; i < weights.length; i++) {
			weights[i] = Math.random();
		}
		bias = Math.random();
	}

	public void randomizeWeights(double min, double max){
		for (int i = 0; i < weights.length; i++) {
			weights[i] = Math.random()*(max-min)+min;
		}
		bias = Math.random()*(max-min)+min;
	}

	public double processInput(double[] inputValues){
			return activationFunctionBehaviour.compute((summingFunction(inputValues)));
	}

	public void updateWeights(double biasInfo, double[] updatingInfo){
		try {
			if(updatingInfo.length != weights.length){
				throw new MyException
				("Number of updating information values does not match "
						+ "the number of weights for this neuron!");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		for (int i = 0; i < weights.length; i++) {
			weights[i] = weights[i] + updatingInfo[i];
		}
			bias += biasInfo;

	}

	public String toString(){
		String output ="";

		output	+= bias + " | ";
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
	public double getBias() {
		return bias;
	}
	public void setBias(double bias) {
		this.bias = bias;
	}


	public void setActivationFunction(String name){
		if(name.toLowerCase().equals("bs")){
			this.setActivationFunctionBehaviour(new BinarySigmoidActivationFunction(1,0.5));
		} else if(name.toLowerCase().equals("s")){
			this.setActivationFunctionBehaviour(new SigmoidActivationFunction(1));
		} else if(name.toLowerCase().equals("ht")){
			this.setActivationFunctionBehaviour(new HyperbolicTangentActivationFunction());
		} else{
			this.setActivationFunctionBehaviour(new LinearActivationFunction());
		}
	}

}

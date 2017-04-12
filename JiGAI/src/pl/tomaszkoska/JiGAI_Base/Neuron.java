package pl.tomaszkoska.JiGAI_Base;

import java.io.Serializable;

import pl.tomaszkoska.JiGAI_Activation.ActivationFunctionBehaviour;
import pl.tomaszkoska.JiGAI_Activation.HyperbolicTangentActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.LinearActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Exceptions.MyException;


public class Neuron implements Serializable{



	private static final long serialVersionUID = 1L;
	private double[] weights;
	private double bias;
	ActivationFunctionBehaviour activationFunctionBehaviour;
	protected double lastOutput;
	protected double lastSignalError;
	private double[] lastWeightDiff;
	protected double lastBiasError;

	public Neuron(int numberOfInputs){
		this.weights = new double[numberOfInputs];
		this.activationFunctionBehaviour = new SigmoidActivationFunction();
		this.lastSignalError = 0;
		this.lastBiasError = 0;
		this.lastWeightDiff = new double[numberOfInputs];

	}
	public Neuron(double[] weights){
		this.weights = new double[weights.length];
		this.lastWeightDiff = new double[weights.length];
		for (int i = 0; i < weights.length; i++) {
			this.weights[i] = weights[i];
			this.lastWeightDiff[i] = 0;
		}
		this.activationFunctionBehaviour = new SigmoidActivationFunction();
		this.lastSignalError = 0;
		this.lastBiasError = 0;
	}

	public Neuron(int numberOfInputs,
			ActivationFunctionBehaviour activationFunctionBehaviour){
		this.weights = new double[numberOfInputs];
		this.activationFunctionBehaviour = activationFunctionBehaviour;
		this.lastSignalError = 0;
		this.lastBiasError = 0;
		this.lastWeightDiff = new double[numberOfInputs];
	}

	public Neuron(double[] weights,
		ActivationFunctionBehaviour activationFunctionBehaviour){
		this.weights = new double[weights.length];
		this.lastWeightDiff = new double[weights.length];
		for (int i = 0; i < weights.length; i++) {
			this.weights[i] = weights[i];
			this.lastWeightDiff[i] = 0;
		}
		this.activationFunctionBehaviour = activationFunctionBehaviour;
		this.lastSignalError = 0;
		this.lastBiasError = 0;
	}

	public double summingFunction(double[] inputValues){
		double sum = 0;

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

		return (sum+bias);
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

	public double processInput(double[] inputValues, boolean learningMode){
		double tmp = activationFunctionBehaviour.compute((summingFunction(inputValues)));
		if(learningMode){
			lastOutput = activationFunctionBehaviour.compute((summingFunction(inputValues)));
		}
		return tmp;
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
		if(name.toLowerCase().equals("s")){
			this.setActivationFunctionBehaviour(new SigmoidActivationFunction());
		} else if(name.toLowerCase().equals("ht")){
			this.setActivationFunctionBehaviour(new HyperbolicTangentActivationFunction());
		} else{
			this.setActivationFunctionBehaviour(new LinearActivationFunction());
		}
	}

	public double getLastOutput() {
		return lastOutput;
	}
	public void setLastOutput(double lastOutput) {
		this.lastOutput = lastOutput;
	}
	public double getLastSignalError() {
		return lastSignalError;
	}
	public void setLastSignalError(double lastSignalError) {
		this.lastSignalError = lastSignalError;
	}
	public double getLastBiasError() {
		return lastBiasError;
	}
	public void setLastBiasError(double lastBiasError) {
		this.lastBiasError = lastBiasError;
	}
	public double[] getLastWeightDiff() {
		return lastWeightDiff;
	}

	public void setLastWeightDiff(double[] lastWeightDiff) {
		this.lastWeightDiff = lastWeightDiff;
	}

	public double getLastWeightDiff(int i) {
		return lastWeightDiff[i];
	}

	public void setLastWeightDiff(int i, double lastWeightDiff) {
		this.lastWeightDiff[i] = lastWeightDiff;
	}
	public void setWeight(int w, double d) {
		this.weights[w] = d;
	}
	public double getWeight(int w) {
		return this.weights[w];
	}
	public void calculateLastSignalError(double errorSum) {
		setLastSignalError(errorSum*activationFunctionBehaviour.derivative(this.getLastOutput()));
	}

}

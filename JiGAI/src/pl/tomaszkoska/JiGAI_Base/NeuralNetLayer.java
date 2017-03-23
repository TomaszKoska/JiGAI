package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_Activation.BinarySigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.HyperbolicTangentActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.LinearActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;

public class NeuralNetLayer {
	private NeuralNetLayer previousLayer;
	private Neuron[] neurons;


	public NeuralNetLayer(int neuronCount, int weightCount){
		super();
		this.neurons = new Neuron[neuronCount];
		for (int i = 0; i < neurons.length; i++) {
			neurons[i] = new Neuron(weightCount);
		}
		randomize();
	}


	public NeuralNetLayer(int neuronCount,
			NeuralNetLayer previousLayer
			){
//
		super();
		this.previousLayer = previousLayer;
		this.neurons = new Neuron[neuronCount];
		for (int i = 0; i < neurons.length; i++) {
			neurons[i] = new Neuron(previousLayer.getNeuronCount());
		}
	}


	public void randomize(){
		for (int i = 0; i < neurons.length; i++) {
				neurons[i].randomizeWeights();
		}
	}

	public void randomize(double min, double max){
		for (int i = 0; i < neurons.length; i++) {
			neurons[i].randomizeWeights(min,max);
		}
	}

	public void setActivationFunction(String activationFunctionName){
			for (int i = 0; i < neurons.length; i++) {
			neurons[i].setActivationFunction(activationFunctionName);
			}
	}



	public double[] processInput(double[] inputValues){
		double[] outcome = new double[this.getNeuronCount()];

		for (int i = 0; i < outcome.length; i++) {
				outcome[i] = neurons[i].processInput(inputValues);
		}

		return outcome;
	}


	public NeuralNetLayer getPreviousLayer() {
		return previousLayer;
	}


	public void setPreviousLayer(NeuralNetLayer previousLayer) {
		this.previousLayer = previousLayer;
	}


	public Neuron[] getNeurons() {
		return neurons;
	}


	public void setNeurons(Neuron[] neurons) {
		this.neurons = neurons;
	}

	public int getNeuronCount(){
		return neurons.length;
	}




	@Override
	public String toString() {
		String outcome = "";

		for (int i = 0; i < neurons.length; i++) {
			outcome += neurons[i] + "\n";
		}

		return outcome;
	}




}

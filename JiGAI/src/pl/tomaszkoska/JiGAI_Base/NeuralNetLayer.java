package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_Activation.BinarySigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.NeutralActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;

public class NeuralNetLayer {
	NeuralNetLayer previousLayer;
	Neuron[] neurons;


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
			neurons[i] = new Neuron(previousLayer.getNeuronsCount());
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
		if(activationFunctionName.toLowerCase() == "bs"){
			for (int i = 0; i < neurons.length; i++) {
				neurons[i].setActivationFunctionBehaviour(new BinarySigmoidActivationFunction(1,0.5));
			}
		} else if(activationFunctionName.toLowerCase() == "s"){
			for (int i = 0; i < neurons.length; i++) {
			neurons[i].setActivationFunctionBehaviour(new SigmoidActivationFunction(1));
			}
		} else{
			for (int i = 0; i < neurons.length; i++) {
			neurons[i].setActivationFunctionBehaviour(new NeutralActivationFunction());
			}
		}
	}



	public double[] processInput(double[] inputValues){
		double[] outcome = new double[this.getNeuronsCount()];

		for (int i = 0; i < outcome.length; i++) {
				outcome[i] = neurons[i].processInput(inputValues);
		}

		return outcome;
	}


	public int getNeuronsCount(){
		return neurons.length;
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

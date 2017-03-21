package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_genetics.InheritanceBehaviour;
import pl.tomaszkoska.JiGAI_genetics.MutationBehaviour;

public class GeneticNeuralNet extends NeuralNet {
	protected double[] genome;
	protected double fitness; //can be integer, can be double, can even be boolean
	protected MutationBehaviour mutationBehaviour;
	protected InheritanceBehaviour inheritanceBehaviour;

	public GeneticNeuralNet(int[] neuronCounts, int numberOfInputVariables) {
		super(neuronCounts, numberOfInputVariables);
	}


	public void introduceMutation(double mutationRate){
		mutationBehaviour.mutate(mutationRate);
	};

	public void decodeGenome(){
		//TODO: this function should turn genome into an object that
	}

	public String getGenomeString(){
		String out="[";
		for (int i = 0; i < genome.length; i++) {
			out = out + genome[i] + ",";
		}
		out = out + "]";
		return out;
	}

	public double[] getGenome() {
		return genome;
	}
	public void setGenome(double[] genome) {
		this.genome = genome;
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public int getGenomeLength(){
		return genome.length;
	}


}

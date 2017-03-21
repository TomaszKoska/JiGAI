package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_Genetics.InheritanceBehaviour;
import pl.tomaszkoska.JiGAI_Genetics.MutationBehaviour;
import pl.tomaszkoska.JiGAI_Genetics.RandomInheritance;
import pl.tomaszkoska.JiGAI_Genetics.RandomMutation;

public class GeneticNeuralNet extends NeuralNet implements Comparable<GeneticNeuralNet> {
	protected double[] genome;
	protected double fitness; //can be integer, can be double, can even be boolean
	protected MutationBehaviour mutationBehaviour;
	protected InheritanceBehaviour inheritanceBehaviour;
	protected int age;	//how many epochs did this net survive?


	public GeneticNeuralNet(int[] neuronCounts, int numberOfInputVariables) {
		super(neuronCounts, numberOfInputVariables);
		codeGenome();
		setAllGeneticBehaviour();
	}

	public void setAllGeneticBehaviour(){
		mutationBehaviour = new RandomMutation(this, -1, 1);
		inheritanceBehaviour = new RandomInheritance(this);
	}

	public void codeGenome(){
		//1 - number of layers
		//2...n - neuron counts
		//n+1...m - biases for each neuron
		//m+1...p - code number of activation function
		//p+1...q - weights of each neuron one by one (input layer first)

		int pointer = layers.length+1;


		for (int i = 0; i < layers.length; i++) {
			pointer+= 2*layers[i].getNeurons().length;
		}

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length; j++) {
				for (int k = 0; k < layers[i].getNeurons()[j].getWeights().length; k++) {
					pointer++;
				}
			}
		}


		this.genome = new double[pointer];

		//1+layerCount+layerCount*neuronCount(czyli bias)+layerCount*neuronCount(czyli funkcje aktywacji) + layerCount*neuronCount*weghtsCount
		this.genome[0] = this.layers.length;


		pointer = 1;
		for (int i = 0; i < layers.length; i++) {
			this.genome[pointer] = layers[i].getNeuronCount();
			pointer++;
		}

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length; j++) {
				this.genome[pointer] = layers[i].getNeurons()[j].getBias();
				pointer++;
			}
		}


		for (int i = 0; i < layers.length; i++) {

			for (int j = 0; j < layers[i].getNeurons().length; j++) {

				switch(layers[i].getNeurons()[j].getActivationFunctionBehaviour().getShortName()){
				case "s":
					this.genome[pointer] = 1;
				break;
				case "bs":
					this.genome[pointer] = 2;
				break;
				case "l":
					this.genome[pointer] = 3;
				break;
				case "ht":
					this.genome[pointer] = 4;
				break;
				default:
					this.genome[pointer] = -1;	// this will mean linear in future probably
				break;
				}
				pointer++;
			}
		}

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length; j++) {
				for (int k = 0; k < layers[i].getNeurons()[j].getWeights().length; k++) {
					this.genome[pointer] = layers[i].getNeurons()[j].getWeights()[k];
					pointer++;
				}
			}
		}


		//genome is: 1: number of layers,
		//n - number of neurons on each layer
		//sequence: baias,weights,acivationFunctionNumber top->down
	}

	public void decodeGenome(){
		//TODO: this function should turn genome into a neural net

		//ZASTAP TO KONSTRUKTIREM!!
	}



	public void makeRandom() {
		for (int i = 0; i < genome.length; i++) {
			genome[i] = Math.random();
		}
	}

	public void introduceMutation(double mutationRate){
		mutationBehaviour.mutate(mutationRate);
	};





	@Override
	public int compareTo(GeneticNeuralNet o) {
		// TODO Auto-generated method stub
		return 0;
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


	public MutationBehaviour getMutationBehaviour() {
		return mutationBehaviour;
	}


	public void setMutationBehaviour(MutationBehaviour mutationBehaviour) {
		this.mutationBehaviour = mutationBehaviour;
	}


	public InheritanceBehaviour getInheritanceBehaviour() {
		return inheritanceBehaviour;
	}


	public void setInheritanceBehaviour(InheritanceBehaviour inheritanceBehaviour) {
		this.inheritanceBehaviour = inheritanceBehaviour;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	public void increaseAge() {
		this.age++;
	}



}

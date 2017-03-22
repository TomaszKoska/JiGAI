package pl.tomaszkoska.JiGAI_Base;

import java.util.Random;

import pl.tomaszkoska.JiGAI_Activation.BinarySigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.HyperbolicTangentActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.LinearActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;
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

	public static int UNMUTABLE_PART_OF_GENOME =10;

	public GeneticNeuralNet(int[] neuronCounts, int numberOfInputVariables) {
		super(neuronCounts, numberOfInputVariables);
		codeGenome();
		setAllGeneticBehaviour();
	}
	public GeneticNeuralNet(double[] genome) {
		super();
		this.genome = genome;
		decodeGenome();
		setAllGeneticBehaviour();
	}
	public GeneticNeuralNet() {
		super();
	}

	public void setAllGeneticBehaviour(){
		UNMUTABLE_PART_OF_GENOME = 1+1+(int)(this.genome[0]);
		mutationBehaviour = new RandomMutation(this, -1, 1);
		inheritanceBehaviour = new RandomInheritance(this);
	}

	public void decodeGenome(){
		//0 - number of layers
		//1...n - neuron counts
		//m+1...n - code number of activation function
		//n+1...p - biases for each neuron
		//p+1...q - weights of each neuron one by one (input layer first)

		int layerCount = (int)(genome[0]);
		int[] neuronCounts = new int[layerCount];
		this.inputVariableCount = (int)(genome[1]);
		int pointer = 2;

		for (int i = 0; i < neuronCounts.length; i++) {
			neuronCounts[i] = (int)(genome[pointer]);
			pointer++;
		}


		this.layers = new NeuralNetLayer[neuronCounts.length];
		this.layers[0] = new NeuralNetLayer(neuronCounts[0],(int)(genome[1]));

		for (int i = 1; i < layers.length; i++) {
			this.layers[i] = new NeuralNetLayer(neuronCounts[i],layers[i-1]);
		}

		this.inputVariableCount = (int)(genome[1]);

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length; j++) {

				switch((int)(this.genome[pointer])){
				case 1:
					layers[i].getNeurons()[j].setActivationFunctionBehaviour(new SigmoidActivationFunction());
				break;
				case 2:
					layers[i].getNeurons()[j].setActivationFunctionBehaviour(new BinarySigmoidActivationFunction(0.5,1));
				break;
				case 3:
					layers[i].getNeurons()[j].setActivationFunctionBehaviour(new LinearActivationFunction());
				break;
				case 4:
					layers[i].getNeurons()[j].setActivationFunctionBehaviour(new HyperbolicTangentActivationFunction());
				break;
				default:
					layers[i].getNeurons()[j].setActivationFunctionBehaviour(new LinearActivationFunction());
				break;
				}
				pointer++;
			}
		}


		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length; j++) {

				layers[i].getNeurons()[j].setBias(genome[pointer]);
				pointer++;
			}
		}


		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length; j++) {
				for (int k = 0; k< layers[i].getNeurons()[j].getWeights().length; k++) {
					layers[i].getNeurons()[j].getWeights()[k] = genome[pointer];
					pointer++;
				}
			}
		}

	}


	public void codeGenome(){
		//0 - number of layers
		//1...n - neuron counts
		//m+1...n - code number of activation function
		//n+1...p - biases for each neuron
		//p+1...q - weights of each neuron one by one (input layer first)

		int pointer = layers.length+2; // plus 2 because 1. number of layers, and 2. number od input variables


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
		this.genome[1] = this.inputVariableCount;

		pointer = 2;

		for (int i = 0; i < layers.length; i++) {
			this.genome[pointer] = layers[i].getNeuronCount();
			pointer++;
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
				this.genome[pointer] = layers[i].getNeurons()[j].getBias();
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
	}

	public void makeRandom() {
		//0 - number of layers
		//1...n - neuron counts
		//m+1...n - code number of activation function
		//n+1...p - biases for each neuron
		//p+1...q - weights of each neuron one by one (input layer first)
		int min=3;
		int max=4;
		int mutationStart = UNMUTABLE_PART_OF_GENOME;
		Random random = new Random();
		for (int i = mutationStart; i < mutationStart+layers.length; i++) {
			genome[i] = random.nextInt(max + 1 - min) + min;;
		}


		for (int i = 0; i < layers.length; i++) {
			mutationStart += layers[i].getNeuronCount();
		}

		for (int i = mutationStart; i < genome.length; i++) {
			genome[i] = Math.random();
		}

	}


	public void intherit(GeneticNeuralNet mom, GeneticNeuralNet dad) {
		for (int i = 0; i < genome.length; i++) {

			if(Math.random() < 0.5){
			this.genome[i] = mom.getGenome()[i];
			}else{
			this.genome[i] = dad.getGenome()[i];
			}
		}
	}




	public void introduceMutation(double mutationRate){
		mutationBehaviour.mutate(mutationRate);
	};

	public double calculateFitness(){
		double[] rmses = this.getRMSE();
		double fit = 0;

		for (int i = 0; i < rmses.length; i++) {
			fit += rmses[i];
		}
		fit = fit/rmses.length;
		//System.out.println(fit);
		this.fitness = fit;
		return fit;
	}

	public double calculateFitness(double[][] inputDataSet, double[][] targetDataSet){

		this.fullPredict(inputDataSet, targetDataSet);

		double[] rmses = this.getRMSE();
		double fit = 0;

		for (int i = 0; i < rmses.length; i++) {
			fit += rmses[i];
		}
		fit = fit/rmses.length;

		this.fitness = fit;
		return fit;
	}



	@Override
	public int compareTo(GeneticNeuralNet o) {
		int thisFittness = (int)(this.getFitness()*100000000);
		int otherFittness = (int)(o.getFitness()*100000000);

		return otherFittness-thisFittness;
	}

	public String getGenomeString(){
		String out="";

		for (int i = 0; i < genome.length; i++) {
			out = out + genome[i];
			if (i < genome.length-1){
				out += ",";
			}
		}
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

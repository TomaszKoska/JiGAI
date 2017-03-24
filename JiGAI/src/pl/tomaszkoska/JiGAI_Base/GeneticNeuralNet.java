package pl.tomaszkoska.JiGAI_Base;



import pl.tomaszkoska.JiGAI_Activation.BinarySigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.HyperbolicTangentActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.LinearActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_InheritanceBehaviours.InheritanceBehaviour;
import pl.tomaszkoska.JiGAI_InheritanceBehaviours.RandomInheritance;
import pl.tomaszkoska.JiGAI_KillingBehaviours.KillingBehaviour;
import pl.tomaszkoska.JiGAI_MutationBehaviours.AlterationMutation;
import pl.tomaszkoska.JiGAI_MutationBehaviours.MutationBehaviour;
import pl.tomaszkoska.JiGAI_MutationBehaviours.RandomMutation;
import pl.tomaszkoska.JiGAI_ReproductionBehaviours.ReproductionBehaviour;

public class GeneticNeuralNet extends NeuralNet implements Comparable<GeneticNeuralNet> {
	protected double[] genome;
	protected double fitness; //can be integer, can be double, can even be boolean
	protected MutationBehaviour mutationBehaviour;
	protected InheritanceBehaviour inheritanceBehaviour;
	protected int age;	//how many epochs did this net survive?
	protected int points;

	public GeneticNeuralNet(int[] neuronCounts, int numberOfInputVariables) {
		super(neuronCounts, numberOfInputVariables);
		codeGenome();
		setAllGeneticBehaviour();
		points=0;
		age=0;
	}
	public GeneticNeuralNet(int[] neuronCounts,
			int numberOfInputVariables,
			String activationFunctionShortName) {
		super(neuronCounts, numberOfInputVariables,activationFunctionShortName);
		codeGenome();
		setAllGeneticBehaviour();
		points=0;
		age=0;
	}


	public void setAllGeneticBehaviour(){
		mutationBehaviour = new RandomMutation(this, -100, 100);
		inheritanceBehaviour = new RandomInheritance(this);
	}

	public void decodeGenome(){
		int pointer = 0;
		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				layers[i].getNeurons()[j].setBias(genome[pointer]);
				pointer++;
			}
		}

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				for (int k = 0; k < layers[i].getNeurons()[j].getWeights().length ; k++) {
					layers[i].getNeurons()[j].getWeights()[k] = genome[pointer];
					pointer++;
				}
			}
		}
		this.setActivationFunction();
	}


	public void codeGenome(){
		int lengthOfGenome = 0;

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				lengthOfGenome+=layers[i].getNeurons()[j].getWeights().length+1;
				//+1 because there is also a bias term
			}
		}

		genome = new double[lengthOfGenome];

		int pointer = 0;
		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				genome[pointer] = layers[i].getNeurons()[j].getBias();
				pointer++;
			}
		}

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				for (int k = 0; k < layers[i].getNeurons()[j].getWeights().length ; k++) {
					genome[pointer] = layers[i].getNeurons()[j].getWeights()[k];
					pointer++;
				}
			}
		}
		this.setActivationFunction();
	}

	public void makeRandom() {
		int lengthOfGenome = 0;

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				lengthOfGenome+=layers[i].getNeurons()[j].getWeights().length+1;
				//+1 because there is also a bias term
			}
		}

		genome = new double[lengthOfGenome];

		int pointer = 0;
		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				genome[pointer] = Math.random();
				pointer++;
			}
		}

		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length ; j++) {
				for (int k = 0; k < layers[i].getNeurons()[j].getWeights().length ; k++) {
					genome[pointer] = Math.random();
					pointer++;
				}
			}
		}

		this.setActivationFunction();

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

		return thisFittness-otherFittness;
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

	public void setInheritanceBehaviourBasedOnName(String ibShortName){
		if(ibShortName.toLowerCase().equals("ri")){
				this.setInheritanceBehaviour(new RandomInheritance(this));
		}
	}

	public void setMutationBehaviourBasedOnName(String mbShortName){
		if(mbShortName.toLowerCase().equals("rm")){
				this.setMutationBehaviour(new RandomMutation(this,-1,1));
		}else if(mbShortName.toLowerCase().equals("am")){
				this.setMutationBehaviour(new AlterationMutation(this));
		}
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


	public int getTotalNeuronCount(){
		int sum = 0;
		for (int i = 0; i < layers.length; i++) {
				sum += layers[i].getNeuronCount();
		}

		return sum;
	}
	public String getActivationFunctionShortName() {
		return activationFunctionShortName;
	}
	public void setActivationFunctionShortName(String activationFunctionShortName) {
		this.activationFunctionShortName = activationFunctionShortName;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	public void addPoints(int apoints) {
		this.points += apoints;
	}



}

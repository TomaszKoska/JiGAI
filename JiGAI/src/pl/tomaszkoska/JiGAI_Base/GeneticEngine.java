package pl.tomaszkoska.JiGAI_Base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import pl.tomaszkoska.JiGAI_Genetics.KillingBehaviour;
import pl.tomaszkoska.JiGAI_Genetics.ReproductionBehaviour;


public class GeneticEngine {

	protected ArrayList<GeneticNeuralNet> population;

	protected KillingBehaviour killingBehaviour;
	ReproductionBehaviour reproductionBehaviour;

	protected double MUTATION_RATE;
	protected int START_POPULATION_SIZE;

	protected int INPUT_COUNT;
	protected int[] NEURON_COUNTS;



	public GeneticEngine( int iNPUT_COUNT, int[] nEURON_COUNTS,double mUTATION_RATE, int sTART_POPULATION_SIZE) {
		super();
		MUTATION_RATE = mUTATION_RATE;
		START_POPULATION_SIZE = sTART_POPULATION_SIZE;
		INPUT_COUNT = iNPUT_COUNT;
		NEURON_COUNTS = nEURON_COUNTS;
		killingBehaviour = new KillingBehaviour(this);
		reproductionBehaviour = new ReproductionBehaviour(this);
	}

	public void initialize() {
		population = new ArrayList<GeneticNeuralNet>();
		for (int i = 0; i < START_POPULATION_SIZE; i++) {
			GeneticNeuralNet gnn = new GeneticNeuralNet(NEURON_COUNTS,INPUT_COUNT);
			gnn.makeRandom();
			gnn.decodeGenome();
			population.add(gnn);
		}
	}

	public void runNextTurn(double[][] inputDataSet,double[][] targetDataSet) {
		updateAge();
		doTasks(inputDataSet,targetDataSet);
		calculateFitness();

		sort();

		kill();

		reproduce();

	}


	private void doTasks(double[][] inputDataSet,double[][] targetDataSet) {

		for (Iterator<GeneticNeuralNet> iterator = population.iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			gnn.fullPredict(inputDataSet, targetDataSet);
		}
	}

	private void reproduce() {
		reproductionBehaviour.reproduce();

	}

	private void calculateFitness() {
		for (Iterator<GeneticNeuralNet> iterator = population.iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			gnn.calculateFitness();
		}
	}

	private void sort() {
		Collections.sort(population);
	}

	private void kill(){
		killingBehaviour.kill();
	}

	private void updateAge() {
		for (Iterator<GeneticNeuralNet> iterator = population.iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			gnn.increaseAge();
		}
	}

	public ArrayList<GeneticNeuralNet> getPopulation() {
		return population;
	}

	public void setPopulation(ArrayList<GeneticNeuralNet> population) {
		this.population = population;
	}

	public double getMUTATION_RATE() {
		return MUTATION_RATE;
	}

	public void setMUTATION_RATE(double mUTATION_RATE) {
		MUTATION_RATE = mUTATION_RATE;
	}

	public int getSTART_POPULATION_SIZE() {
		return START_POPULATION_SIZE;
	}

	public void setSTART_POPULATION_SIZE(int sTART_POPULATION_SIZE) {
		START_POPULATION_SIZE = sTART_POPULATION_SIZE;
	}

	public int getINPUT_COUNT() {
		return INPUT_COUNT;
	}

	public void setINPUT_COUNT(int iNPUT_COUNT) {
		INPUT_COUNT = iNPUT_COUNT;
	}

	public int[] getNEURON_COUNTS() {
		return NEURON_COUNTS;
	}

	public void setNEURON_COUNTS(int[] nEURON_COUNTS) {
		NEURON_COUNTS = nEURON_COUNTS;
	}

	public void printTop(){
		for (int i = 0; i < Math.min(5,population.size()); i++) {
			System.out.println(i + ". " + population.get(i).getFitness());
		}
	}
	public double getBestFitness(){
		return population.get(0).getFitness();
	}

}

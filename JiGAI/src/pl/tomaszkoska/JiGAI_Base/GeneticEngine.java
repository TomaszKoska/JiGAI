package pl.tomaszkoska.JiGAI_Base;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import pl.tomaszkoska.JiGAI_KillingBehaviours.KillingBehaviour;
import pl.tomaszkoska.JiGAI_ReproductionBehaviours.ReproductionBehaviour;


public class GeneticEngine {

	protected ArrayList<GeneticNeuralNet> population;

	protected KillingBehaviour killingBehaviour;
	protected ReproductionBehaviour reproductionBehaviour;

	protected String inheritanceBehaviourName;
	protected String mutationBehaviourName;
	protected String activationFunctionShortName;

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
		inheritanceBehaviourName = "ri";
		mutationBehaviourName = "rm";
		activationFunctionShortName = "ht";
	}

	public void initialize() {
		population = new ArrayList<GeneticNeuralNet>();
		for (int i = 0; i < START_POPULATION_SIZE; i++) {
			GeneticNeuralNet gnn = new GeneticNeuralNet(NEURON_COUNTS,INPUT_COUNT, activationFunctionShortName);
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
		givePoints();
		kill();
		reproduce();

	}


	private void givePoints() {
		int i = 0;
		for (Iterator<GeneticNeuralNet> iterator = population.iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			if(i<population.size()*1/10){
			gnn.addPoints(2);
			}else if(i<population.size()*1/4){
			gnn.addPoints(1);
			}else if(i<population.size()*2/4){
				gnn.addPoints(0);
			}else{
				gnn.addPoints(-3);
			}

			i++;
		}
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

	public double getAverageAge(){
		double age = 0;
		for (Iterator<GeneticNeuralNet> iterator = population.iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			age+= gnn.getAge();
		}
		age = age/population.size();
		return age;
	}

	public KillingBehaviour getKillingBehaviour() {
		return killingBehaviour;
	}

	public void setKillingBehaviour(KillingBehaviour killingBehaviour) {
		this.killingBehaviour = killingBehaviour;
	}

	public ReproductionBehaviour getReproductionBehaviour() {
		return reproductionBehaviour;
	}

	public void setReproductionBehaviour(ReproductionBehaviour reproductionBehaviour) {
		this.reproductionBehaviour = reproductionBehaviour;
	}

	public void setInheritanceBehaviour(String ibShortName){
		inheritanceBehaviourName = ibShortName;
	}

	public void setMutationBehaviour(String mbShortName){
		mutationBehaviourName = mbShortName;
	}

	public String getInheritanceBehaviourName() {
		return inheritanceBehaviourName;
	}

	public void setInheritanceBehaviourName(String inheritanceBehaviourName) {
		this.inheritanceBehaviourName = inheritanceBehaviourName;
	}

	public String getMutationBehaviourName() {
		return mutationBehaviourName;
	}

	public void setMutationBehaviourName(String mutationBehaviourName) {
		this.mutationBehaviourName = mutationBehaviourName;
	}

	public String getActivationFunctionShortName() {
		return activationFunctionShortName;
	}

	public void setActivationFunctionShortName(String activationFunctionShortName) {
		this.activationFunctionShortName = activationFunctionShortName;
	}

	public void saveInCSV(String path){
		 CSVWriter writer;
		try {
		writer = new CSVWriter(new FileWriter(path), ',');

		for (Iterator<GeneticNeuralNet> iterator = population.iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			String singleLine = gnn.getGenomeString();
			String[] entries = singleLine.split(",");
		    writer.writeNext(entries);
		}

		 writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public void readFromCSV(String path){
		population = new ArrayList<GeneticNeuralNet>();
		CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(path),',');
            String[] line;
            double[] tmpGenome;
            while ((line = reader.readNext()) != null) {
            	tmpGenome = new double[line.length];
            	for (int i = 0; i < line.length; i++) {
            		tmpGenome[i] = Double.parseDouble(line[i]);
				}
            	GeneticNeuralNet gnn =  new GeneticNeuralNet(NEURON_COUNTS,INPUT_COUNT,activationFunctionShortName);
            	gnn.setGenome(tmpGenome);
            	gnn.decodeGenome();
            	population.add(gnn);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	public double[][] votePredict(double[][] inputs, double[][] targets, int howManyVotes){
		double [][] votePrediction = new double [targets.length][targets[0].length];

		Collections.sort(population);
		Collections.sort(population, (o1, o2) -> o2.getPoints() - o1.getPoints());
		for (Iterator<GeneticNeuralNet> iterator = population.iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			gnn.fullPredict(inputs, targets);
		}

		for (int i = 0; i < votePrediction.length; i++) {
			for (int j = 0; j < votePrediction[i].length; j++) {
				for (int k = 0; k < Math.min(population.size(),howManyVotes); k++) {
					votePrediction[i][j] = population.get(k).getPrediction()[i][j];
				}
			}
		}

		return votePrediction;
	}


}

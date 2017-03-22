package pl.tomaszkoska.JiGAI_Genetics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class ReproductionBehaviour {
	private GeneticEngine geneticEngine;

	public ReproductionBehaviour(GeneticEngine geneticEngine) {
		super();
		this.geneticEngine = geneticEngine;
	}

	public GeneticEngine getGeneticEngine() {
		return geneticEngine;
	}

	public void setGeneticEngine(GeneticEngine geneticEngine) {
		this.geneticEngine = geneticEngine;
	}

	public void reproduce(){
		ArrayList<GeneticNeuralNet> population = geneticEngine.getPopulation();
		int currentPopulation = population.size();

		for (int i = currentPopulation; i < geneticEngine.getSTART_POPULATION_SIZE(); i++) {
			GeneticNeuralNet child  = new GeneticNeuralNet(geneticEngine.getNEURON_COUNTS(),geneticEngine.getINPUT_COUNT());

			int motherNumber = ThreadLocalRandom.current().nextInt(0,  currentPopulation);
			int fatherNumber = ThreadLocalRandom.current().nextInt(0,  currentPopulation);

			//System.out.println( i + "   "+ motherNumber + "   " + fatherNumber);

			child.intherit(population.get(motherNumber), population.get(fatherNumber));
			child.introduceMutation(geneticEngine.getMUTATION_RATE());
			child.decodeGenome();
			geneticEngine.getPopulation().add(child);
		}
	}


}

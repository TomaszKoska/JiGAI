package pl.tomaszkoska.JiGAI_KillingBehaviours;

import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class FitnessBasedChanceOfSurvival extends KillingBehaviour {

	public FitnessBasedChanceOfSurvival(GeneticEngine geneticEngine) {
		super(geneticEngine);
	}

	public void kill(){

		double sumOfFitness = 0;
		for (Iterator<GeneticNeuralNet> iterator = geneticEngine.getPopulation().iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			sumOfFitness += gnn.getFitness();
		}

		double cumulated = 0;
		Iterator<GeneticNeuralNet> i = geneticEngine.getPopulation().iterator();

		while (i.hasNext()) {
			GeneticNeuralNet tmp = i.next();
			cumulated += tmp.getFitness();
			if(Math.random() < cumulated/sumOfFitness){
				i.remove();
			}
		}
	}


}

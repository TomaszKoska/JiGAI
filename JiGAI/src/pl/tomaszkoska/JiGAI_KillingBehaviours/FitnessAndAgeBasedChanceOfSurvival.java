package pl.tomaszkoska.JiGAI_KillingBehaviours;

import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class FitnessAndAgeBasedChanceOfSurvival extends KillingBehaviour {


	public FitnessAndAgeBasedChanceOfSurvival(GeneticEngine geneticEngine) {
		super(geneticEngine);
	}

	public void kill(){

		double sumOfFitness = 0;
		double sumOfAge = 0;

		for (Iterator<GeneticNeuralNet> iterator = geneticEngine.getPopulation().iterator(); iterator.hasNext();) {
			GeneticNeuralNet gnn = (GeneticNeuralNet) iterator.next();
			sumOfFitness += gnn.getFitness();
			sumOfAge += gnn.getAge();
		}

		double cumulated = 0;
		double cumulatedAge =0;
		Iterator<GeneticNeuralNet> i = geneticEngine.getPopulation().iterator();

		while (i.hasNext()) {
			GeneticNeuralNet tmp = i.next();
			cumulated += tmp.getFitness();
			cumulatedAge += tmp.getAge();
			if(Math.random() < 2*cumulated/sumOfFitness && Math.random()< 1-(sumOfAge/sumOfAge)){
				i.remove();
			}
		}
	}
}

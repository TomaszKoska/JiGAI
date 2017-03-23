package pl.tomaszkoska.JiGAI_KillingBehaviours;

import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class DecreasingChanceOfSurvival extends KillingBehaviour {

	public DecreasingChanceOfSurvival(GeneticEngine geneticEngine) {
		super(geneticEngine);
	}

	public void kill(){

		int j = 0;

		Iterator<GeneticNeuralNet> i = geneticEngine.getPopulation().iterator();
		while (i.hasNext()) {
			GeneticNeuralNet tmp = i.next();
			if(Math.random() < j/geneticEngine.getPopulation().size()){
				i.remove();
			}
			j++;
		}
	}

}

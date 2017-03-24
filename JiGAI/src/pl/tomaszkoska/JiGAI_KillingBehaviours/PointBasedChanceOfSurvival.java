package pl.tomaszkoska.JiGAI_KillingBehaviours;

import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class PointBasedChanceOfSurvival extends KillingBehaviour {

	public PointBasedChanceOfSurvival(GeneticEngine geneticEngine) {
		super(geneticEngine);
	}
	public void kill(){

		Iterator<GeneticNeuralNet> i = geneticEngine.getPopulation().iterator();

		while (i.hasNext()) {
			GeneticNeuralNet tmp = i.next();
			if(tmp.getPoints()<0){
				i.remove();
			}
		}
	}

}

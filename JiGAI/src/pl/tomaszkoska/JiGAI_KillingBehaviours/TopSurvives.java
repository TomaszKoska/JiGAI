package pl.tomaszkoska.JiGAI_KillingBehaviours;

import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class TopSurvives extends KillingBehaviour {

	protected double howManyPercentSurvives;


	public TopSurvives(GeneticEngine geneticEngine) {
		super(geneticEngine);
		this.howManyPercentSurvives = 20;
	}

	public TopSurvives(GeneticEngine geneticEngine, double howManyPercentSurvives) {
		super(geneticEngine);
		this.howManyPercentSurvives  = howManyPercentSurvives;
	}


	public void kill(){
		/*In basic scenario half of the population dies*/


		int j = 0;
		int first = (int)(geneticEngine.getPopulation().size()*howManyPercentSurvives/100);
		int last = geneticEngine.getPopulation().size();


		Iterator<GeneticNeuralNet> i = geneticEngine.getPopulation().iterator();
		while (i.hasNext()) {
			GeneticNeuralNet tmp = i.next();
			if(j > first && j < last){
				i.remove();
			}
			j++;
		}
	}

	public double getHowManyPercentSurvives() {
		return howManyPercentSurvives;
	}

	public void setHowManyPercentSurvives(double howManyPercentSurvives) {
		this.howManyPercentSurvives = howManyPercentSurvives;
	}


}

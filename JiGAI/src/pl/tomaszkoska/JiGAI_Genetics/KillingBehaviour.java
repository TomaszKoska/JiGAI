package pl.tomaszkoska.JiGAI_Genetics;

import java.util.Collections;
import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class KillingBehaviour {
	private GeneticEngine geneticEngine;

	public KillingBehaviour(GeneticEngine geneticEngine) {
		this.geneticEngine = geneticEngine;
	}

	public void kill(){
		/*In basic scenario half of the population dies*/


		int j = 0;
		int first = (int)(geneticEngine.getPopulation().size()*9/10);
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
}

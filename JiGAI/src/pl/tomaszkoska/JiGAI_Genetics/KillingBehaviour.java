package pl.tomaszkoska.JiGAI_Genetics;

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

		Iterator<GeneticNeuralNet> i = geneticEngine.getPopulation().iterator();

		int j = (int)(geneticEngine.getPopulation().size()/2);
		int last = geneticEngine.getPopulation().size();
		while (i.hasNext() && j < last) {
			GeneticNeuralNet tmp = i.next();
				i.remove();
				j++;
			}
	}
}

package pl.tomaszkoska.JiGAI_MutationBehaviours;

import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class AlterationMutation implements MutationBehaviour {
	private GeneticNeuralNet child;



	public AlterationMutation(GeneticNeuralNet child) {
		super();
		this.child = child;
	}

	@Override
	public void mutate(double mutationRate) {
		for (int i = GeneticNeuralNet.UNMUTABLE_PART_OF_GENOME; i < child.getGenomeLength(); i++) {
			if(Math.random() < mutationRate){
				child.getGenome()[i] = child.getGenome()[i] *(1+(Math.random()-0.5));
			}
		}

	}

	public GeneticNeuralNet getChild() {
		return child;
	}

	public void setChild(GeneticNeuralNet child) {
		this.child = child;
	}



}

package pl.tomaszkoska.JiGAI_genetics;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class RandomInheritance implements InheritanceBehaviour{
	private GeneticNeuralNet child;

	public RandomInheritance(GeneticNeuralNet child) {
		this.child = child;
	}

	@Override
	public void inherit(GeneticNeuralNet mother, GeneticNeuralNet father) {
		for (int i = 0; i < child.getGenomeLength(); i++) {
			if(Math.random() > 0.5){
				child.getGenome()[i] = mother.getGenome()[i];
			}else{
				child.getGenome()[i] = father.getGenome()[i];
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

package pl.tomaszkoska.JiGAI_MutationBehaviours;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;

public class RandomMutation implements MutationBehaviour {

	private GeneticNeuralNet child;
	private int minValue;	// should it be -100 to 1?
	private int maxValue;	//should it be


	public RandomMutation(GeneticNeuralNet child, int minValue, int maxValue) {
		super();
		this.child = child;
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public void mutate(double mutationRate) {

		for (int i = 0; i < child.getGenomeLength(); i++) {
			if(Math.random() < mutationRate){
				child.getGenome()[i] = minValue + Math.random()*(maxValue-minValue);
			}
		}
	}

	public GeneticNeuralNet getChild() {
		return child;
	}

	public void setChild(GeneticNeuralNet child) {
		this.child = child;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

}

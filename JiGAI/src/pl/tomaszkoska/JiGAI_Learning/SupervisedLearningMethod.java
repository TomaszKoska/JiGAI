package pl.tomaszkoska.JiGAI_Learning;

public abstract class SupervisedLearningMethod extends LearningMethod {
	protected double[][] trainargets;
	protected double[][] testTargets;


	public double[][] getTrainargets() {
		return trainargets;
	}
	public void setTrainargets(double[][] trainargets) {
		this.trainargets = trainargets;
	}
	public double[][] getTestTargets() {
		return testTargets;
	}
	public void setTestTargets(double[][] testTargets) {
		this.testTargets = testTargets;
	}



}

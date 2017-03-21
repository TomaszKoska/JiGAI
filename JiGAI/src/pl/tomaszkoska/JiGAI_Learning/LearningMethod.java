package pl.tomaszkoska.JiGAI_Learning;
import pl.tomaszkoska.JiGAI_Base.NeuralNet;

public abstract class LearningMethod {

	protected double[][] trainSet;
	protected double[][] testSet;
	protected NeuralNet neuralNet;

	public abstract double[] runNextEpoch();
	//this function should return double[2]{RMSE train, RMSE test}

	public double[][] getTestSet() {
		return testSet;
	}
	public void setTestSet(double[][] testSet) {
		this.testSet = testSet;
	}
	public double[][] getTrainSet() {
		return trainSet;
	}
	public void setTrainSet(double[][] trainSet) {
		this.trainSet = trainSet;
	}
	public NeuralNet getNeuralNet() {
		return neuralNet;
	}
	public void setNeuralNet(NeuralNet neuralNet) {
		this.neuralNet = neuralNet;
	}


}

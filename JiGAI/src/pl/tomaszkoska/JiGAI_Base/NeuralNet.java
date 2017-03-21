package pl.tomaszkoska.JiGAI_Base;

import pl.tomaszkoska.JiGAI_Learning.LearningMethod;

public class NeuralNet {

	protected LearningMethod learningMethod;

	protected NeuralNetLayer[] layers;
	protected int inputVariableCount;

	protected double[][] prediction; //last calculated prediction
	protected double[][] error; // last calculated error


	public NeuralNet(int[] neuronCounts, int numberOfInputVariables) {
		// we give something like 5,5,5 which is 1 input layer, 1 hidden layer and 1 output layer
		// or 6,6,6,2 - 1 input, 2 hidden and 1 output
		if (neuronCounts.length < 1){
			System.out.println("we need at least one dimention!");
		}
		else{
			layers = new NeuralNetLayer[neuronCounts.length];
			layers[0] = new NeuralNetLayer(neuronCounts[0],numberOfInputVariables);
			for (int i = 1; i < layers.length; i++) {
				layers[i] = new NeuralNetLayer(neuronCounts[i],layers[0]);
			}
			inputVariableCount = numberOfInputVariables;
		}

	}


	public void setActivationFunction(String activationFunctionName){
		for (int i = 0; i < layers.length; i++) {
			layers[i].setActivationFunction(activationFunctionName);
		}
	}

	public void randomizeLayers(){
		for (int i = 0; i < layers.length; i++) {
			layers[i].randomize(-1,1);
		}
	}


	public double[] processInput(double[] inputValues){
		//calculates one row of input
		double[] input = inputValues;

		for (int i = 0; i < layers.length; i++) {
			input = layers[i].processInput(input);
		}
		return input;
	}

	public void train(){
		//TODO: some day please implement the learning algorithm
	}


	public double[][] predict(double[][] inputDataSet){

		if(inputDataSet[0].length != this.getInputVariableCount()){
			System.out.println("Number of input variables doesn't match what the net expects");
		}


		double[][] outcome = new double[inputDataSet.length][this.getOutputNeuronsCount()];
		double[] dataRow = new double[inputVariableCount];
		for (int i = 0; i < inputDataSet.length; i++) {
			for (int j = 0; j < inputVariableCount; j++) {
				dataRow[j] = inputDataSet[i][j];
			}
			for (int j = 0; j < this.getOutputNeuronsCount(); j++) {
				outcome[i] = processInput(dataRow);
			}
		}
		prediction = outcome;
		return outcome;
	}



	public double[][] fullPredict(double[][] inputDataSet, double[][] targetDataSet){

		predict(inputDataSet);
		calculatePredictionError(targetDataSet);

		return prediction;
	}

	public double[][] calculatePredictionError(double[][] targetDataSet){
		double[][] outcome = new double[prediction.length][this.getOutputNeuronsCount()];

		for (int i = 0; i < outcome.length; i++) {
			for (int j = 0; j < outcome[0].length; j++) {
				outcome[i][j] = targetDataSet[i][j]-prediction[i][j];
			}
		}
		error = outcome;
		return outcome;
	}



	public String toString(){
		String outcome = "";
		for (int i = 0; i < layers.length; i++) {
			for (int j = 0; j < layers[i].getNeurons().length; j++) {
				outcome += i + "," + j + "  ";
			}
			outcome += "\n";
		}
		return outcome;
	}

	public String weightsToString(){
		String outcome = "";
		for (int i = 0; i < layers.length; i++) {
			outcome += layers[i];
		}
		return outcome;
	}


	public NeuralNetLayer[] getLayers() {
		return layers;
	}


	public void setLayers(NeuralNetLayer[] layers) {
		this.layers = layers;
	}

	public int getOutputNeuronsCount(){
		return this.layers[this.layers.length-1].getNeuronCount();
	}


	public int getInputVariableCount() {
		return inputVariableCount;
	}


	public void setInputVariableCount(int inputVariableCount) {
		this.inputVariableCount = inputVariableCount;
	}


	public double[][] getPrediction() {
		return prediction;
	}




	public double[][] getError() {
		return error;
	}

	public double[][] getSquaredError() {
		double[][] outcome = new double[error.length][error[0].length];
		for (int i = 0; i < error.length; i++) {
			for (int j = 0; j < error[0].length; j++) {
				outcome[i][j] = error[i][j]*error[i][j];
			}
		}
		return outcome;
	}

	public double[] getRMSE(){
		double[] outcome = new double[error[0].length];
		double[][] sqe = getSquaredError();

		for (int j = 0; j < outcome.length; j++) {
			outcome[j] = 0;
			for (int i = 0; i < error.length; i++) {
					outcome[j] += sqe[i][j];
			}
			outcome[j] = Math.sqrt(outcome[j]/sqe.length);
		}
		return outcome;
	}


	public LearningMethod getLearningMethod() {
		return learningMethod;
	}


	public void setLearningMethod(LearningMethod learningMethod) {
		this.learningMethod = learningMethod;
	}



}

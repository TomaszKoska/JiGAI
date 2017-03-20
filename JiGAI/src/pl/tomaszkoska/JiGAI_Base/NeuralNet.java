package pl.tomaszkoska.JiGAI_Base;



public class NeuralNet {


	private NeuralNetLayer[] layers;
	private int inputVariableCount;
	private double[][] lastForecast;
	private double[][] lastError;


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
		lastForecast = outcome;
		return outcome;
	}



	public double[][] fullPredict(double[][] inputDataSet, double[][] targetDataSet){

		predict(inputDataSet);
		calculatePredictionError(targetDataSet);

		return lastForecast;
	}

	public double[][] calculatePredictionError(double[][] targetDataSet){
		double[][] outcome = new double[lastForecast.length][this.getOutputNeuronsCount()];

		for (int i = 0; i < outcome.length; i++) {
			for (int j = 0; j < outcome[0].length; j++) {
				outcome[i][j] = targetDataSet[i][j]-lastForecast[i][j];
			}
		}
		lastError = outcome;
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


	public double[][] getLastForecast() {
		return lastForecast;
	}




	public double[][] getLastError() {
		return lastError;
	}

	public double[][] getLastSquaredError() {
		double[][] outcome = new double[lastError.length][lastError[0].length];
		for (int i = 0; i < lastError.length; i++) {
			for (int j = 0; j < lastError[0].length; j++) {
				outcome[i][j] = lastError[i][j]*lastError[i][j];
			}
		}
		return outcome;
	}



}

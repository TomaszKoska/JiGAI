package pl.tomaszkoska.JiGAI_Base;

import java.io.Serializable;

import pl.tomaszkoska.JiGAI_Activation.BinarySigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.HyperbolicTangentActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.LinearActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Learning.LearningMethod;
import pl.tomaszkoska.JiGAI_Learning.SupervisedLearningMethodHyperbolicTangent;
import pl.tomaszkoska.JiGAI_Learning.SupervisedLearningMethodSigmoid;


public class NeuralNet implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected LearningMethod learningMethod;

    protected NeuralNetLayer[] layers;
    protected int inputVariableCount;

    protected double[][] prediction; //last calculated prediction
    protected double[][] error; // last calculated error
    protected String activationFunctionShortName;






    public NeuralNet(){

    }

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
                layers[i] = new NeuralNetLayer(neuronCounts[i],layers[i-1]);
            }
            inputVariableCount = numberOfInputVariables;
        }
        activationFunctionShortName = "ht";
        setLearningMethodBasedOnActivationFunctionName();


    }

    private void setLearningMethodBasedOnActivationFunctionName() {
    	if(this.getActivationFunctionShortName().toLowerCase().equals("bs")){
			this.setLearningMethod(new SupervisedLearningMethodSigmoid(this,0.05,0,0,0));
		} else if(this.getActivationFunctionShortName().toLowerCase().equals("s")){
			this.setLearningMethod(new SupervisedLearningMethodSigmoid(this,0.05,0,0,0));
		} else if(this.getActivationFunctionShortName().toLowerCase().equals("ht")){
			this.setLearningMethod(new SupervisedLearningMethodHyperbolicTangent(this,0.05,0,0,0));
		} else{
			this.setLearningMethod(new SupervisedLearningMethodSigmoid(this,0.05,0,0,0));
		}
	}

	public NeuralNet(int[] neuronCounts, int numberOfInputVariables, String activationFunctionShortName) {
        if (neuronCounts.length < 1){
            System.out.println("we need at least one dimention!");
        }
        else{
            layers = new NeuralNetLayer[neuronCounts.length];
            layers[0] = new NeuralNetLayer(neuronCounts[0],numberOfInputVariables);
            for (int i = 1; i < layers.length; i++) {
                layers[i] = new NeuralNetLayer(neuronCounts[i],layers[i-1]);
            }
            inputVariableCount = numberOfInputVariables;
        }
        this.activationFunctionShortName = activationFunctionShortName;
        setLearningMethodBasedOnActivationFunctionName();
    }


    public void setActivationFunction(String activationFunctionName){
        for (int i = 0; i < layers.length; i++) {
            layers[i].setActivationFunction(activationFunctionName);
        }
        setLearningMethodBasedOnActivationFunctionName();
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


    public double[] trainOneEpoch(double[][] inputDataSet, double[][] targetDataSet, boolean shuffle){
    	return learningMethod.trainOneEpoch(inputDataSet, targetDataSet, shuffle);
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
        double[][] outcome = new double[targetDataSet.length][targetDataSet[0].length];

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

    public NeuralNetLayer getInputLayer() {
        return layers[0];
    }

    public NeuralNetLayer getOutputLayer() {
        return layers[layers.length-1];
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


    public String getActivationFunctionShortName() {
        return activationFunctionShortName;
    }

    public void setActivationFunctionShortName(String activationFunctionShortName) {
        this.activationFunctionShortName = activationFunctionShortName;
    }

    public void setActivationFunction(){
        for (int i = 0; i < layers.length; i++) {
        layers[i].setActivationFunction(activationFunctionShortName);
        }
    }

	protected void setLearningMethod(LearningMethod learningMethod) {
		this.learningMethod = learningMethod;
	}




}

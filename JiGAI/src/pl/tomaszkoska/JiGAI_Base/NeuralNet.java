package pl.tomaszkoska.JiGAI_Base;

import java.io.Serializable;

import pl.tomaszkoska.JiGAI_Activation.HyperbolicTangentActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.LinearActivationFunction;
import pl.tomaszkoska.JiGAI_Activation.SigmoidActivationFunction;
import pl.tomaszkoska.JiGAI_Util.Helper;


public class NeuralNet implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected LearningSpecification learningSpecifiaction;

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
        learningSpecifiaction = new LearningSpecification(0.0001,0,0,0);
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
        learningSpecifiaction = new LearningSpecification(0.0001,0,0,0);
    }


    public void setActivationFunction(String activationFunctionName){
    	this.setActivationFunctionShortName(activationFunctionName);
        for (int i = 0; i < layers.length; i++) {
            layers[i].setActivationFunction(activationFunctionName);
        }
    }

    public void randomizeLayers(){
        for (int i = 0; i < layers.length; i++) {
            layers[i].randomize(-1,1);
        }
    }
    public void randomizeLayers(double min, double max){
        for (int i = 0; i < layers.length; i++) {
            layers[i].randomize(min,max);
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


    public double[] trainOneEpochSupervised(double[][] inputDataSet, double[][] targetDataSet, boolean shuffle){
    	double[][] in = inputDataSet;
    	double[][] tar = targetDataSet;

    	if(shuffle){
    		double[][] b = Helper.bindDataset(targetDataSet, inputDataSet);
    		Helper.shuffleArray(b);
    		tar = Helper.splitDataset(b, targetDataSet[0].length,true);
    		in = Helper.splitDataset(b, targetDataSet[0].length,false);
    	}

    	for (int obs = 0; obs < targetDataSet.length; obs++) {	//for each observation
    		trainOneObsSupervised(in[obs], tar[obs]);
    	}
    	this.fullPredict(inputDataSet, targetDataSet);

    	return this.getRMSE();
    }


    public void trainOneObsSupervised(double[] inputData, double[] targetData){

    	this.processInput(inputData);
    	for (int i = 0; i < this.getOutputLayer().getNeurons().length; i++) {
    		Neuron neu = this.getOutputLayer().getNeurons()[i];
    		neu.calculateLastSignalError((targetData[i]-neu.getLastOutput()));
    	}


    	for (int l = this.getLayers().length-2; l >= 0 ; l--) {
    		for (int n = 0; n < this.getLayers()[l].getNeurons().length ; n++) {
    			Neuron neu = this.getLayers()[l].getNeurons()[n];
    			double errorSum = 0;
    			for (int pn = 0; pn < this.getLayers()[l+1].getNeurons().length ; pn++) { //we check the errors on previous layer
    				Neuron pneu = this.getLayers()[l+1].getNeurons()[pn];
    				errorSum = errorSum + pneu.getWeights()[n] * pneu.getLastSignalError();
    			}
    			neu.calculateLastSignalError(errorSum);
    		}
    	}

    	//now all the neurons have their errors calculated

    	for (int l = this.getLayers().length-1; l > 0; l--) {
    		for (int n = 0; n < this.getLayers()[l].getNeurons().length ; n++) {
    			Neuron neu = this.getLayers()[l].getNeurons()[n];
    			//update bias
    			neu.setLastBiasError(
    					learningSpecifiaction.getLearningRate()*neu.getLastSignalError()+
    					learningSpecifiaction.getMomentum()*neu.getLastBiasError()
    					);
    			neu.setBias(neu.getBias()+neu.getLastBiasError());

    			//update the rest of weights
    			for (int w = 0; w < neu.getWeights().length; w++) {
    				neu.setLastWeightDiff(w,
    						learningSpecifiaction.getLearningRate()*
    						neu.getLastSignalError()*
    						this.getLayers()[l-1].getNeurons()[w].getLastOutput()
    						+ learningSpecifiaction.getMomentum()*neu.getLastWeightDiff(w)
    						);

    				neu.setWeight(w,
    						neu.getWeight(w)+neu.getLastWeightDiff(w)
    						);
    			}
    		}
    	}


    	//and the first layer at last
    	for (int n = 0; n < this.getLayers()[0].getNeurons().length ; n++) {
			Neuron neu = this.getLayers()[0].getNeurons()[n];
			//update bias
			neu.setLastBiasError(
					learningSpecifiaction.getLearningRate()*neu.getLastSignalError()+
					learningSpecifiaction.getMomentum()*neu.getLastBiasError()
					);
			neu.setBias(neu.getBias()+neu.getLastBiasError());

			//update the rest of weights
			for (int w = 0; w < neu.getWeights().length; w++) {
				neu.setLastWeightDiff(w,
						learningSpecifiaction.getLearningRate()*
						neu.getLastSignalError()*
						inputData[w]
						+ learningSpecifiaction.getMomentum()*neu.getLastWeightDiff(w)
						);

				neu.setWeight(w,
						neu.getWeight(w)+neu.getLastWeightDiff(w)
						);
			}
		}
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

    public LearningSpecification getLearningSpecifiaction() {
		return learningSpecifiaction;
	}

	public void setLearningSpecifiaction(LearningSpecification learningSpecifiaction) {
		this.learningSpecifiaction = learningSpecifiaction;
	}

}

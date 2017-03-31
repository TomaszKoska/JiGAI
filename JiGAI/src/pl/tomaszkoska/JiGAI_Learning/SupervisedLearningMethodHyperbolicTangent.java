package pl.tomaszkoska.JiGAI_Learning;

import pl.tomaszkoska.JiGAI_Base.NeuralNet;
import pl.tomaszkoska.JiGAI_Base.Neuron;
import pl.tomaszkoska.JiGAI_Util.Helper;

public class SupervisedLearningMethodHyperbolicTangent extends LearningMethod {

	public SupervisedLearningMethodHyperbolicTangent(NeuralNet neuralNet, double learningRate, double learningRateDelta,
			double minLearningRate, double momentum) {
		super(neuralNet, learningRate, learningRateDelta, minLearningRate, momentum);
	}


	public double[] trainOneEpoch(double[][] inputDataSet, double[][] targetDataSet, boolean shuffle){
    	double[][] in = inputDataSet;
    	double[][] tar = targetDataSet;

    	if(shuffle){
    		double[][] b = Helper.bindDataset(targetDataSet, inputDataSet);
    		Helper.shuffleArray(b);
    		tar = Helper.splitDataset(b, targetDataSet[0].length,true);
    		in = Helper.splitDataset(b, targetDataSet[0].length,false);
    	}

    	for (int obs = 0; obs < targetDataSet.length; obs++) {	//for each observation
    		trainOneObs(in[obs], tar[obs]);
    	}
    	neuralNet.fullPredict(inputDataSet, targetDataSet);

    	return neuralNet.getRMSE();
    }


    public void trainOneObs(double[] inputData, double[] targetData){

    	neuralNet.processInput(inputData);
    	for (int i = 0; i < neuralNet.getOutputLayer().getNeurons().length; i++) {
    		Neuron neu = neuralNet.getOutputLayer().getNeurons()[i];
    		neu.setLastSignalError((targetData[i]-neu.getLastOutput())*(1-neu.getLastOutput()*neu.getLastOutput()));
    	}


    	for (int l = neuralNet.getLayers().length-2; l >= 0 ; l--) {
    		for (int n = 0; n < neuralNet.getLayers()[l].getNeurons().length ; n++) {
    			Neuron neu = neuralNet.getLayers()[l].getNeurons()[n];
    			double errorSum = 0;
    			for (int pn = 0; pn < neuralNet.getLayers()[l+1].getNeurons().length ; pn++) { //we check the errors on previous layer
    				Neuron pneu = neuralNet.getLayers()[l+1].getNeurons()[pn];
    				errorSum = errorSum + pneu.getWeights()[n] * pneu.getLastSignalError();
    			}
    			neu.setLastSignalError(errorSum*(1-neu.getLastOutput()*neu.getLastOutput()));
    		}
    	}

    	//now all the neurons have their errors calculated

    	for (int l = neuralNet.getLayers().length-1; l > 0; l--) {
    		for (int n = 0; n < neuralNet.getLayers()[l].getNeurons().length ; n++) {
    			Neuron neu = neuralNet.getLayers()[l].getNeurons()[n];
    			//update bias
    			neu.setLastBiasError(
    					this.getLearningRate()*neu.getLastSignalError()+
    					this.getMomentum()*neu.getLastBiasError()
    					);
    			neu.setBias(neu.getBias()+neu.getLastBiasError());

    			//update the rest of weights
    			for (int w = 0; w < neu.getWeights().length; w++) {
    				neu.setLastWeightDiff(w,
    						this.getLearningRate()*
    						neu.getLastSignalError()*
    						neuralNet.getLayers()[l-1].getNeurons()[w].getLastOutput()
    						+ this.getMomentum()*neu.getLastWeightDiff(w)
    						);

    				neu.setWeight(w,
    						neu.getWeight(w)+neu.getLastWeightDiff(w)
    						);
    			}
    		}
    	}


    	//and the first layer at last
    	for (int n = 0; n < neuralNet.getLayers()[0].getNeurons().length ; n++) {
			Neuron neu = neuralNet.getLayers()[0].getNeurons()[n];
			//update bias
			neu.setLastBiasError(
					this.getLearningRate()*neu.getLastSignalError()+
					this.getMomentum()*neu.getLastBiasError()
					);
			neu.setBias(neu.getBias()+neu.getLastBiasError());

			//update the rest of weights
			for (int w = 0; w < neu.getWeights().length; w++) {
				neu.setLastWeightDiff(w,
						this.getLearningRate()*
						neu.getLastSignalError()*
						inputData[w]
						+ this.getMomentum()*neu.getLastWeightDiff(w)
						);

				neu.setWeight(w,
						neu.getWeight(w)+neu.getLastWeightDiff(w)
						);
			}
		}
    }


}

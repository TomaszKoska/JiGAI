package pl.tomaszkoska.JiGAI_test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.NeuralNet;
import pl.tomaszkoska.JiGAI_Dataset.Dataset;
import pl.tomaszkoska.JiGAI_Util.Helper;

public class ForecastingEngine {

	ArrayList<NeuralNet> nets;
	ArrayList<Dataset> subsamples;

	Dataset trainingData;
	Dataset testingData;
	int targetCount;
	int inputCount;

	public ForecastingEngine(){
		nets = new ArrayList<NeuralNet>();
		subsamples = new ArrayList<Dataset>();
	}


	public void loadData(String csvPath, int inputCount, int targetCount, int trainingRows){

		double[][] f = Helper.loadDataFromCSV(csvPath);
		//f = Helper.normalizeData(f);
		Dataset d = new Dataset(f, targetCount);
//		System.out.println("train rows: " + trainingRows);
//		System.out.println("od 0 do " + (trainingRows-1));
//		System.out.println("od "+ trainingRows + " do " + d.xs.length);

		this.trainingData =  d.getSubset(0,trainingRows-1);
		this.testingData =  d.getSubset(trainingRows, d.xs.length);

		this.inputCount = inputCount;
		this.targetCount = targetCount;
	}


	public void makeNewNets(int howManyNewNets,
				int[] architecture,
				String activation,
				double learningRate,
				double momentum
				){

		for (int i = 0; i < howManyNewNets; i++) {
			NeuralNet nn = new NeuralNet(architecture,inputCount);
			nn.setActivationFunction(activation);
			nn.getLearningSpecifiaction().setLearningRate(learningRate);
			nn.getLearningSpecifiaction().setMomentum(momentum);
			nn.randomizeLayers(-0.2,0.2);
			nets.add(nn);
//			System.out.println(nn.weightsToString());
		}
	}


	public void makeSubsampleForEachNet(){

		for (int n = 0; n < nets.size(); n++) {
			Dataset subsample = trainingData.getRandomSubset(trainingData.xs.length, true);
			subsamples.add(subsample);
		}

	}



	public void trainNets(int iterations, int maxTurning){
		double thisRMSE=0;
		double lastRMSE=999;
		for (int n = 0; n < nets.size(); n++) {

			NeuralNet nn = nets.get(n);
			Dataset thisSet = subsamples.get(n);
			Dataset d = new Dataset(thisSet.ys,thisSet.xs);

			int howManyTurning = 0;
			lastRMSE = 999;
			thisRMSE = 0;
			for(int i=0;i<iterations;i++){
//				Helper.printDataSet(tar);
//				Helper.printDataSet(in);
				double[] x = nn.trainOneEpochSupervisedWithValidation(trainingData,testingData,true);
//				nn.fullPredict(testingData, false);
				double[] x2 = nn.getRMSE(false);
				thisRMSE = 0;
				double lastRMSEValidation = 999;
				double thisRMSEValidation = 0;
				for (int j = 0; j < x.length; j++) {
					thisRMSE += x[j];
					thisRMSEValidation+= x2[j];
				}
				thisRMSE = thisRMSE/x.length;
				thisRMSEValidation = thisRMSEValidation/x2.length;

				if (thisRMSEValidation > lastRMSEValidation){
					howManyTurning++;
				}else{
					howManyTurning--;
					if(howManyTurning<0){howManyTurning=0;}
				}
				lastRMSE = thisRMSE;
				lastRMSEValidation = thisRMSEValidation;
				System.out.println("net: " + n + ", rmses in training: " + nn.getRMSE(true)[0]);
				System.out.println("net: " + n + ", rmses in testing: " + nn.getRMSE(false)[0]);
				if(howManyTurning > maxTurning ){
					System.out.println("Things turn!");
					break;
				}



				}
			System.out.println(n + " finished!" + thisRMSE);
		}

		Iterator<NeuralNet> n = nets.iterator();
		while (n.hasNext()) {
		   NeuralNet nn = n.next(); // must be called before you can call i.remove()
		   if(Double.isNaN(thisRMSE)){
			   n.remove();
			}
		}

	}

	public void saveNets(String csvNetsPath,String csvDataPath){
		try (
				OutputStream file = new FileOutputStream(csvNetsPath);
				OutputStream buffer = new BufferedOutputStream(file);
				ObjectOutput output = new ObjectOutputStream(buffer);
				OutputStream file2 = new FileOutputStream(csvDataPath);
				OutputStream buffer2 = new BufferedOutputStream(file2);
				ObjectOutput output2 = new ObjectOutputStream(buffer2);
				){
			output.writeObject(nets);
			output2.writeObject(subsamples);
			output.close();
			file.close();
			output2.close();
			file2.close();
		}catch(IOException ex){
			System.out.println("O M G");
			ex.printStackTrace();
		}


	}
	public void loadNets(String serPathNet, String serPathData){
		try(
				InputStream file = new FileInputStream(serPathNet);
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream (buffer);
				InputStream file2 = new FileInputStream(serPathData);
				InputStream buffer2 = new BufferedInputStream(file2);
				ObjectInput input2 = new ObjectInputStream (buffer2);
				){
			nets = (ArrayList<NeuralNet>)input.readObject();
			subsamples = (ArrayList<Dataset>)input2.readObject();
			input.close();
			file.close();
			input2.close();
			file2.close();
		}
		catch(ClassNotFoundException ex){
			System.out.println("O M G");
			ex.printStackTrace();
		}
		catch(IOException ex){
			System.out.println("O M G");
			ex.printStackTrace();
		}
	}


	public ArrayList<NeuralNet> getNets() {
		return nets;
	}


	public void setNets(ArrayList<NeuralNet> nets) {
		this.nets = nets;
	}



	public int getTargetCount() {
		return targetCount;
	}


	public void setTargetCount(int targetCount) {
		this.targetCount = targetCount;
	}


	public int getInputCount() {
		return inputCount;
	}


	public void setInputCount(int inputCount) {
		this.inputCount = inputCount;
	}

	public double[][] predict(double[][] tar, double [][] in){
		double[][] avg = new double[tar.length][tar[0].length];
		for (int i = 0; i < avg.length; i++) {
			for (int j = 0; j < avg[0].length; j++) {
				avg[i][j] = 0;
			}
		}


		for (Iterator<NeuralNet> iterator = nets.iterator(); iterator.hasNext();) {
			NeuralNet nn = (NeuralNet) iterator.next();
			nn.predict(in,false);

			for (int i = 0; i < avg.length; i++) {
				for (int j = 0; j < avg[0].length; j++) {
					avg[i][j] += nn.getPrediction()[i][j];
				}
			}
		}
		for (int i = 0; i < avg.length; i++) {
			for (int j = 0; j < avg[0].length; j++) {
				avg[i][j] = avg[i][j]/nets.size();
			}
		}

		return avg;
	}

	public void saveForecastsToCSV(String directoryWithSlash){
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Date date = new Date();
		String timeForFileName = dateFormat.format(date);

		double[][] forecastSet = new double[nets.get(0).getPrediction().length][nets.size()];

			for (int i = 0; i < nets.get(0).getPrediction()[0].length; i++) {
				String fileName = timeForFileName + "_" +  i;
				for (int n = 0; n < nets.size(); n++) {
					for (int p = 0; p < nets.get(0).getPrediction().length; p++) {
						forecastSet[p][n] = nets.get(n).getPrediction()[p][i];
					}
				}
				Helper.saveAsCSV(forecastSet, ( directoryWithSlash + fileName + ".csv"));
			}
	}


	public ArrayList<Dataset> getSubsamples() {
		return subsamples;
	}


	public void setSubsamples(ArrayList<Dataset> subsamples) {
		this.subsamples = subsamples;
	}


	public Dataset getTrainingData() {
		return trainingData;
	}


	public void setTrainingData(Dataset trainingData) {
		this.trainingData = trainingData;
	}


	public Dataset getTestingData() {
		return testingData;
	}


	public void setTestingData(Dataset testingData) {
		this.testingData = testingData;
	}






}

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
import java.util.ArrayList;
import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.NeuralNet;
import pl.tomaszkoska.JiGAI_Util.Helper;

public class ForecastingEngine {

	ArrayList<NeuralNet> nets;
	ArrayList<double[][]> subsamples;

	double[][] fullData;
	int targetCount;
	int inputCount;

	public ForecastingEngine(){
		nets = new ArrayList<NeuralNet>();
		subsamples = new ArrayList<double[][]>();
	}


	public void loadData(String csvPath, int inputCount, int targetCount){

		fullData = Helper.loadDataFromCSV(csvPath);
		fullData = Helper.normalizeData(fullData);
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
			nn.getLearningMethod().setLearningRate(learningRate);
			nn.getLearningMethod().setMomentum(momentum);
			nn.randomizeLayers(-0.2,0.2);
			nets.add(nn);
//			System.out.println(nn.weightsToString());
		}

	}


	public void makeSubsampleForEachNet(){

		for (int n = 0; n < nets.size(); n++) {
			double [][] subsample = Helper.getSubset(fullData, fullData.length);
			subsamples.add(subsample);
		}

	}



	public void trainNets(int iterations){
		double thisRMSE=0;
		double lastRMSE=999;
		for (int n = 0; n < nets.size(); n++) {

			NeuralNet nn = nets.get(n);
			double[][] s = subsamples.get(n);
			double [][] in = Helper.splitDataset(s, targetCount, false);
			double [][] tar = Helper.splitDataset(s, targetCount, true);

			int howManyTurning = 0;
			lastRMSE = 999;
			thisRMSE = 0;
			for(int i=0;i<iterations;i++){
//				Helper.printDataSet(tar);
//				Helper.printDataSet(in);
				double[] x = nn.trainOneEpoch(in,tar,true);
				thisRMSE = 0;
				for (int j = 0; j < x.length; j++) {
					thisRMSE += x[j];
				}
				thisRMSE = thisRMSE/x.length;

				if (thisRMSE > lastRMSE){
					howManyTurning++;
				}else{
					howManyTurning=0;
				}
				lastRMSE = thisRMSE;
				System.out.println("rmses in training: " + x[0]);

				if(howManyTurning > 10 ){
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
			subsamples = (ArrayList<double[][]>)input2.readObject();
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


	public ArrayList<double[][]> getSubsamples() {
		return subsamples;
	}


	public void setSubsamples(ArrayList<double[][]> subsamples) {
		this.subsamples = subsamples;
	}


	public double[][] getFullData() {
		return fullData;
	}


	public void setFullData(double[][] fullData) {
		this.fullData = fullData;
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
			nn.predict(in);

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


}

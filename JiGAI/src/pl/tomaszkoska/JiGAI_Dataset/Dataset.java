package pl.tomaszkoska.JiGAI_Dataset;

import pl.tomaszkoska.JiGAI_Util.Helper;

public class Dataset {

	public double[][] xs;
	public double[][] ys;
	public double[][] errorWeights;

	public Dataset(double[][] fullData, int targetCount){
		xs = Helper.splitDataset(fullData, targetCount, false);
		ys = Helper.splitDataset(fullData, targetCount, true);
		errorWeights= new double[ys.length][ys[0].length];
		setWeightsToOne();
	}

	public Dataset(String csvPath, int targetCount){
		double[][] fullData= Helper.loadDataFromCSV(csvPath);
		xs = Helper.splitDataset(fullData, targetCount, false);
		ys = Helper.splitDataset(fullData, targetCount, true);
		errorWeights= new double[ys.length][ys[0].length];
		setWeightsToOne();
	}

	public void setWeightsToOne(){
		for (int i = 0; i < errorWeights.length; i++) {
			for (int j = 0; j < errorWeights[0].length; j++) {
				errorWeights[i][j] = 1;
			}
		}
	}

	public void changeWeightsBasedOnPrediction(){
		//TODO: implement this
	}





}

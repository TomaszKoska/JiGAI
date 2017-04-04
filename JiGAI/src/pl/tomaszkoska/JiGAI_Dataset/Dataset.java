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
	}



}

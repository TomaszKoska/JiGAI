package pl.tomaszkoska.JiGAI_Dataset;

import pl.tomaszkoska.JiGAI_Util.Helper;

public class Dataset {

	public double[][] xs;
	public double[][] ys;
	public double[][] weights;

	public Dataset(double[][] fullData, int targetCount){
		this.xs = Helper.splitDataset(fullData, targetCount, false);
		this.ys = Helper.splitDataset(fullData, targetCount, true);
		this.weights= new double[ys.length][ys[0].length];
		setWeightsToOne();
	}

	public Dataset(double[][] ys,double[][] xs){
		this.xs = xs;
		this.ys = ys;
		this.weights= new double[ys.length][1];
		setWeightsToOne();
	}

	public Dataset(double[][] ys,double[][] xs,double[][] w){
		this.xs = xs;
		this.ys = ys;
		this.weights= w;
	}


	public Dataset(String csvPath, int targetCount){
		double[][] fullData= Helper.loadDataFromCSV(csvPath);
		xs = Helper.splitDataset(fullData, targetCount, false);
		ys = Helper.splitDataset(fullData, targetCount, true);
		weights= new double[ys.length][1];
		setWeightsToOne();
	}

	public void setWeightsToOne(){
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[0].length; j++) {
				weights[i][j] = 1;
			}
		}
	}


	public void printXs(){
		Helper.printDataSet(xs);
		System.out.println("");
	}
	public void printYs(){
		Helper.printDataSet(ys);
		System.out.println("");
	}
	public void printWeights(){
		Helper.printDataSet(weights);
		System.out.println("");
	}

	public void normalizeXs(){
		xs=Helper.normalizeData(this.xs);
	}
	public void normalizeYs(){
		ys=Helper.normalizeData(this.ys);
	}
	public void normalizeAll(){
		normalizeXs();
		normalizeYs();
	}

	public void shuffle(){
		double [][] binded;// = new double[xs.length][xs[0].length+ys[0].length+weights.length];
		binded = Helper.bindDataset(weights, ys);
		binded = Helper.bindDataset(binded,xs);
		Helper.shuffleArray(binded);
		double [][] tmp = Helper.splitDataset(binded, weights[0].length + ys[0].length, true);
		xs = Helper.splitDataset(binded, weights[0].length + ys[0].length, false);
		weights = Helper.splitDataset(tmp, weights[0].length, true);
		ys = Helper.splitDataset(tmp, weights[0].length, false);
	}

	public Dataset getSubset(int rows, boolean repetition){
		double [][] binded;// = new double[xs.length][xs[0].length+ys[0].length+weights.length];
		binded = Helper.bindDataset(weights, ys);
		binded = Helper.bindDataset(binded,xs);

		double[][] chosen = Helper.getSubset(binded, rows, repetition);

		double [][] tmp = Helper.splitDataset(chosen, weights[0].length + ys[0].length, true);
		double [][] newXs = Helper.splitDataset(chosen, weights[0].length + ys[0].length, false);
		double [][] newWeights = Helper.splitDataset(tmp, weights[0].length, true);
		double [][] newYs = Helper.splitDataset(tmp, weights[0].length, false);

		return new Dataset(newYs,newXs,newWeights);
	}

	public void changeWeightsBasedOnPrediction(){
		//TODO: implement this
	}

}

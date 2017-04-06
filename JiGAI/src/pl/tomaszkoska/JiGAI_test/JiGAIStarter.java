package pl.tomaszkoska.JiGAI_test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pl.tomaszkoska.JiGAI_Util.Helper;

public class JiGAIStarter {

	public static void main(String[] args) {
		//NeuronTester.runTest();
		//NeuralNetLayerTester.runTest();
		//NeuralNetTester.runTest();
//		GeneticNeuralNetTester.runTest();
//		GeneticEngineTester.runTest();
//		GeneticEngineTester.runTest2();
//		GeneticEngineTester.runTest3();
//		GeneticEngineTester.runTest4();
//		HelperTester.runTest();
		LearningTest.runTest();
//		SaveLoadTester.runTest();
//		SaveLoadTester.runTest2();
//		DatasetTester.runTest();


//		startNewForecasting();
//		loadNetsAndForecast();
	}

	public static void startNewForecasting(){
		ForecastingEngine fe = new ForecastingEngine();
		fe.loadData("D:\\test\\nn\\pkb_data_bin.csv",12,1);
		fe.makeNewNets(12, new int[]{5,1}, "th", 0.00005,0);
		fe.makeSubsampleForEachNet();
		System.out.println("Data basic info:");
		System.out.println(fe.getFullData().length);
		System.out.println(fe.getFullData()[0].length);
		System.out.println("targets: " + fe.targetCount + "  inputs: " + fe.inputCount);

		fe.trainNets(1000000,10);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Date date = new Date();
		String timeForFileName = dateFormat.format(date);

		fe.saveNets("D:\\test\\nn\\nets.ser",
					"D:\\test\\nn\\subamples.ser");
		fe.saveNets("D:\\test\\nn\\nets" + timeForFileName + ".ser",
					"D:\\test\\nn\\subamples" + timeForFileName + ".ser");
		fe.saveForecastsToCSV("D:\\test\\nn\\");

	}
	public static void loadNetsAndForecast(){
		ForecastingEngine fe = new ForecastingEngine();
		fe.loadNets("D:\\test\\nn\\nets.ser", "D:\\test\\nn\\subamples.ser");
		fe.loadData("D:\\test\\nn\\pkb_data.csv",12,1);
		double [][] in = Helper.splitDataset(fe.getFullData(), 1,false);
		double [][] tar = Helper.splitDataset(fe.getFullData(), 1,true);

		Helper.printDataSet(fe.predict(tar,in));
	}

}

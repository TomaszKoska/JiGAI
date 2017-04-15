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
//		WeightsTester.runTest();

//		startNewForecasting();
//		loadNetsAndForecast();
	}

	public static void startNewForecasting(){
		ForecastingEngine fe = new ForecastingEngine();
		fe.loadData("C:\\Users\\Tomek\\Desktop\\kaggle\\tit2.csv",24,2,500);

		fe.getTrainingData().printXs();
		fe.makeNewNets(1, new int[]{2}, "s", 0.000001,0);

		fe.makeSubsampleForEachNet();
		System.out.println("Data basic info:");
		System.out.println(fe.getTrainingData().xs.length);
		System.out.println(fe.getTrainingData().xs[0].length);
		System.out.println("targets: " + fe.targetCount + "  inputs: " + fe.inputCount);

		fe.trainNets(10000,10);

		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd-HHmmss");
		Date date = new Date();
		String timeForFileName = dateFormat.format(date);

		fe.saveNets("D:\\test\\nn\\nets.ser",
					"D:\\test\\nn\\subsamples.ser");
		fe.saveNets("D:\\test\\nn\\nets" + timeForFileName + ".ser",
					"D:\\test\\nn\\subsamples" + timeForFileName + ".ser");
		fe.saveForecastsToCSV("C:\\Users\\Tomek\\Desktop\\kaggle\\");

	}
	public static void loadNetsAndForecast(){
		ForecastingEngine fe = new ForecastingEngine();
		fe.loadNets("D:\\test\\nn\\nets.ser", "D:\\test\\nn\\subamples.ser");
		fe.loadData("C:\\Users\\Tomek\\Desktop\\kaggle\\tit2.csv",24,2,100);

		Helper.printDataSet(fe.predict(fe.getTrainingData().ys,fe.getTrainingData().xs));
	}

}

package pl.tomaszkoska.JiGAI_test;
import pl.tomaszkoska.JiGAI_Base.NeuralNet;
import pl.tomaszkoska.JiGAI_Dataset.Dataset;


public class LearningTest {
	public static double[][] XORinput
	= new double[][]{{1,1},{0,1},{1,0},{0,0}};

	public static double[][] XORtarget =new double[][]{{0,1},
			{1,0},
			{1,0},
			{0,1}};
	public static Dataset XORdataset = new Dataset(XORtarget,XORinput);
	public static double[][] basic3Input
			= new double[][]{{1,1,1},{1,1,0},{1,0,1},{0,1,1},{1,0,0},{0,1,0},{0,0,1},{0,0,0}};

	public static double[][] basic3target =
			new double[][]{{1,1,1},{1,1,0},{1,0,1},{0,1,1},{1,0,0},{0,1,0},{0,0,1},{0,0,0}};
	public static Dataset basic3dataset = new Dataset(basic3target,basic3Input);

	public static void runTest2(){
		Dataset d = basic3dataset;
		int[] architecture = new int[]{10,3};
		NeuralNet nn = new NeuralNet(architecture,d.xs[0].length);
		nn.setActivationFunction("p");
		nn.getLearningSpecifiaction().setLearningRate(0.1);
		nn.getLearningSpecifiaction().setLearningRateDelta(0.0000);
		nn.getLearningSpecifiaction().setMinLearningRate(0.000);


		nn.randomizeLayers();

//		System.out.println(nn.weightsToString());
		nn.fullPredict(d,true);
		System.out.println(nn.getRMSE(true)[0] + " , " + nn.getRMSE(true)[1] + "\n");

		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + nn.getPrediction()[j][0]
					+", " + nn.getPrediction()[j][1]);
		}

		System.out.println("\n\n\n\n");

		System.out.println(nn.weightsToString());

		for(int i=0;i<100000;i++){
		double[] x = nn.trainOneEpochSupervised(d,false);
		System.out.println("rmses in training: " + x[0] + "  , " + x[1]+ "  , " + x[2]);
		}

		System.out.println("\n\n\n\n");
		System.out.println(nn.weightsToString());

//		System.out.println(nn.weightsToString());
		nn.fullPredict(d,false);
		System.out.println(nn.getRMSE(false)[0] + " , " + nn.getRMSE(false)[1]+ "\n");


		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + Math.round(nn.getPrediction()[j][0])
					+", " + Math.round(nn.getPrediction()[j][1])
					+", " + Math.round(nn.getPrediction()[j][2]));
		}
	}

	public static void runTest(){
		Dataset d = XORdataset;
		int[] architecture = new int[]{4,2};
		NeuralNet nn = new NeuralNet(architecture,d.xs[0].length);
		nn.randomizeLayers();
		nn.getLearningSpecifiaction().setLearningRate(0.1);
		nn.setActivationFunction("p");


//		System.out.println(nn.weightsToString());
		nn.fullPredict(d,true);
		System.out.println(nn.getRMSE(true)[0] + " , " + nn.getRMSE(true)[1] + "\n");

		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + nn.getPrediction()[j][0]
					+", " + nn.getPrediction()[j][1]);
		}

		System.out.println("\n\n\n\n");

		for(int i=0;i<1000;i++){
		double[] x = nn.trainOneEpochSupervised(d,true);
		System.out.println(x[0] + "  , " + x[1]);
		}

//		System.out.println(nn.weightsToString());
		nn.fullPredict(d,false);
		System.out.println(nn.getRMSE(false)[0] + " , " + nn.getRMSE(false)[1]+ "\n");


		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" +  Math.round(nn.getPrediction()[j][0])
					+", " +  Math.round(nn.getPrediction()[j][1]));
		}
		d.printYs();

	}
}

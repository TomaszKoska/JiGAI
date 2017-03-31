package pl.tomaszkoska.JiGAI_test;
import pl.tomaszkoska.JiGAI_Base.NeuralNet;


public class LearningTest {
	public static double[][] XORinput
	= new double[][]{{1,1},{0,1},{1,0},{0,0}};

	public static double[][] XORtarget =new double[][]{{0,1},
			{1,0},
			{1,0},
			{0,1}};

	public static double[][] basic3Input
			= new double[][]{{1,1,1},{1,1,0},{1,0,1},{0,1,1},{1,0,0},{0,1,0},{0,0,1},{0,0,0}};

	public static double[][] basic3target =
			new double[][]{{1,1,1},{1,1,0},{1,0,1},{0,1,1},{1,0,0},{0,1,0},{0,0,1},{0,0,0}};

	public static void runTest2(){
		double[][] in = basic3Input;
		double[][] tar = basic3target;
		int[] architecture = new int[]{10,10,3};
		NeuralNet nn = new NeuralNet(architecture,in[0].length);
		nn.setActivationFunction("ht");
		nn.getLearningMethod().setLearningRate(0.1);
		nn.getLearningMethod().setMomentum(0);
		nn.randomizeLayers();

//		System.out.println(nn.weightsToString());
		nn.fullPredict(in, tar);
		System.out.println(nn.getRMSE()[0] + " , " + nn.getRMSE()[1] + "\n");

		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + nn.getPrediction()[j][0]
					+", " + nn.getPrediction()[j][1]);
		}

		System.out.println("\n\n\n\n");

		for(int i=0;i<100;i++){
		double[] x = nn.trainOneEpoch(in, tar,false);
		System.out.println(x[0] + "  , " + x[1]);
		}

//		System.out.println(nn.weightsToString());
		nn.fullPredict(in, tar);
		System.out.println(nn.getRMSE()[0] + " , " + nn.getRMSE()[1]+ "\n");


		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + Math.round(nn.getPrediction()[j][0])
					+", " + Math.round(nn.getPrediction()[j][1])
					+", " + Math.round(nn.getPrediction()[j][2]));
		}
	}

	public static void runTest(){
		double[][] in = XORinput;
		double[][] tar = XORtarget;
		int[] architecture = new int[]{4,4,2};
		NeuralNet nn = new NeuralNet(architecture,in[0].length);
		nn.setActivationFunction("ht");
		nn.getLearningMethod().setLearningRate(0.25);
		nn.getLearningMethod().setMomentum(0);
		nn.randomizeLayers();

//		System.out.println(nn.weightsToString());
		nn.fullPredict(in, tar);
		System.out.println(nn.getRMSE()[0] + " , " + nn.getRMSE()[1] + "\n");

		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + nn.getPrediction()[j][0]
					+", " + nn.getPrediction()[j][1]);
		}

		System.out.println("\n\n\n\n");

		for(int i=0;i<10000;i++){
		double[] x = nn.trainOneEpoch(in, tar,true);
		System.out.println(x[0] + "  , " + x[1]);
		}

//		System.out.println(nn.weightsToString());
		nn.fullPredict(in, tar);
		System.out.println(nn.getRMSE()[0] + " , " + nn.getRMSE()[1]+ "\n");


		for (int j = 0; j < nn.getPrediction().length; j++) {
			System.out.println("" + nn.getPrediction()[j][0]
					+", " + nn.getPrediction()[j][1]);
		}
	}
}

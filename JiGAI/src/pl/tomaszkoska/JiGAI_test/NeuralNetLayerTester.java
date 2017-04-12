package pl.tomaszkoska.JiGAI_test;


import pl.tomaszkoska.JiGAI_Base.NeuralNetLayer;

public class NeuralNetLayerTester {
	public static void runTest(){
//		NeuralNetLayer layer = new NeuralNetLayer(3);
//		layer.getNeurons()[0] = new Neuron(new double[]{0.5,0.5,0.5},new NeutralActivationFunction());
//		layer.getNeurons()[1] = new Neuron(new double[]{0.1,0.1,0.1},new NeutralActivationFunction());
//		layer.getNeurons()[2] = new Neuron(new double[]{0,0,1},new NeutralActivationFunction());
//
//		System.out.println(layer);
//
//
//
//		double[] layerOutput = layer.processInput(new double[]{2,1,2});
//
//		for (int i = 0; i < layerOutput.length; i++) {
//			System.out.println(layerOutput[i]);
//		}

		NeuralNetLayer layer = new NeuralNetLayer(2, 2);
		NeuralNetLayer layer2 = new NeuralNetLayer(2, layer);
		NeuralNetLayer layer3 = new NeuralNetLayer(1, layer2);

		layer.randomize(-1,1);
		layer2.randomize(-1,1);
		layer3.randomize(-1,1);
		layer.setActivationFunction("s");
		layer2.setActivationFunction("s");
		layer3.setActivationFunction("bs");

		System.out.println(layer);
		System.out.println("*****");
		System.out.println(layer2);
		System.out.println("*****");
		System.out.println(layer3);

		System.out.println(layer.getNeuronCount());
		System.out.println(layer2.getNeuronCount());
		System.out.println(layer3.getNeuronCount());


		System.out.println("layer 1 output: " + layer.processInput(new double[]{1,1},false)[0]);
		System.out.println("layer 1 output: " + layer.processInput(new double[]{1,1},false)[1]);

		System.out.println("layer 2 output: " + layer2.processInput(layer.processInput(new double[]{1,1},false),false)[0]);
		System.out.println("layer 2 output: " + layer2.processInput(layer.processInput(new double[]{1,1},false),false)[1]);


		double[] outcome = layer3.processInput(
				layer2.processInput(
						layer.processInput(new double[]{1,1},false),false),false);
		System.out.println(outcome[0]);

	}
}

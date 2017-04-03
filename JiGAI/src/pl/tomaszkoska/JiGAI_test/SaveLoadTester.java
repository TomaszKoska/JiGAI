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

import pl.tomaszkoska.JiGAI_Base.NeuralNet;

public class SaveLoadTester {
	public static double[][] XORinput
	= new double[][]{{1,1},{0,1},{1,0},{0,0}};

	public static double[][] XORtarget =new double[][]{{0,1},
			{1,0},
			{1,0},
			{0,1}};


	public static void runTest(){
		double[][] in = XORinput;
		double[][] tar = XORtarget;
		int[] architecture = new int[]{4,4,2};
		NeuralNet nn = new NeuralNet(architecture,in[0].length);
		nn.randomizeLayers();
		nn.setActivationFunction("ht");
		nn.getLearningMethod().setLearningRate(0.05);
		nn.getLearningMethod().setMomentum(0);

		System.out.println(nn.weightsToString());
		 		try (
			      OutputStream file = new FileOutputStream("D:\\test\\testingNet.ser");
			      OutputStream buffer = new BufferedOutputStream(file);
			      ObjectOutput output = new ObjectOutputStream(buffer);
			    ){
			      output.writeObject(nn);
			    }catch(IOException ex){
			    	System.out.println("O M G");
			    	ex.printStackTrace();
			    }
	}

	public static void runTest2(){
		NeuralNet nn = null;
		try(
				InputStream file = new FileInputStream("D:\\test\\testingNet.ser");
				InputStream buffer = new BufferedInputStream(file);
				ObjectInput input = new ObjectInputStream (buffer);
				){
			nn = (NeuralNet)input.readObject();
		}
		catch(ClassNotFoundException ex){
			System.out.println("O M G");
			ex.printStackTrace();
		}
		catch(IOException ex){
			System.out.println("O M G");
			ex.printStackTrace();
		}
		System.out.println(nn);
		System.out.println(nn.weightsToString());


	}


}

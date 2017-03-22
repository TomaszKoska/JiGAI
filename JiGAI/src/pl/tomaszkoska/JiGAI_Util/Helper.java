package pl.tomaszkoska.JiGAI_Util;

import java.util.Random;

public class Helper {
	public static void shuffleArray(double[] array)
	{
	    int index;
	    double temp;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}
}

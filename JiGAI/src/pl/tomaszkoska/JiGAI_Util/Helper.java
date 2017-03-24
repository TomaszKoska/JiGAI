package pl.tomaszkoska.JiGAI_Util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.opencsv.CSVReader;


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

	public static double[][] loadDataFromCSV(String csvFile){
		double[][] output;
		ArrayList<String[]> lines = new ArrayList<String[]>();

		 CSVReader reader = null;
	        try {
	            reader = new CSVReader(new FileReader(csvFile));
	            String[] line;

	            while ((line = reader.readNext()) != null) {
	            	   lines.add(line);
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        output = new double[lines.size()][lines.get(0).length];

	        int j=0;
	        for (Iterator<String[]> iterator = lines.iterator(); iterator.hasNext();) {
				String[] line = (String[]) iterator.next();
				for (int i = 0; i < line.length; i++) {
					output[j][i] = Double.parseDouble(line[i]);
				}
				j++;
			}

		return output;
	}
	public static void printDataSet(double[][] dataSet){
		for (int i = 0; i < dataSet.length; i++) {
			String line = "";
			for (int j = 0; j < dataSet[i].length; j++) {
				line += dataSet[i][j];
				if(j <dataSet[i].length-1){
					line += ",";
				}
			}
			System.out.println(line);
		}
	}


	public static double[][] getSubset(double[][] source, int rows){
		double[][] subset = new double[rows][source[0].length];

		for (int i = 0; i < subset.length; i++) {
			int randomNum = ThreadLocalRandom.current().nextInt(0, source.length);
			for (int j = 0; j < subset[i].length; j++) {
				subset[i][j] = source[randomNum][j];
			}
		}
		return subset;
	}

	public static double[][] normalizeData(double[][] source){
		double[][] normalized = new double[source.length][source[0].length];
		double[] max = new double [source[0].length];
		double[] min = new double [source[0].length];


			for (int j = 0; j < max.length; j++) {
				max[j]=Double.MIN_VALUE;
				min[j]=Double.MAX_VALUE;
				for (int i = 0; i < source.length; i++) {
					if(source[i][j] > max[j]){
						max[j] = source[i][j];
					}
					if(source[i][j] < min[j]){
						min[j] = source[i][j];
					}
				}
			}

			for (int i = 0; i < normalized.length; i++) {
				for (int j = 0; j < normalized[0].length; j++) {
					normalized[i][j] = (source[i][j]-min[j])/(max[j]-min[j]);
				}
			}

		return normalized;
	}

	public static double[][] splitDataset(double[][] source, int targetCount, boolean target){

		double[][] outcome;
		int outcomeCount = 0;
		if(target){
			outcomeCount = targetCount;
			outcome = new double[source.length][outcomeCount];
			for (int i = 0; i < outcome.length; i++) {
				for (int j = 0; j < outcome[i].length; j++) {
					outcome[i][j] = source[i][j];
				}
			}
		}else{
			outcomeCount =source[0].length-targetCount;
			outcome = new double[source.length][outcomeCount];
			for (int i = 0; i < outcome.length; i++) {
				for (int j = 0; j < outcome[i].length; j++) {
					outcome[i][j] = source[i][j+targetCount];
				}
			}
		}


		return outcome;
	}



}

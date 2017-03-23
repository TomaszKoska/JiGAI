package pl.tomaszkoska.JiGAI_test;


import java.util.Iterator;

import pl.tomaszkoska.JiGAI_Base.GeneticEngine;
import pl.tomaszkoska.JiGAI_Base.GeneticNeuralNet;
import pl.tomaszkoska.JiGAI_KillingBehaviours.DecreasingChanceOfSurvival;
import pl.tomaszkoska.JiGAI_KillingBehaviours.TopSurvives;
import pl.tomaszkoska.JiGAI_MutationBehaviours.AlterationMutation;
import pl.tomaszkoska.JiGAI_ReproductionBehaviours.ReproductionBehaviour;

public class GeneticEngineTester {


	public static double[][] inputDataSet
	= new double[][]
			{{0.6,-0.5,0.9,-0.3,0.8,0.4,0.9,0.9,1,0,-0.6,-0.5,0,0.2,0.9,0.5,0.3,0.1,0.2,-3.7},
		{0.4,-1.7,0.6,0.1,-0.3,0.6,-0.1,-0.5,0.4,0.1,0.3,0,0.1,0.1,1.3,0,0.1,0,0.6,-0.5},
		{-0.3,-0.2,-0.4,0,1.1,0.8,0,-0.2,1.1,0.1,0,0.2,0.1,0.5,2.6,0,0,0,0.4,-1.7},
		{-1.2,-2.6,-1,-1.8,-0.3,0.7,0,0,0.3,0.1,0,-0.9,0.3,0.1,-0.7,-0.1,0.2,0,-0.3,-0.2},
		{0,-4.7,0.2,0.1,0.2,0.7,-0.1,-0.1,-0.3,0,0.1,0.2,0.2,0.1,-0.7,0,0.4,0,-1.2,-2.6},
		{-0.6,1.6,-0.7,0,-0.5,0.5,0,0.2,0.1,0.6,0,0.3,0.2,0,1.6,0,0.1,0,0,-4.7},
		{-0.5,-1.7,-0.5,0,0.9,0.2,1.9,-0.1,0,1.1,-0.4,0.1,0.2,0.4,-0.8,-2.4,-3.3,0.3,-0.6,1.6},
		{0.6,0,0.7,0,0.7,-0.1,0.3,0.2,-1.6,0.8,0.3,-0.1,0.1,0,-1.5,-2,-2.3,0,-0.5,-1.7},
		{0.1,-1.2,0.1,0.1,-0.1,-0.3,0.1,-0.3,2.3,0.4,0.2,0,0.2,0,1.4,-0.1,0.1,0.3,0.6,0},
		{0.1,1.8,0.1,0,0,-0.2,-0.1,0.4,1.5,0.2,0.1,0.1,0,0.1,2.6,0.1,0,0.8,0.1,-1.2},
		{0.6,-0.5,0.7,0,-0.4,-0.1,0.2,0.3,1,0.1,0.1,0.1,0.1,0,2.6,0.3,0.4,0,0.1,1.8},
		{-0.3,1.7,-0.5,0,0.3,-0.2,0.2,0.5,0.3,0.3,0,0.2,0.2,0.6,2.4,0.2,0.4,0.1,0.6,-0.5},
		{0.6,9.6,0.3,-0.8,2.5,-0.2,0,0,-0.1,0,-1,1.1,0.1,0.1,-0.6,0.3,0,0.4,-0.3,1.7},
		{0.3,2.2,-0.1,2.3,0.2,-0.4,0.3,-0.1,-0.5,0.2,0.1,2.1,0.1,0.2,-1.3,-0.1,-0.3,0.1,0.6,9.6},
		{-0.3,-0.5,-0.3,0,-0.9,-0.5,0.1,0.3,-0.3,0.1,1.6,0.1,0,-0.3,-1.5,0,-0.1,0.1,0.3,2.2},
		{-0.6,-4.3,-0.6,1,0.9,-0.5,0.1,0.2,-0.2,0.2,0,-0.1,-0.1,0.1,-1.7,0,0.2,0,-0.3,-0.5},
		{0.5,-0.7,0.2,3.4,2.3,-0.8,0.1,-0.2,0.2,0.1,-0.1,1.4,0,0.2,0.3,0,0.2,0,-0.6,-4.3},
		{-0.2,0.1,-0.2,0,-0.4,-0.5,0.2,-0.2,0.6,-0.2,-0.1,0,0.1,0.2,0.6,0.4,0.9,0,0.5,-0.7},
		{-0.5,1.1,-0.6,0.1,-2.1,-0.4,-1.3,0.1,0.2,-0.2,-0.5,0.3,0,0,0.3,0.1,0.3,-0.1,-0.2,0.1},
		{-0.3,1.3,-0.4,0.2,-1.9,-0.3,0,0.3,-0.9,0.8,-0.5,0.3,0,-0.1,-0.7,1.9,2,0.4,-0.5,1.1},
		{0.6,0.3,0.6,0.5,-0.9,-0.3,1.2,-0.4,-1.8,0.7,1.4,0.5,0.1,0,-1.7,0,0,0.7,-0.3,1.3},
		{0.4,-3.5,0.7,0,-1.1,-0.3,0.2,0.1,-1.1,0.4,0,0.6,0,-0.1,-1,0,0.2,0,0.6,0.3},
		{-0.2,1.9,-0.6,2.9,-0.4,-0.2,0.2,0.1,-1.1,0.6,-0.2,0.7,-0.2,0.1,-4,0,0.1,-0.1,0.4,-3.5},
		{0.5,0.8,0.5,0.1,0.4,-0.3,0,-0.3,0.3,0.1,-0.4,-0.1,0,-0.5,-4.4,-0.5,-0.1,0.1,-0.2,1.9},
		{0.9,-3.5,0.1,8.2,-2,-0.5,1.6,-1.8,-0.1,-0.4,-0.4,-0.2,-0.1,0.2,-2,-0.4,-1.4,-0.2,0.5,0.8},
		{2.1,1.5,2.4,-0.2,0.4,-0.6,0.1,-1.6,0.3,0.5,-0.1,-0.2,-0.1,0.1,3.1,0.2,1,0,0.9,-3.5},
		{-0.2,1.2,-0.2,-0.5,-0.6,-0.5,-0.2,-0.3,1.4,0.9,-0.6,-0.3,0.2,-0.2,-0.2,0,0.6,0.1,2.1,1.5},
		{-0.7,1.3,-0.9,-0.8,-0.5,-0.6,0.4,-2,0.8,0.6,0,-0.1,0.2,0,1.2,0,0.4,-0.1,-0.2,1.2},
		{-1.1,0.4,-1,-3.8,-1.1,-0.4,-0.1,0.7,-0.4,0.2,0,-1.5,0,-0.1,0.2,0,0.2,0.1,-0.7,1.3},
		{0.4,1.9,0.6,-1.9,0.7,-0.4,-0.5,0,-0.5,0.2,-0.2,-0.3,-0.1,0,1.1,-0.5,0,-0.5,-1.1,0.4},
		{-1.3,-5.1,-1.2,-0.1,1,-0.3,-0.7,-0.5,-0.7,1.9,0,-0.4,0,0,1.6,-0.1,-0.1,-0.2,0.4,1.9},
		{-0.6,3.2,-0.9,-0.1,1.6,-0.3,-0.3,-2.1,0.5,-1.8,0.2,-0.2,-0.1,0.2,0,0.4,0.7,-0.2,-1.3,-5.1},
		{-0.6,1,-0.8,-0.5,1.6,-0.1,-1.4,0.3,-0.9,-0.9,0.3,-0.5,0,0.1,0.8,0,0,-0.4,-0.6,3.2},
		{0.4,4.5,0.2,0.1,1,-0.1,-0.2,-0.9,-0.6,-0.5,1.3,-0.9,0,0,0.3,-0.1,-0.2,0,-0.6,1},
		{-0.1,2.4,0.1,-2.9,0.2,0.1,0,-1.3,0.4,-0.6,0.1,-0.9,0,0,3.7,-0.4,0,0,0.4,4.5},
		{0.2,3.4,0,-0.1,0.3,0.1,0.1,-0.2,0,-0.3,0.1,-0.1,0.1,-0.1,4.2,-0.9,-1.6,0,-0.1,2.4},
		{-1.9,-3.5,-0.6,-9.1,-0.6,0,-2.7,2,-0.4,-0.5,1.1,-1.3,-0.1,-0.1,4.1,0.7,0.6,-0.1,0.2,3.4},
		{-2.6,-5.2,-2.8,-0.6,-0.9,0.1,-0.4,1.2,-0.2,-0.1,0.1,-1,0,-0.2,-3.1,-0.3,-0.9,-0.1,-1.9,-3.5},
		{-0.2,-1.2,-0.3,0.5,0.1,0.1,0.2,0.6,-1.4,0.3,0.3,0.1,-0.4,0,0.2,-0.3,-0.9,-0.1,-2.6,-5.2},
		{2.2,2.3,2.5,-0.3,0.9,0.2,-0.4,0.1,-0.7,-0.5,0.6,-0.1,-0.1,0,0,0,-0.3,-0.1,-0.2,-1.2},
		{2.3,1.8,2.6,0.2,-0.3,0.3,0.1,-0.3,-0.1,-0.2,-0.1,0,-0.2,0.1,-0.3,0,-0.3,0.1,2.2,2.3},
		{0.2,-1.3,0,2.8,-0.6,0.1,0.3,-0.1,1.6,-0.8,0.1,0.3,-0.1,0,-3.3,0,-0.2,0.5,2.3,1.8},
		{1.7,3.9,1.8,-0.1,-0.7,0.1,-0.1,1.3,0.2,-2.6,0.5,0,-0.1,-0.1,-1.8,-0.1,-0.1,0.1,0.2,-1.3},
		{0.2,-2.2,0.4,0,-0.6,0,-0.9,2.6,-0.1,0,-0.3,0,-0.1,0.1,0.2,0.1,-0.1,-0.1,1.7,3.9},
		{0.3,2,0.3,-0.1,0.7,0,0.8,-0.4,2,-0.3,0.8,0.1,-0.1,-0.1,0.6,0,0,-0.2,0.2,-2.2},
		{-0.4,-2.4,-0.5,1,-0.2,-0.1,0.3,0,0.5,0,0,0.4,-0.1,0.1,1.1,-0.1,0,-0.4,0.3,2},
		{0.8,1.9,0.8,0,0.3,-0.1,-0.4,1.5,-0.5,0.5,0,0.1,-0.1,-0.1,-1.2,1.4,-0.1,0,-0.4,-2.4},
		{1.5,4.4,1.6,0.1,0.1,0,-1.1,0,-0.4,0.3,0.4,0.2,0,-0.1,3.2,-0.1,1.9,0.1,0.8,1.9},
		{0,-3.4,0.2,-0.2,0.9,0.3,0.5,-1.9,0.7,0.4,-0.9,1.6,0.5,0.6,0.6,-1.2,-1.3,0.1,1.5,4.4},
		{1.3,3.8,1.7,-1.3,0.1,0.1,0.6,0.4,0.4,-0.3,-0.7,-0.6,0.3,0.1,-1,0.4,0.8,0.1,0,-3.4},
		{2,-1.6,2.5,0,-0.7,0.2,0.2,-0.6,1.8,-1.1,2.4,0.1,0.3,0.1,0.8,0.3,0.3,0,1.3,3.8},
		{-0.7,0.3,-0.9,0,-1.5,0,0.1,2.3,0.4,-0.3,0.7,0.2,-0.1,0.1,0.3,0,0.2,0,2,-1.6},
		{-2.5,-3.1,-2.7,-0.2,-0.1,0.1,0.1,-0.2,1.6,0.2,2,0.2,0.3,0.4,-0.8,-1.1,0,0,-0.7,0.3},
		{-0.7,-0.1,-0.7,-1.1,1,0.1,0.1,0,-2.4,0.6,0,-0.2,0.2,0.1,-0.4,-1.7,-0.6,-0.2,-2.5,-3.1},
		{0.3,-0.5,0.2,0.9,0.8,0.3,0.9,-2.1,-1.1,0.4,-1,0.5,0.1,0.1,-0.3,-0.4,0.3,0.2,-0.7,-0.1},
		{0.9,-2.8,1.2,0.5,-0.1,0.3,1.5,-0.9,-0.8,0.1,-0.8,0.3,0.2,0,1.8,4.6,-0.1,0,0.3,-0.5},
		{1.6,-1.9,2.1,0.2,-1.4,0.2,-0.3,0.4,-1.2,0.1,-2,0.1,0.1,-0.1,-0.3,0,0.1,2.4,0.9,-2.8},
		{0.1,-4,0.5,-1.2,-0.2,0,-0.3,0.5,0.8,-0.1,1.3,0,0.1,-0.2,1.5,0.4,0.3,-0.1,1.6,-1.9},
		{0.6,0.3,0.7,0.1,-0.4,0.1,0.5,-0.7,0.5,-0.4,0,0.2,0.2,2.4,1.9,-0.2,0.4,0,0.1,-4},
		{-0.9,-4.7,-0.9,-0.1,0.3,0.1,1.1,-0.5,0.1,-0.3,-0.5,-0.2,0.1,1.6,-2,0,-0.2,-0.2,0.6,0.3},
		{-0.3,2.1,-0.5,0.8,-0.2,-0.3,-0.5,0.4,-0.2,0.2,0,-0.7,-0.4,-4,0.2,-1.3,-0.3,0.3,-0.9,-4.7},
		{-1.9,-3,-2.2,0,0.3,-0.1,-0.6,-0.4,0,1,0.3,0,-0.2,0.1,1.1,0.2,0.7,0,-0.3,2.1},
		{-1.6,-0.5,-1.9,0,0.6,-0.1,0.3,0.3,-0.8,0.2,-2.6,0,-0.1,0.1,-1,0,0.3,-0.1,-1.9,-3},
		{0,-5.7,0,3.6,-0.2,-0.2,0.4,-0.1,-0.5,0.2,1.8,0.8,0.3,-0.1,-0.6,1.5,0,0,-1.6,-0.5},
		{0.8,2,0.8,0.1,-0.4,-0.2,-0.2,-0.9,-0.5,-0.3,-2,-0.2,-0.1,-0.4,-0.6,1.1,-1.1,0,0,-5.7},
		{-0.8,-2.4,-0.8,-0.1,-1.4,-0.4,-0.3,0,2.7,-0.6,-0.4,-0.1,-0.1,-0.1,0.1,1,0.3,-0.1,0.8,2},
		{-0.8,-1.1,-0.8,-0.8,-0.4,-0.4,-1.3,0.5,-0.4,-0.3,-0.1,-0.5,-0.1,0.1,-1.4,0.3,0.4,-0.2,-0.8,-2.4},
		{-0.6,-0.4,-0.6,-0.6,0.2,-0.4,-0.8,-0.3,0.3,-0.1,0,-0.4,-0.1,0.1,-0.3,-3.5,0,0.2,-0.8,-1.1},
		{-1.2,1.2,-1.5,0,0.2,-0.4,0.3,-0.8,0,0,0.1,-0.1,0,-0.4,1.5,0,-0.4,-2.4,-0.6,-0.4},
		{-0.8,1.3,-1.1,0,0.3,-0.2,-0.5,-1.4,-0.6,0,0.4,-0.4,0.1,-0.2,-2,-0.2,0,0.2,-1.2,1.2},
		{-1.1,-3,-1.1,-0.1,0,-0.2,-0.4,0.8,-0.8,-0.1,-0.2,-0.3,-0.4,-1.9,-2.9,0.2,-0.2,0,-0.8,1.3},
		{-1,-2.3,-1,0,-0.7,-0.2,-0.3,1.3,0,0,0.1,-0.1,0,-1.5,-2,0,-0.1,0,-1.1,-3},
		{-0.1,-2.9,0.5,-3.2,-0.8,-0.1,0.5,-0.4,-0.4,-0.6,-0.4,-2.1,-0.2,2.5,-1.9,-1.5,-0.5,0.1,-1,-2.3},
		{0.9,1,1.1,-0.3,-0.5,-0.2,1.1,0.2,-1,0.2,0,-0.3,0,-0.1,0.2,-3.1,-0.3,-0.2,-0.1,-2.9},
		{-0.4,-1,-0.4,-0.1,-0.3,-0.2,-0.4,-0.8,-1,0.4,-0.1,-0.3,-0.3,-0.1,0.1,-2.8,1.7,0.1,0.9,1},
		{-1.4,-2.2,-1.2,-3.5,-0.2,-0.1,-0.2,-0.4,0.2,0,0.1,-0.9,-0.2,-0.2,-1.7,0,2,-0.1,-0.4,-1},
		{-0.4,-0.3,-0.4,0,0.4,-0.1,0.5,-0.6,-0.1,-0.1,0.3,0,-0.2,0,-2,-2.4,0.3,0,-1.4,-2.2},
		{1.2,0.3,1.4,0.1,0.4,0,0.2,-1.3,-0.9,0.2,0.1,-0.1,-0.3,-0.2,0.7,0,-0.4,-0.1,-0.4,-0.3},
		{0.5,-1,0.7,-0.2,0,0.1,0.3,-0.8,1.8,-0.1,-0.3,1.3,0,-0.1,2.3,0,-0.5,0.1,1.2,0.3},
		{-0.3,1.3,-0.5,0,-0.5,0,0.8,1.1,0,0,0.2,0,-0.2,-0.2,-0.2,0,0,-0.1,0.5,-1},
		{-0.3,-1.6,-0.3,-0.1,0.3,0.1,-0.5,-0.5,0.1,0.1,0.1,-0.1,-0.1,-0.2,0,0,1,-8.4,-0.3,1.3},
		{0,1.7,0,0,-0.1,0,0,1.1,-0.7,-0.1,-0.1,-0.1,-0.3,0,-0.9,2.5,0.2,-0.1,-0.3,-1.6},
		{-0.1,0.6,-0.2,0,0.1,0.1,-0.2,0.3,-0.2,0,-0.1,0,0.3,0.8,0,-4.5,-0.3,0,0,1.7},
		{0.5,0.3,0.7,0,-0.2,0,-0.4,-1.7,-0.2,0.1,0,0.1,-0.3,-0.1,1.4,0.1,-0.5,0,-0.1,0.6},
		{0,1.8,-0.4,2.2,-0.6,0,0.2,2.1,0.1,-0.3,-0.1,0.1,0.2,-1.5,-0.6,3.8,-0.3,-0.2,0.5,0.3},
		{-0.4,-1.3,-0.5,0.2,-0.2,0.1,-0.7,0.6,0,0.2,0.3,0,-0.3,-0.1,-0.3,4.6,-0.2,0,0,1.8},
		{0.1,-0.6,0.1,0.1,0.2,0.1,0.2,-0.7,-0.4,0.1,0.4,-0.1,-0.1,0,-0.9,2.9,-0.9,-0.1,-0.4,-1.3},
		{0.6,3,0.4,0.2,-0.1,0,-0.6,-0.5,-0.9,0.2,-0.1,-0.1,-0.2,-0.1,0.6,-1.4,-1.6,0,0.1,-0.6},
		{-0.3,0.7,-0.3,-0.1,0.1,0.1,-0.6,1.1,-1.1,0,-0.2,-0.1,0.2,0.1,2,0.6,0,0,0.6,3},
		{-0.8,0.8,-1,0,0.1,0.1,-0.4,1.1,-0.1,0.1,-0.1,-0.1,0,0,-0.5,2.4,0.4,0.3,-0.3,0.7},
		{-0.3,3.2,-0.5,0.1,0.3,0.1,0.3,0.4,-0.8,0,-0.2,-1.2,0,-0.3,-0.4,1.3,0,0,-0.8,0.8},
		{0.6,-0.3,0.7,0,0.2,0.3,-0.9,-0.4,-0.4,-0.2,-0.2,0,-0.1,0,-0.5,1.3,0.2,0,-0.3,3.2},
		{-0.1,0.3,-0.1,0.1,0.1,0.1,-0.1,1.6,0.1,-0.2,0.4,0,0,0.7,-1.7,0.1,-0.9,7.2,0.6,-0.3},
		{0.3,0.9,0.2,0,0,0.1,0.4,-0.5,-0.2,0,0.1,-0.1,0.2,0,0.2,-4.4,0.1,0.1,-0.1,0.3},
		{-0.3,0,-0.3,0.1,-0.1,0.1,0.5,-0.5,-0.3,0,0,0,-0.2,-1.4,-0.7,3.4,0.5,0,0.3,0.9},
		{-1.1,-0.1,-1.3,-0.1,0,-0.2,0.3,0.5,-0.7,-0.1,-0.4,-0.1,0,0.1,-2.8,0,0.7,0,-0.3,0},
		{-0.1,-3.2,0,0,-0.2,0.3,0.1,-0.8,-0.7,-0.8,-0.1,0.2,-0.1,2.1,-3.7,-0.2,0.6,-0.1,-1.1,-0.1},
		{0,1.6,-0.1,0,0,0.1,0.2,0,0.2,-0.9,-0.2,-0.1,0,0,-1.4,-0.4,0,0.1,-0.1,-3.2},
		{0.3,4.5,0.2,0,-0.2,0.1,-0.4,0.1,0.2,-0.4,0,0.1,0,-0.4,1.7,-0.3,-0.7,-0.1,0,1.6},
		{-0.2,0.2,-0.2,-0.1,0.4,0,0.6,1.2,1,-0.3,0.2,0.1,0.1,0,0.8,2.8,-0.2,0,0.3,4.5},
		{0.6,-2.8,0.7,0,0.3,0,-0.1,0,0.3,-0.2,0.2,0.1,-0.1,-0.1,1.1,-0.3,0.1,0,-0.2,0.2},
		{0.7,-0.4,0.8,0,0.1,-0.1,0.5,-0.8,0.5,0,0.1,0,0.2,0,0.7,-3.2,0.1,-0.1,0.6,-2.8},
		{-0.4,-4.4,-0.1,0,-0.4,0,0.2,1,0,0,0.3,0,-0.2,1.5,0.3,0.4,-0.3,0,0.7,-0.4},
		{-0.9,-1.5,-1.1,-0.3,0,-0.2,0,0.4,1,0,0.4,-0.1,0.1,0.2,-1.3,-1.3,0.2,-0.1,-0.4,-4.4},
		{-0.1,0.1,0,-0.7,-0.9,0,-0.1,-0.5,0.5,0.1,-0.3,-0.4,0,-0.4,-1.7,-0.2,-1.1,0.1,-0.9,-1.5},
		{0.5,1.3,0.6,-0.2,-0.2,-0.1,1,1,0.6,-0.1,-0.1,0,-0.2,0.3,-0.4,0,0,-0.1,-0.1,0.1},
		{0.5,-1.3,0.6,-0.1,0.1,0,0.1,-0.1,-0.3,0,-0.1,0,0.1,0,2.2,0.6,-0.6,-0.1,0.5,1.3},
		{1,0.1,1.2,0.2,0.1,-0.2,0.3,1,-0.2,0,0.1,0,-0.1,0,1.4,-0.1,-0.2,0,0.5,-1.3},
		{-0.4,-1.4,-0.1,-1.8,0,0.2,-1,-0.9,0.1,0,-0.1,-1,-0.1,-2.5,0.4,-1,0.1,-0.1,1,0.1},
		{-0.3,-0.3,-0.3,-0.3,0.2,0,-1,-1.1,0.4,-0.3,0.1,0,0.1,-0.1,-0.9,0.2,0.2,0,-0.4,-1.4},
		{-0.4,-0.8,-0.4,-0.1,0.2,-0.1,-0.1,0.6,0.4,-0.3,1,-0.1,-0.2,-0.1,-1.1,-0.3,-1.5,0,-0.3,-0.3},
		{0.7,-0.2,0.9,-0.9,0.2,0.1,-0.2,-0.6,-0.2,0.2,-0.4,-0.3,0.4,0.2,0.4,-1.3,-0.9,0,-0.4,-0.8},
		{0.8,2.1,0.8,0.2,-0.3,0.1,0.4,1.1,0.2,0.1,-0.4,-0.1,0,0.2,1.2,0.5,-0.2,0,0.7,-0.2},
		{-0.4,1.3,-0.6,0.4,0,0.1,-0.8,0.5,0.1,-0.1,0.2,0.1,0,0.1,0.8,0.7,0.1,0,0.8,2.1},
		{0.3,5.4,0.2,-0.5,0.3,0.1,-0.2,-0.7,0.1,0.1,-0.6,0,0.2,-1.6,-1.1,-1,0.7,-0.1,-0.4,1.3},
		{0.4,1.1,0.3,0.4,0.1,0.2,0.1,-1.1,0.2,0,-0.1,0.1,0.2,-0.2,0.2,0,0,0,0.3,5.4},
		{0.3,-1.8,0.4,0.5,0.4,0,0.4,0.3,-0.8,0,-0.1,0.4,0,-0.7,4.4,0.2,0.2,0.2,0.4,1.1},
		{0.4,-0.6,0.4,0.3,0.6,0.1,-1,0.2,0,0.2,0.3,0.1,0.2,0,2.8,0.4,0.3,-0.2,0.3,-1.8},
		{1.2,19.6,0.6,-0.1,0.5,0.1,-0.8,-0.6,0.8,0.1,-0.1,0,-0.4,0.3,-0.5,0.1,0.6,0,0.4,-0.6}}

			;

	public static double[][] targetDataSet =new double[][]{{0.3},
		{0.6},
		{-0.2},
		{0},
		{0.3},
		{-0.3},
		{-0.8},
		{0.8},
		{0.7},
		{0.6},
		{0.4},
		{0},
		{0.2},
		{-0.1},
		{-0.1},
		{0.4},
		{0.2},
		{0.2},
		{0},
		{-0.3},
		{-0.3},
		{-0.5},
		{-0.4},
		{-0.5},
		{0.5},
		{0.3},
		{0.4},
		{-0.4},
		{-0.1},
		{0.1},
		{0.1},
		{-0.3},
		{-0.3},
		{0.2},
		{0.2},
		{0},
		{-0.6},
		{-0.3},
		{-0.2},
		{-0.2},
		{0.1},
		{-0.3},
		{0},
		{0.5},
		{0.3},
		{-0.1},
		{0.4},
		{0.5},
		{0},
		{0.7},
		{0.2},
		{0.5},
		{-0.8},
		{-0.1},
		{0.2},
		{-0.4},
		{0.4},
		{0.5},
		{-0.2},
		{-0.5},
		{0.2},
		{-0.4},
		{0.1},
		{-0.4},
		{0.7},
		{-0.3},
		{-0.2},
		{0},
		{-0.4},
		{-0.6},
		{-0.4},
		{-0.7},
		{-0.4},
		{-0.3},
		{-0.2},
		{-0.3},
		{-0.3},
		{0.9},
		{0},
		{-0.1},
		{-0.2},
		{-0.2},
		{0.1},
		{-0.2},
		{0.2},
		{0},
		{-0.4},
		{-0.1},
		{0.1},
		{-0.5},
		{-0.1},
		{0},
		{-0.3},
		{0},
		{-0.4},
		{-0.4},
		{-0.2},
		{0.1},
		{0.4},
		{0.2},
		{0.1},
		{0.1},
		{0.1},
		{-0.2},
		{0.1},
		{0.1},
		{0.1},
		{-0.4},
		{0.1},
		{-0.1},
		{-0.2},
		{0.2},
		{0.1},
		{-0.1},
		{0.1},
		{0.3},
		{0.3},
		{0.2},
		{0.8}}
	;

	public static void runTest(){
		//define class of nns
		int numberOfInputs = inputDataSet[0].length;
		int[] neuronCounts = new int[]{6,1};
		GeneticEngine ge = new GeneticEngine(numberOfInputs,neuronCounts,0.05,1000);
		ge.setSTART_POPULATION_SIZE(1000);
		ge.setMUTATION_RATE(0.01);
		ge.setKillingBehaviour(new TopSurvives(ge,0.1));
		ge.setReproductionBehaviour(new ReproductionBehaviour(ge));
		ge.setMutationBehaviourName("rm");
		ge.setInheritanceBehaviourName("ri");

		ge.initialize();


		ge.runNextTurn(inputDataSet, targetDataSet);
		for (int i = 0; i < 6000; i++) {
			ge.runNextTurn(inputDataSet, targetDataSet);
			System.out.println(i + ".  " + ge.getBestFitness());
		}
		System.out.println("\n");
		ge.printTop();


	}

}

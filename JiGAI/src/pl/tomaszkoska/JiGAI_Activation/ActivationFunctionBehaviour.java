package pl.tomaszkoska.JiGAI_Activation;
/*
 *1-s-sigmoid
 *2-bs-binary sigmoid
 *3-l-linear
 *4-ht-hyperbolicTangent
 * */

import java.io.Serializable;

public abstract class ActivationFunctionBehaviour  implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String shortName;
	public abstract double compute(double value);

	public String getName() {
		return name;
	}
	public String getShortName() {
		return shortName;
	}

}

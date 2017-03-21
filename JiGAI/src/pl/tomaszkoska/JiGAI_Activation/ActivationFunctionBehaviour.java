package pl.tomaszkoska.JiGAI_Activation;
/*
 *1-s-sigmoid
 *2-bs-binary sigmoid
 *3-l-linear
 *4-ht-hyperbolicTangent
 * */


public abstract class ActivationFunctionBehaviour {
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

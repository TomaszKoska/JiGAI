package pl.tomaszkoska.JiGAI_Activation;

public abstract class ActivationFunctionBehaviour {
	protected String name;
	public abstract double compute(double value);

	public String getName() {
		return name;
	}

}

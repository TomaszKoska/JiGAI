package pl.tomaszkoska.JiGAI_Activation;

public class SigmoidActivationFunction extends ActivationFunctionBehaviour {
	double parameter;


	public SigmoidActivationFunction() {
		super();
		this.parameter = 1;
		this.name = "Sigmoid";
		this.shortName = "s";
	}

	public SigmoidActivationFunction(double parameter) {
		super();
		this.parameter = parameter;
		this.name = "Sigmoid";
		this.shortName = "s";
	}

	@Override
	public double compute(double value) {
		return 1/(1+Math.exp((-value)*parameter));
	}

	public double getParameter() {
		return parameter;
	}

	public void setParameter(double parameter) {
		this.parameter = parameter;
	}


}

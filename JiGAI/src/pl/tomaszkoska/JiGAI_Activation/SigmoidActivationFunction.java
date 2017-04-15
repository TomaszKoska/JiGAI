package pl.tomaszkoska.JiGAI_Activation;

public class SigmoidActivationFunction extends ActivationFunctionBehaviour {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SigmoidActivationFunction() {
		super();
		this.name = "Sigmoid";
		this.shortName = "s";
	}

	@Override
	public double compute(double value) {
		return 1/(1+Math.exp((-value)));
	}

	@Override
	public double derivative(double x){
		return (1-x)*x;
	}

}

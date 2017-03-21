package pl.tomaszkoska.JiGAI_Activation;

public class HyperbolicTangentActivationFunction extends ActivationFunctionBehaviour {

	public HyperbolicTangentActivationFunction(){
		super();
		this.name = "Hyperbolic Tangent";
		this.shortName = "ht";
	}

	@Override
	public double compute(double value) {
		return (Math.exp(value)-Math.exp(-value))/(Math.exp(value)+Math.exp(-value));
	}

}

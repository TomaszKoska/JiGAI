package pl.tomaszkoska.JiGAI_Activation;

public class HyperbolicTangentActivationFunction extends ActivationFunctionBehaviour {

	public HyperbolicTangentActivationFunction(){
		this.name = "Hyperbolic Tangent";
	}

	@Override
	public double compute(double value) {
		return (Math.exp(value)-Math.exp(-value))/(Math.exp(value)+Math.exp(-value));
	}

}

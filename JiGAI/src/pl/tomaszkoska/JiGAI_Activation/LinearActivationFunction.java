package pl.tomaszkoska.JiGAI_Activation;

public class LinearActivationFunction extends ActivationFunctionBehaviour{

	public LinearActivationFunction(){
		name = "Linear";
		this.shortName = "l";
	}

	@Override
	public double compute(double value) {
		return value;
	}

}

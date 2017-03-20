package pl.tomaszkoska.JiGAI_Activation;

public class LinearActivationFunction extends ActivationFunctionBehaviour{

	public LinearActivationFunction(){
		name = "Neutral";
	}

	@Override
	public double compute(double value) {
		return value;
	}

}

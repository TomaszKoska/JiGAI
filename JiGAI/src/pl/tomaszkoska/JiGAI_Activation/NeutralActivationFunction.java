package pl.tomaszkoska.JiGAI_Activation;

public class NeutralActivationFunction extends ActivationFunctionBehaviour{

	public NeutralActivationFunction(){
		name = "Neutral";
	}

	@Override
	public double compute(double value) {
		return value;
	}

}

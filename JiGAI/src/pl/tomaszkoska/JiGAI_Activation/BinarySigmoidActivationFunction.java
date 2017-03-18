package pl.tomaszkoska.JiGAI_Activation;

public class BinarySigmoidActivationFunction extends ActivationFunctionBehaviour {

	double parameter;
	double treshold;


	public BinarySigmoidActivationFunction(double parameter, double treshold) {
		super();
		this.parameter = parameter;
		this.treshold = treshold;
		this.name = "Binary Sigmoid";
	}

	@Override
	public double compute(double value) {
		if( 1/(1+Math.exp((-value)*parameter)) >= treshold){
			return 0;
		}else{
			return 1;
		}
	}

	public double getParameter() {
		return parameter;
	}

	public void setParameter(double parameter) {
		this.parameter = parameter;
	}

	public double getTreshold() {
		return treshold;
	}

	public void setTreshold(double treshold) {
		this.treshold = treshold;
	}


}

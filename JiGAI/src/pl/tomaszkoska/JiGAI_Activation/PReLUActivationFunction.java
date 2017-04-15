package pl.tomaszkoska.JiGAI_Activation;

public class PReLUActivationFunction extends ActivationFunctionBehaviour {


	private static final long serialVersionUID = 1L;
	private double a = 0.1;

	public PReLUActivationFunction() {
		super();
		this.name = "PReLU";
		this.shortName = "p";
	}

	@Override
	public double compute(double value) {
		if(value > 0){
			return value;
		}else{
			return a * value;
		}

	}

	@Override
	public double derivative(double x){
		if(x > 0){
			return 1;
		}else{
			return a;
		}
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

}
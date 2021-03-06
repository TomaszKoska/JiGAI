package pl.tomaszkoska.JiGAI_Base;

import java.io.Serializable;

public class LearningSpecification implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected double learningRate;
    protected double learningRateDelta; // if decreasing, how big should the change be?
    protected double startLearningRate; //if decreasing, what is the minimum?
    protected double minLearningRate; //if decreasing, what is the minimum?
    protected double momentum;




    public LearningSpecification(double learningRate, double learningRateDelta, double minLearningRate,
			double momentum) {
		super();
		this.startLearningRate = learningRate;
		this.learningRate = learningRate;
		this.learningRateDelta = learningRateDelta;
		this.minLearningRate = minLearningRate;
		this.momentum = momentum;
	}

    public void resetLearningRate(){
    	this.learningRate=this.startLearningRate;
    }

    public void updateLearningRate(){
    	this.learningRate = Math.max(this.learningRate-this.learningRateDelta, this.minLearningRate);
    }

	public double getLearningRate() {
		return learningRate;
	}

	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	public double getMomentum() {
		return momentum;
	}

	public void setMomentum(double momentum) {
		this.momentum = momentum;
	}

	public double getLearningRateDelta() {
		return learningRateDelta;
	}

	public void setLearningRateDelta(double learningRateDelta) {
		this.learningRateDelta = learningRateDelta;
	}

	public double getMinLearningRate() {
		return minLearningRate;
	}

	public void setMinLearningRate(double minLearningRate) {
		this.minLearningRate = minLearningRate;
	}

	public double getStartLearningRate() {
		return startLearningRate;
	}

	public void setStartLearningRate(double startLearningRate) {
		this.startLearningRate = startLearningRate;
	}

}

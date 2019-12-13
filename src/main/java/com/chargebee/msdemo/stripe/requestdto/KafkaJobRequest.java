package com.chargebee.msdemo.stripe.requestdto;

public class KafkaJobRequest {
	
	private int jobId;
	private Object inputParams;
	
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public Object getInputParams() {
		return inputParams;
	}
	public void setInputParams(Object inputParams) {
		this.inputParams = inputParams;
	}
	@Override
	public String toString() {
		return "KafkaJobRequest [jobId=" + jobId + ", inputParams=" + inputParams + "]";
	}
	
	

}

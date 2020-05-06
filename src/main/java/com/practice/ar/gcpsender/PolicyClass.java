package com.practice.ar.gcpsender;


public class PolicyClass {

	private String policyId;
	private String policyName;
	private String policyC;
	public PolicyClass()
	{
		
	}
	public PolicyClass(String policyId, String policyName, String policyC) {
		super();
		this.policyId = policyId;
		this.policyName = policyName;
		this.policyC = policyC;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyC() {
		return policyC;
	}
	public void setPolicyC(String policyC) {
		this.policyC = policyC;
	}
	
	
}

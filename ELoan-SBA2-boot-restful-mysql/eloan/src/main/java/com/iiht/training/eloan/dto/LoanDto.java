package com.iiht.training.eloan.dto;

public class LoanDto {

	private String loanName;
	private Double loanAmount;
	private String loanApplicationDate;
	private String businessStructure;
	private String billingIndicator;
	private String taxIndicator;
	
	
	public LoanDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public LoanDto(String loanName, Double loanAmount, String loanApplicationDate, String businessStructure,
			String billingIndicator, String taxIndicator) {
		super();
		this.loanName = loanName;
		this.loanAmount = loanAmount;
		this.loanApplicationDate = loanApplicationDate;
		this.businessStructure = businessStructure;
		this.billingIndicator = billingIndicator;
		this.taxIndicator = taxIndicator;
	}
	
	public String getLoanName() {
		return loanName;
	}
	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}
	public Double getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanApplicationDate() {
		return loanApplicationDate;
	}
	public void setLoanApplicationDate(String loanApplicationDate) {
		this.loanApplicationDate = loanApplicationDate;
	}
	public String getBusinessStructure() {
		return businessStructure;
	}
	public void setBusinessStructure(String businessStructure) {
		this.businessStructure = businessStructure;
	}
	public String getBillingIndicator() {
		return billingIndicator;
	}
	public void setBillingIndicator(String billingIndicator) {
		this.billingIndicator = billingIndicator;
	}
	public String getTaxIndicator() {
		return taxIndicator;
	}
	public void setTaxIndicator(String taxIndicator) {
		this.taxIndicator = taxIndicator;
	}

	@Override
	public String toString() {
		return "LoanDto [loanName=" + loanName + ", loanAmount=" + loanAmount + ", loanApplicationDate="
				+ loanApplicationDate + ", businessStructure=" + businessStructure + ", billingIndicator="
				+ billingIndicator + ", taxIndicator=" + taxIndicator + "]";
	}
	
}

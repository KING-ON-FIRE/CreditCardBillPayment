package com.wipro.ccbill.entity;

import java.util.Date;

import com.wipro.ccbill.exception.InputValidationException;
public class CreditCardBill {

	private String creditCardNo;
	private String customerId;
	private Date billDate;
	private Transaction monthTransactions[];

	public CreditCardBill() {
		
	}
	
	public CreditCardBill (String creditCardNo, String customerId, Date BillDate, Transaction monthTransactions[]) {
		this.creditCardNo = creditCardNo;
		this.billDate = BillDate;
		this.customerId = customerId;
		this.monthTransactions = monthTransactions;
	}
	
	public double getTotalAmount(String transactionType) {
		double total=0;
		if(transactionType != "DB" && transactionType != "CR" ) {
			return 0;
		}
		else if(transactionType == "DB" ) {
			for(Transaction i:monthTransactions) {
				if(i.getTransactionType() == "DB") total += i.getTransactionAmount();
			}
		}
		else {
			for(Transaction i:monthTransactions) {
				if(i.getTransactionType() == "CR") total += i.getTransactionAmount();
			}
		}
		return total;
	}
	
	public String validateData() throws InputValidationException{
		if(this.customerId!=null) {
			if(creditCardNo.length() == 16 && this.customerId.length() == 6) return "valid";
		}
		else throw new InputValidationException();
		return null;
	}
	
	public double calculateBillAmount() {
		double d=0;
		try {
			validateData();
			if(this.customerId == null) return 0.0;
			if(this.monthTransactions != null) {
				if(this.monthTransactions.length > 0 ){
					d = getTotalAmount("DB") - getTotalAmount("CR");
					d += d*19.9/1200;
				}
				else return 0.0;
			}
			else return 0.0;
		} 
		catch (InputValidationException e) {
			// TODO Auto-generated catch block
			return 0.0;
		}
		return d;
	}
}

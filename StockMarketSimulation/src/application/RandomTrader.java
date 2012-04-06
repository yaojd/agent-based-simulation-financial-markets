package application;

import utility.ComputePrice;

public class RandomTrader extends Trader {
	
	private ComputePrice computePrice; 

 
	
	public RandomTrader(int cash){
		super(cash); 
	}

	
	public double computeConfidenceScore() {
		
		return 0;
	}

	
	public double computeRisk() {
		
		return 0;
	}

	
	public void overValuedStockComputation() {
		
		
	}
	
	public void computePriceTrends(){
		
	}
	


	
	public void computeBuysSells() {
		
		
	}
	
}

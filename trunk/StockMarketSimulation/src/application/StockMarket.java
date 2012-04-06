package application;

import utility.ComputePrice;

public class StockMarket {
	
	private double currentValueOfStock; 
	private static HistoryList historyList; 
	private ComputePrice computePrice; 
	
	
	public StockMarket(){
		historyList =  new HistoryList(); 
		
	}
	
	public double getCurrentValueOfTheStock(){
		return currentValueOfStock; 
	}
	
	public void setCurrentValueOfTheStock(double currentValOfStock){
		currentValueOfStock = currentValOfStock; 
	}

}

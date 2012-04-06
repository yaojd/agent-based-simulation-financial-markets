package application;

import utility.ComputePrice;

public class StockMarket {
	
	private double currentValueOfStock; 
	private static HistoryList historyList; 
	private ComputePrice computePrice;
	private static StockMarket stockMarket; 
	
	
	public StockMarket(){
		historyList =  new HistoryList(); 
		
	}
	
	public static StockMarket getInstance(){
		if(stockMarket == null){
			stockMarket = new StockMarket(); 
		}
		
		return stockMarket; 
	}
	
	public double getCurrentValueOfTheStock(){
		return currentValueOfStock; 
	}
	
	public void setCurrentValueOfTheStock(double currentValOfStock){
		currentValueOfStock = currentValOfStock; 
	}
	
	public HistoryList getHistoryList(){
		return historyList; 
	}

}

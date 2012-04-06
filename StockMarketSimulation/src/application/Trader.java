package application;

public abstract class Trader {
	
	private int cash; 
	private int amountOfStockHeld; 
	private double currentValueOfStock;
	private StockMarket stockMarket;
	
	
	public Trader(){
		cash = 1000; 
		amountOfStockHeld = 0; 
		currentValueOfStock = stockMarket.getCurrentValueOfTheStock();
	}
	
	
	public abstract double computeConfidenceScore(); 
	
	public abstract double computeRisk(); 
	
	public abstract void computeDividentScore();
	
	public abstract void overValuedStockComputation();
	
	public abstract void computeBuysSells(); 
	
	
	
}

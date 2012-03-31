package application;

import java.util.ArrayList;

public class HistoryList {
	
	
	private Periods period; 
	private ArrayList<Periods> historyList; 
	
	public HistoryList(){
		
		historyList = new ArrayList<Periods>(); 
	}
	
	
	public void addPeriod(double price, double confidenceScore, double risk){
		period = new Periods(price, confidenceScore, risk); 
		
		historyList.add(period); 
	}
}

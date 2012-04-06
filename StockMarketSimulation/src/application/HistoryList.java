package application;

import java.util.ArrayList;

public class HistoryList {
	
	
	private Periods period; 
	private ArrayList<Periods> historyList;
	private int periodNo; 
	
	public HistoryList(){
		
		historyList = new ArrayList<Periods>(); 
		periodNo = 1; 
	}
	
	
	public void addPeriod(double price, double confidenceScore, double risk){
		period = new Periods(price, confidenceScore, risk, periodNo); 
		
		historyList.add(period); 
		periodNo ++; 
	}
}

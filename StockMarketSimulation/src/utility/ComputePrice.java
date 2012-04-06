package utility;

public class ComputePrice {

	private IndexGenerator index;
	private double currentPrice; 
	
	public ComputePrice(){
		index = new IndexGenerator(); 
	}
	
	
	public double currentPrice(){
		
		int currentIndexVal = index.getIndex(); 
		
		currentPrice = currentIndexVal*0.1;  // I am goin to work on a better way to do this 
		//just figured i'd get the ball rolling. 
		
		return currentPrice; 
	}
	
	
	
	
	 
}

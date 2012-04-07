package application;

import utility.*;

public class RandomTrader{
//public class RandomTrader extends Trader {
	
	private ComputePrice computePrice; 

 
    public double currentIndex[];
	
	public RandomTrader(int cash){
		//super(cash);

        System.out.println("Inital Cash");
        System.out.println((int)(cash*.001));

        int numberOfRecords = 1000;
        IndexGenerator idx = new IndexGenerator(1,numberOfRecords );

        double dividends[] = IndexGenerator.getDividends(numberOfRecords);
        double[][] baseline = idx.getIndex();



        
        currentIndex = new double[numberOfRecords];        
        double[] pctChg = new double[numberOfRecords];
        double[] buySell = new double[numberOfRecords];
        double[] profit = new double[numberOfRecords];

        
        for(int i =0; i < numberOfRecords; i++)
        {
            currentIndex[i]= baseline[i][0];
        }

        
        
        pctChg[0] = 0;
        
        double pChg = 0;
        for(int i =1; i < numberOfRecords; i++)
        {
            pChg = currentIndex[i]/currentIndex[i-1] -1.0;
            pctChg[i] = pChg;
        }



        double confidence[] = IndexGenerator.getDividends(numberOfRecords);

        for(int i =0; i < numberOfRecords; i++)
        {
            //buy trigger
            if( pctChg[i] <= -.04)
            {
                buySell[i]=1;
            }

            //sell trigger
            if( pctChg[i] >= .04)
            {
                buySell[i]=-1;
            }
            if (pctChg[i] < .03 && pctChg[i] > -.03 ) {

                buySell[i]= 0;
            }

            //Confidence Triggers:
            if ( i > 1 && confidence[i]-confidence[i-1] > 0 ) {
                buySell[i]=1;
            }

            if ( i > 1 && confidence[i]-confidence[i-1] < 0 ) {
                buySell[i]=-1;
            }

        }

        //# of Days To Hold Stock
        int daysHeld = 10;
       // dal.printArray(buySell);

        double profitCalc = 0;

        for(int i =0; i < numberOfRecords; i++)
        {   //init to zero:
            profit[i] = 0;
        }

        double pctToRisk = .5;

        
        //track ur cash level @ each time period
        double TrackCash[] = new double[numberOfRecords];
        //Calculate Your Profit
        for(int i =0; i < numberOfRecords; i++)
        {
            TrackCash[i]= cash;

            //calc profit on stock buys
            if (buySell[i] == 1.0 && i+daysHeld < numberOfRecords)
            {
                double amtToRisk = cash*pctToRisk;
                int shares =  (int)(amtToRisk/currentIndex[i]);
                double costBasis = shares*currentIndex[i];
                double closeCost = shares*currentIndex[i+daysHeld];
                double profitLoss = closeCost-costBasis;
                cash+=profitLoss;
                TrackCash[i]= cash;

            }

            //calc profit on stock sells
            if (buySell[i] == -1.0 && i+daysHeld < numberOfRecords)
            {
                double amtToRisk = cash*pctToRisk;
                int shares =  (int)(amtToRisk/currentIndex[i]);
                double costBasis = shares*currentIndex[i];
                double closeCost = shares*currentIndex[i+daysHeld];
                double profitLoss = -1.0*(closeCost-costBasis);
                cash+=profitLoss;
                TrackCash[i]= cash;
            }
        }

        //dal.printArray(TrackCash);
        System.out.println("Final Cash");
        System.out.println((int)(cash*.001));
        System.out.println("doing something else");

    }



    public void doSomething()
    {

        
        //dal.printArray(baseline);

        
        






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

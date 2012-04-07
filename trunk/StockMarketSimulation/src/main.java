package utility;


import application.RandomTrader;

public class main {

    public static void main(String[] args) {



        SampleCall1();

        SampleCall2();


        /*

       //EXAMPLE OF GENERATING INDEX:

       int NumberOfRecords = 1000;

       //Generate some Dividends
       double dividends[] = IndexGenerator.getDividends(NumberOfRecords );

       //dal.printArray(dividends);

       // Generate some index values:
       RNG r1 = new RNG(1);
       int S0      = 1000;
       double mu      = .05;
       double sigma   = .32;
       int T       =   5;
       double step    = 1.0/255;

       //Number of companies to simulate:
       int  nCompanies = 1;
       //# of records:
       int N = NumberOfRecords;
       double[][] index = IndexGenerator.getIndex(N, r1, S0, mu, sigma, T, nCompanies, step);

       //dal.printArray(index);
       */

        




    }

    private static void SampleCall2() {

        //example of a trader using the index info...

        int cash = 1000000;
        RandomTrader rnd = new RandomTrader(cash);
    }

    private static void SampleCall1() {

        //shows how to generate 1,000 records of dividends and stock prices

        int numRecords = 1000;

        //get dividends :
        double dividends[] = IndexGenerator.getDividends(numRecords);

        //get index
        int numberOfCompanies = 1;
        IndexGenerator idx = new IndexGenerator(numberOfCompanies,numRecords);
        double[][] baseline = idx.getIndex();

        double[] singleStockIndex = new double[numRecords];

        for(int i =0; i < numRecords; i++)
        {
            singleStockIndex [i]= baseline[i][0];
        }
    }


}

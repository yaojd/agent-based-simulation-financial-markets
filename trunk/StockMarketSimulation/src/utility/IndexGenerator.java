package utility;
// Just created some shell methods work with the price class

public class IndexGenerator {

    private double[][] baselineIndex;

    public IndexGenerator(int nCompanies, int numberOfRecords){

        RNG r1 = new RNG(1);
        int S0      = 1000;
        double mu      = .05;
        double sigma   = .32;
        int T       =   5;
        double step    = 1.0/255;


        //# of records:
        int N = numberOfRecords;
        baselineIndex = IndexGenerator.getIndex(N, r1, S0, mu, sigma, T, nCompanies, step);

	}


    public double getPrice(int CompanyIndex, int rowIndex)
    {
        double result = baselineIndex[CompanyIndex][rowIndex];
        return result;
        //System.out.println(cPrice);
    }

    //runs index simulation
    //returns array of results
    public static double[][] getIndex(int N, RNG r1, int s0, double mu, double sigma, int t, int nCompanies, double step) {
        //begin method

        int nT = N-1;
        //int nT =  (int)Math.ceil(t /step);

        double[][] Normal = new double[nT][nCompanies];
        double[][] cumSum =new double[nT][nCompanies];

        double[][] W =new double[nT][nCompanies];
        double z = (mu -   (sigma*sigma)/2.0)*step ;

        double[] X = new double[nT];
        double[] zX = new double[nT];

        //replication cols:
        double[][] c = new double[nT][nCompanies];
        double[][] S = new double[nT+1][nCompanies];

        //foreach col, then row:
        double cumTotal = 0;
        for (int col = 0; col < nCompanies; col++) {
            cumTotal = 0;

            for (int j = 0; j < nT; ++j) {

                if(col == 0)
                {

                    X[j] = j+1;
                    zX[j] = (j+1.0)*z;
                    //  System.out.println((j+1.0)*z);
                }

                double rnd =  r1.getNormal();
                cumTotal += rnd;
                Normal[j][col] = rnd;
                cumSum[j][col] = cumTotal;
                W[j][col] = sigma * Math.sqrt(step)*cumTotal ;

            }
        }

        //replicate columns
        for(int j = 0; j < nT; ++j)
        {
            for (int col = 0; col < nCompanies; col++)
            {
                c[j][col] = zX[j];

            }

        }


        for (int col = 0; col < nCompanies; col++)
        {
            S[0][col] = s0;
        }

        for(int j = 0; j < nT; ++j)
        {
            for (int col = 0; col < nCompanies; col++)
            {

                S[j+1][col] = s0 * Math.exp( c[j][col] + W[j][col]);
                //c[j][col] = zX[j];

            }

        }
        return S;
    }





    // following is a basic rollup of an index
    // index1 is say autos, and is equal weighted
    // index2 say is insurance

    // avg rows of Index1 are the row averages of all
    // stocks in this index for the particular day

    // IndexRollup  says Autos are 30% rolled up weight
    // and insurance is 70% weight etc...

    /*
   Summary:
   baselineIndex ~ Simulations for N companies on each day
   index1 ~ Index of specific subsets of tickers from baselineindex
   IndexRollup ~ a sector weighted combination of the specific index[i]
    */


/*

// do index rollups:

        int nrows = baselineIndex.length;
        int ncols = baselineIndex[0].length;

        double[][] index1 = new double[nrows][4];

        for(int row =0; row < nrows; row++)
        {
            index1[row][0] = baselineIndex[row][0];
            index1[row][1] = baselineIndex[row][1];
            index1[row][2] = baselineIndex[row][2];
            index1[row][3] = baselineIndex[row][3];
        }

        double[][] index2 = new double[nrows][4];

        for(int row =0; row < nrows; row++)
        {
            index2[row][0] = baselineIndex[row][4];
            index2[row][1] = baselineIndex[row][5];
            index2[row][2] = baselineIndex[row][6];
            index2[row][3] = baselineIndex[row][7];
        }


        double[] avgIndex1 = getArrayColAvgByRow(index1);
        double[] avgIndex2 = getArrayColAvgByRow(index2);

        double[] IndexRollup = new double[nrows];

        for(int row =0; row < nrows; row++)
        {
            //rollup 2 of the indexes:
            IndexRollup[row] = avgIndex1[row]*.3 + avgIndex2[row]*.7;
        }

        //print individual Index Index:
        dal.printArray(avgIndex1);


        //print rolled up Index:
        dal.printArray(IndexRollup);

 */

}

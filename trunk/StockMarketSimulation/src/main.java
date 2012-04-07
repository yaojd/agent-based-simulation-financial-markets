package utility;

public class main {

    public static void main(String[] args) {


        RNG r1 = new RNG(1);
        int S0      = 1000;
        double mu      = .05;
        double sigma   = .32;
        int T       =   5;
        double step    = 1.0/255;

        //Number of companies to simulate:
        int  nCompanies = 1;
        //# of records:
        int N = 4;
        double[][] baselineIndex = IndexGenerator.getIndex(N, r1, S0, mu, sigma, T, nCompanies, step);

        //print Full Index for 8 simulations:
        dal.printArray(baselineIndex);


    }


}

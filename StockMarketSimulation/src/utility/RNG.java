
package utility;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;


public class RNG {

    private static int _N;
    //	private static double[] _set;
    private static long m_w;
    private static long m_z;



    // constructor without seeds
    public RNG(int N) {
        // java.text.DecimalFormat;
        double t = Math.round(java.lang.System.nanoTime());
        NumberFormat df = new DecimalFormat("###.#####");
        String s = df.format(t);

        //old:
        // String s1 = s.substring(0,7);
        //String s2 = s.substring(3,10);


        String s1 =  Long.toString(java.lang.System.nanoTime()).substring(3,10);
        String s2 =  Long.toString(java.lang.System.nanoTime()).substring(4,11);

        int seed1 = Integer.parseInt(s1);
        int seed2 = Integer.parseInt(s2);
        //System.out.println("s1 = " + s1);
        //System.out.println("s2 = " + s2);

        setSeed(seed1, seed2);

        _N = N;
        //_set = generateUniformSet(_N);

    }


    // constructor with seeds
    public RNG(int N, long seed1, long seed2) {
        setSeed(seed1, seed2);
        _N = N;
        //_set = generateUniformSet(_N);
    }

    /*public double[] getStandardizedNumbers() {
            return _set;
        }
    */

    /*
      * George Marsaglia - Multiply With Carry Alg: source:
      * http://en.wikipedia.org/wiki/Random_number_generation
      */

    public static long getRandom() {
        m_z = 36969 * (m_z & 65535) + (m_z >> 16);
        m_w = 18000 * (m_w & 65535) + (m_w >> 16);

        long result = (m_z << 16) + m_w;

        if (result < 0) {
            System.out.println("result less than zero: " + result);
        }

        return result;
    }


    public static double getUniform() {

        return (getRandom()+1E+12)*6.2555E-15;

    }




    public static double getNormal(){
        return (Math.sqrt( Math.log(getUniform())*-2.0))*Math.sin(Math.PI*getUniform()*2.0);
    }

    public static double getNormal(double mean, double stdev)
    {
        return mean + (stdev*getNormal());

    }



    public static double getGamma(double a, double b)
    {

        /*
           This follows procedure described here:
           http://www.cparity.com/projects/AcmClassification/samples/358414.pdf
          */

        /*(1) Setup: d5a-1/3, c51/sqrt(9d).*/

        double d = 0;
        double c = 0;
        double v = 0;
        double x = 0;
        double u = 0;
        double var = 0;

        d = a - (1.0/3.0);
        c = 1.0/Math.sqrt(9.0*d);
        x = getNormal();
        v = Math.pow((1 + c*x), 3);

        /*(2) Generate v5(11cx)�3 with x normal. Repeat if v,50 [rare; requires x, -sqrt(9a-3)].*/

        while (v <= 0)
        {
            x = getNormal();
            v = Math.pow((1 + c*x), 3);

        }


        /* (3) Generate uniform U */

        u = getUniform();

        /* (4) If U , 1-0.0331*x�4 return d*v */

        if (u < 1 - .0331*Math.pow(x,4))
        {

            return d*v*b;
        }

        /* (STEP 5) If log(U) ,0.5*x�21d*(1-v1log(v)) return d*v. */
        else if ( Math.log(u) < (.5*Math.pow(x,2)) + d*(1-v + Math.log(v)))
        {
            return d*v*b;

        }

        /* (6) Go to step 2.*/

        /*
            through testing i found step 6 sufficiently rare , < 1% of the
            time, so instead we can just return a bd normal, which should not change the outcome
          */

        return getNormal(a*b,0);

    }



    /*
     private double[] generateUniformSet(int N) {
         int j;
         double[] set = new double[N];

         for (j = 0; j < N; ++j) {
             set[j] = getRandom();
         }

         Arrays.sort(set);

         double mn = set[0];
         double mx = set[N - 1];

         double[] standardized = set.clone();

         for (j = 0; j < N; ++j) {
             standardized[j] = (standardized[j] - mn) / (mx - mn);
         }

         Arrays.sort(standardized);
         return standardized;
     }

     */

    public static void setSeed(long l1, long l2) {
        if (l1 > 0 && l2 > 0) {
            m_w = l1;
            m_z = l2;
        }

    }




    public static void chisqrNormalityTest(double[] Normal) {
        double mx = dal.max(Normal);
        double mn = dal.min(Normal);
        double std = dal.findStandardDeviation(Normal);
        double avg = dal.arrayAverage(Normal);
        int count = dal.count(Normal);


        int bins = 10;
        double inc = (mx-mn)/(1.*bins);

        System.out.println(avg);



        double[][] histogram = new double[bins][6];


        for(int i = 0; i < bins; i++)
        {
            double st = mn+(inc*i);
            double ed = mn+(inc*(i+1)) - .0000001;
            //	System.out.println("start = " + st + "end = " + ed);

            histogram[i][0] = st;
            histogram[i][1] = ed;

        }

        for(int i = 0; i < bins; i++)
        {
            int counter = 0;
            double st = histogram[i][0];
            double ed = histogram[i][1];

            double cdf = dal.normDist(st, avg, std);


            for(int r =0; r < Normal.length; r++)
            {
                double val = Normal[r];

                if(val > st && val < ed)
                {
                    counter += 1;
                }

            }

            histogram[i][2] = counter;
            histogram[i][3] = cdf;

        }




        for(int i = 0; i < bins-1; i++)
        {
            double cdf = mn+(inc*i);
            histogram[i][4] = histogram[i+1][3];
            double diff = histogram[i][4] - histogram[i][3];
            //expected
            histogram[i][5] = diff*count;
        }


        int df = bins -1;
        double runTotal = 0;

        for(int i = 0; i < bins-1; i++)
        {
            //(act - exp)^2 / exp
            double act =  histogram[i][2];
            double exp = histogram[i][5];
            //System.out.println("act = " + act + " exp = " + exp);

            double x = ((act-exp)*(act-exp))/exp;
            //System.out.println("x = " + x);

            runTotal += x;
        }

        System.out.println(runTotal);



        double[][] chisquared = new double[100][2];


        chisquared[0][0] = 3.84;
        chisquared[1][0] = 5.99;
        chisquared[2][0] = 7.82;
        chisquared[3][0] = 9.49;
        chisquared[4][0] = 11.07;
        chisquared[5][0] = 12.59;
        chisquared[6][0] = 14.07;
        chisquared[7][0] = 15.51;
        chisquared[8][0] = 16.92;
        chisquared[9][0] = 18.31;
        chisquared[10][0] = 19.68;
        chisquared[11][0] = 21.03;
        chisquared[12][0] = 22.36;
        chisquared[13][0] = 23.69;
        chisquared[14][0] = 25;
        chisquared[15][0] = 26.3;
        chisquared[16][0] = 27.59;
        chisquared[17][0] = 28.87;
        chisquared[18][0] = 30.14;
        chisquared[19][0] = 31.41;
        chisquared[20][0] = 32.67;
        chisquared[21][0] = 33.92;
        chisquared[22][0] = 35.17;
        chisquared[23][0] = 36.42;
        chisquared[24][0] = 37.65;
        chisquared[25][0] = 38.89;
        chisquared[26][0] = 40.11;
        chisquared[27][0] = 41.34;
        chisquared[28][0] = 42.56;
        chisquared[29][0] = 43.77;
        chisquared[30][0] = 44.99;
        chisquared[31][0] = 46.19;
        chisquared[32][0] = 47.4;
        chisquared[33][0] = 48.6;
        chisquared[34][0] = 49.8;
        chisquared[35][0] = 51;
        chisquared[36][0] = 52.19;
        chisquared[37][0] = 53.38;
        chisquared[38][0] = 54.57;
        chisquared[39][0] = 55.76;
        chisquared[40][0] = 56.94;
        chisquared[41][0] = 58.12;
        chisquared[42][0] = 59.3;
        chisquared[43][0] = 60.48;
        chisquared[44][0] = 61.66;
        chisquared[45][0] = 62.83;
        chisquared[46][0] = 64;
        chisquared[47][0] = 65.17;
        chisquared[48][0] = 66.34;
        chisquared[49][0] = 67.51;
        chisquared[50][0] = 68.67;
        chisquared[51][0] = 69.83;
        chisquared[52][0] = 70.99;
        chisquared[53][0] = 72.15;
        chisquared[54][0] = 73.31;
        chisquared[55][0] = 74.47;
        chisquared[56][0] = 75.62;
        chisquared[57][0] = 76.78;
        chisquared[58][0] = 77.93;
        chisquared[59][0] = 79.08;
        chisquared[60][0] = 80.23;
        chisquared[61][0] = 81.38;
        chisquared[62][0] = 82.53;
        chisquared[63][0] = 83.68;
        chisquared[64][0] = 84.82;
        chisquared[65][0] = 85.97;
        chisquared[66][0] = 87.11;
        chisquared[67][0] = 88.25;
        chisquared[68][0] = 89.39;
        chisquared[69][0] = 90.53;
        chisquared[70][0] = 91.67;
        chisquared[71][0] = 92.81;
        chisquared[72][0] = 93.95;
        chisquared[73][0] = 95.08;
        chisquared[74][0] = 96.22;
        chisquared[75][0] = 97.35;
        chisquared[76][0] = 98.49;
        chisquared[77][0] = 99.62;
        chisquared[78][0] = 100.75;
        chisquared[79][0] = 101.88;
        chisquared[80][0] = 103.01;
        chisquared[81][0] = 104.14;
        chisquared[82][0] = 105.27;
        chisquared[83][0] = 106.4;
        chisquared[84][0] = 107.52;
        chisquared[85][0] = 108.65;
        chisquared[86][0] = 109.77;
        chisquared[87][0] = 110.9;
        chisquared[88][0] = 112.02;
        chisquared[89][0] = 113.15;
        chisquared[90][0] = 114.27;
        chisquared[91][0] = 115.39;
        chisquared[92][0] = 116.51;
        chisquared[93][0] = 117.63;
        chisquared[94][0] = 118.75;
        chisquared[95][0] = 119.87;
        chisquared[96][0] = 120.99;
        chisquared[97][0] = 122.11;
        chisquared[98][0] = 123.23;
        chisquared[99][0] = 124.34;

        double chiValue = chisquared[df-1][0];

        if(runTotal < chiValue)
        {
            System.out.println("passed Chi Squared Normality Test, failed to reject H0");
        }
        else {
            System.out.println("failed Chi Squared Normality Test, siding with H1");

        }
    }

    /*
      * implentation of Kolmogorov-Smirnov Frequency Test as described here
      * source: http://courses.engr.illinois.edu/ece541/541-RNG.pdf
      */
    public void runKolmogorov(double[] _set) {

        Arrays.sort(_set);

        int nCols = 5;
        int j = 0;

        /*
           KS Table source: http://www.eridlc.com/onlinetextbook/appendix/table7.htm
          */
        double alphaComp = 1.36/Math.sqrt(_N);



        double[][] newArray = new double[_N][nCols];

        for (j = 0; j < _N; ++j) {

            // i
            newArray[j][0] = j + 1;

            // r(i)
            newArray[j][1] = _set[j];

            // i/n
            newArray[j][2] = (j + 1) / ((double) _N);

            // D+ = i/n - r(i)
            newArray[j][3] = (j + 1) / ((double) _N) - _set[j];

            // D- = r(i) - (i-1)/N
            newArray[j][4] = (double) _set[j] - ((double) j / (double) _N);

        }

        // begin... MAX(D+,D-)
        double dp = 0;
        double dm = 0;

        double currentMax = 0;
        double globalMax = 0;

        for (j = 0; j < _N; ++j) {
            dp = newArray[j][3];
            dm = newArray[j][4];

            if (j == 0) {
                currentMax = Math.max(dp, dm);
                globalMax = currentMax;
            } else {
                currentMax = Math.max(dp, dm);

                if (currentMax > globalMax) {
                    globalMax = currentMax;
                }
            }

        }

        // Alpha given confidence @ .05
        // hypothesis test @ .05 level:

        if(globalMax < alphaComp) {
            System.out.println("H0 NOT REJECTED --- Passed Randomness Check");
        }

        else {
            System.out.println("H0 REJECTED --- Failed Randomness Check");
        }

        //dal.saveDoubleArray("c:\\doubleArray.txt", newArray);

    }

}
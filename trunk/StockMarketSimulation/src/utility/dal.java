package utility;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;


/*Helper data access to save output for QA*/
public class dal {

    public static void saveDoubleArray(String fName, double[] array) {

        String aRow = "";
        int j;

        for (j = 0; j < array.length; ++j) {
            aRow = aRow + Double.toString(array[j]) + "\n";
        }

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fName));
            out.write(aRow);
            out.close();
        } catch (IOException e) {
            System.out.println("Exception ");
        }

    }


    //get T statistic given N
    public static double getT(int N)
    {
        if ( N <= 20)
        {
            return 1.812;
        }
        if ( N <= 30)
        {
            return 1.725;
        }
        if ( N <= 40)
        {
            return 1.697;
        }
        if ( N <= 50)
        {
            return 1.684;
        }
        if ( N <= 60)
        {
            return 1.676;
        }
        if ( N <= 70)
        {
            return 1.671;
        }
        if ( N <= 110)
        {
            return 1.66;
        }



        else {
            return 1.645;
        }
    }

    public static void printArray(double[] histogram) {
        int rows = histogram.length;
        //  int cols = histogram[0].length;


        NumberFormat df = new DecimalFormat("#.##");

        for(int r=0; r <rows; r++ )
        {
            //for(int c =0; c < cols; c++)
            //{
            System.out.print(df.format(histogram[r]) + "\n");

            //}
            //System.out.println("");

        }
    }

    public static void printArray(double[][] histogram) {
        int rows = histogram.length;
        int cols = histogram[0].length;
//
        NumberFormat df = new DecimalFormat("#.##");

        for(int r=0; r <rows; r++ )
        {
            for(int c =0; c < cols; c++)
            {
                System.out.print(df.format(histogram[r][c]) + "\t");

            }
            System.out.println("");

        }
    }


    public static void saveDoubleArray(String fName, double[][] array) {

        int j;
        int c = 0;
        int cols = array[1].length;

        String aRow = "";
        String val = "";
        String fullString = "";

        for (j = 0; j < array.length; ++j) {

            for (c = 0; c < cols; ++c) {
                val = Double.toString(array[j][c]);
                aRow = aRow + val;

                if (c != cols - 1) {
                    aRow = aRow + ",";
                }
            }

            fullString = fullString + "\n" + aRow;
            aRow = "";

        }

        fullString = fullString + "\n" + "\n";

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fName));
            out.write(fullString);
            out.close();
        } catch (IOException e) {
            System.out.println("Exception ");
        }

    }


    /*source:  http://www.alina.ch/oliver/faq-excel-normdist.shtml*/

    public static double normDist(double X,double mean, double sigma)
    {
        // Berechnungsformel wurde von Hans Benz COMIT AG ermittelt
        double res = 0;

        final double x = (X - mean) / sigma;

        if (x == 0)
        {
            res = 0.5;
        }
        else
        {
            final double oor2pi = 1 / (Math.sqrt(2 * 3.14159265358979323846));
            double t = 1 / (1 + 0.2316419 * Math.abs(x));
            t *= oor2pi * Math.exp(-0.5 * x * x) *
                    (0.31938153 + t * (-0.356563782 + t *
                            (1.781477937 + t * (-1.821255978 + t * 1.330274429))));

            if (x >= 0)
            {
                res = 1 - t;
            }
            else
            {
                res = t;
            }
        }
        return res;
    }





    public static double findStandardDeviation(double array[]) {
        double mean = arrayAverage(array);

        double d1 = 0;
        double d2 = 0;

        for (int i = 0; i < array.length; i++) {
            d2 = (mean - array[i]) * (mean - array[i]);
            d1 = d2 + d1;
        }

        return Math.sqrt((d1 / (array.length - 1)));

    }

    public static double arrayAverage(double[] normalSet2) {
        double sum = 0;

        for (int j = 0; j < normalSet2.length; ++j) {
            sum += normalSet2[j];
        }

        double avg = sum / ((double) normalSet2.length);

        return avg;
    }



    public static double[] getArrayColAvgByRow(double[][] t) {
        int nrows = t.length;
        int ncols = t[0].length;

        double[] result = new double[nrows];

        //System.out.println(nrows);

        double total = 0;
        double counter = 0;
        for(int r = 0; r < nrows; r++)
        {
            total = 0;
            counter = 0;
            for(int c = 0; c < ncols; c++ )
            {
                counter+=1;
                total += t[r][c];
            }

            double avg = total/counter;
            result[r] = avg;
            //System.out.println("avg = " + avg);
        }
        return result;
    }

    public static double max(double[] normalSet2) {
        double mx = normalSet2[0];

        for (int j = 0; j < normalSet2.length; ++j) {

            if (normalSet2[j] > mx )
            {
                mx = normalSet2[j];
            }
        }

        return mx;
    }


    public static double min(double[] normalSet2) {
        double mn = normalSet2[0];

        for (int j = 0; j < normalSet2.length; ++j) {

            if (normalSet2[j] < mn )
            {
                mn = normalSet2[j];
            }
        }

        return mn;
    }

    public static int count(double[] normalSet2) {
        return normalSet2.length;
    }



    public static double roundvalue(double num) {
        double result = num * 100;
        result = Math.round(result);
        result = result / 100;
        return result;
    }
}

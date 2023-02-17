import java.util.Random;
import java.awt.*;
import java.io.IOException;

public class lab2_q2 
{
    public static int[][][] randomSequence()
    {
        /* sequences of 11 different lengths
        each with 10 random sequences
        random nums between -10 to 10 */

        int seqArray[][][] = new int[11][10][15];
        int length[] = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        Random random = new Random();

        for (int k = 0; k < 11; k++)
            for (int i = 0; i < 10; i++)
                for (int len : length) 
                    for (int j = 0; j < len; j++) 
                        seqArray[k][i][j] = random.nextInt(10 - (-10)) + (-10);

        return seqArray;
    }

    public static void main(String[] args) throws IOException
    {
        int seq[][][] = randomSequence();
        int maxIter[] = new int[11]; // max iteration for each sequence length
        int iter[][] = new int[11][20];  //total num of iteation for each seq length

        for (int k = 0; k < seq.length; k++) 
        {
            for (int i = 0; i < seq[k].length; i++)
            {
                System.out.println("For " + i+1 + "th sequence of length "
                + k+5 + ", the brute force iteration is " + lab2.iterationBF(seq[k][i]) 
                + "\nand improved method iteration is " + lab2.iterationI(seq[k][i]));
                
                for (int j = 0; j < iter.length; ) 
                {
                    //push all the iterations from both methods for each seq length
                    iter[k][j] = lab2.iterationBF(seq[k][i]);
                    iter[k][j++] = lab2.iterationI(seq[k][i]);                    
                }
            }          
        }

        // find max iteration of each seq length and add to maxIter
        for (int i = 0; i < iter.length; i++) 
        {
            int maxm = iter[0][i];
            for (int j = 1; j < iter[i].length; j++)            
                if (iter[j][i] > maxm) 
                {
                    maxm = iter[j][i];
                    for (int k = 0; k < maxIter.length; k++) 
                        maxIter[k] = maxm;
                }            
        }

        // data plot
        Plot.Data curve1 = Plot.data();     // brtue force
        Plot.Data curve2 = Plot.data();     // improved

        for (int x = 5; x < 15; x++)
        {
            curve1.xy(x, x * (x+1)/2 + 1); // y = 1 + n(n+1)/2
            curve2.xy(x, x);     // y = n
        }

        Plot plot = Plot.plot(Plot.plotOpts().
            title("Time Complexity").
            legend(Plot.LegendFormat.BOTTOM)).
            xAxis("x", Plot.axisOpts().
                    range(0, 15)).
            yAxis("y", Plot.axisOpts().
                    range(0, 125)).
            series("y = x * (x+1)/2 + 1", curve1,
                            Plot.seriesOpts().
                                    marker(Plot.Marker.DIAMOND).
                                    markerColor(Color.GREEN).
                                    color(Color.BLACK)).
            series("y = x", curve2,
                    Plot.seriesOpts().
                            marker(Plot.Marker.CIRCLE).
                            markerColor(Color.RED).
                            color(Color.BLUE));

        plot.save("TimeComplexity", "png");


    }
}

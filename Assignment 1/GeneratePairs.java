import java.awt.*;
import java.io.IOException;

public class GeneratePairs 
{
    public int gcdIteration(int m, int n)
    {
        int r;
        int count = 0;
        while ((r = m % n) != 0) 
        {
            m = n;
            n = r;
            count++;    
        }
        return count;
    }

    public static void main(String[] args) throws IOException
    {
        GeneratePairs result = new GeneratePairs();
        int max[] = new int[100];
        for (int i = 0; i < max.length; i++) 
        {
            max[i] = 0;    
        }

        for (int m = 1; m < 101; m++) 
        {
            for (int n = 1; n <= m; n++) 
            {
                max[m-1] = Math.max(max[m-1], result.gcdIteration(m, n));   
            }    
        }

        // find the values of max array
        /*for (int n = 0; n < 100; n++)
        {
            System.out.println("x: " + n + "\ty: " + max[n]);
        }*/

        Plot.Data curve1 = Plot.data();
        Plot.Data curve2 = Plot.data();

        for (int x = 0; x < 100; x++)
        {
            curve1.xy(x, max[x]); // y = max[x]
            curve2.xy(x, 2 * (int)Math.ceil((Math.log(x) / Math.log(2))));     // y = 2 ceil(log_2 x)
        }

        Plot plot = Plot.plot(Plot.plotOpts().
            title("Pairings").
            legend(Plot.LegendFormat.BOTTOM)).
            xAxis("x", Plot.axisOpts().
                    range(0, 100)).
            yAxis("y", Plot.axisOpts().
                    range(0, 20)).
            series("y=x^2", curve1,
                            Plot.seriesOpts().
                                    marker(Plot.Marker.DIAMOND).
                                    markerColor(Color.GREEN).
                                    color(Color.BLACK)).
            series("y=x", curve2,
                    Plot.seriesOpts().
                            marker(Plot.Marker.CIRCLE).
                            markerColor(Color.RED).
                            color(Color.BLUE));

        plot.save("Pairs", "png");
    }
    // the curves start at same point but for x > 1, y =  2 ceil(log x)
}

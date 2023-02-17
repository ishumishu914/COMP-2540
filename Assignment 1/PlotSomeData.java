import java.awt.*;
import java.io.IOException;

public class PlotSomeData
{
        public static void main(String[] args) throws IOException
        {
        // You can modify this to plot your gcdIterations data - sdg
        Plot.Data curve1 = Plot.data();
        Plot.Data curve2 = Plot.data();

        for (int x = 0; x < 100; x++)
        {
                curve1.xy(x, x*x); // y = x^2
                curve2.xy(x, x);     // y = x
        }

        Plot plot = Plot.plot(Plot.plotOpts().
                title("Pairings").
                legend(Plot.LegendFormat.BOTTOM)).
                xAxis("x", Plot.axisOpts().
                        range(0, 9)).
                yAxis("y", Plot.axisOpts().
                        range(0, 81)).
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
}
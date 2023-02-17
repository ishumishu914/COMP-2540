import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class lab5 
{
    public static int iComparison = 0, mComparison = 0;

    public void insertSort(int arr[])
    {
        for (int i = 0; i < arr.length; i++) 
        {
            int key = arr[i];
            int j = i - 1;
            
            // move elements of arr[0..i-1] > key a position forward
            while (j >= 0 && arr[j] > key) 
            {
                arr[j + 1] = arr[i];
                iComparison++;
                j--;    
            }
            arr[j + 1] = key;
        }
    }

    public void merge(int arr[], int l, int m, int r)
    {
        // subarray sizes
        int n1 = m - l + 1;
        int n2 = r - m;

        int left[] = new int[n1];
        int right[] = new int[n2];

        // copy subarray content
        for (int i = 0; i < n1; ++i)
            left[i] = arr[l + i];

        for (int j = 0; j < n2; ++j)
            right[j] = arr[m + 1 + j];

        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2) 
        {
            if (left[i] <= right[j]) 
            {
                arr[k] = left[i];
                mComparison++;
                i++;    
            }
            else
            {
                arr[k] = right[j];
                mComparison++;
                j++;
            }
            k++;
        }

        while (i < n1) 
        {
            arr[k] = left[i];
            i++;
            k++;    
        }

        while (j < n2) 
        {
            arr[k] = right[j];
            j++;
            k++;    
        }
    } 

    public void mergeSort(int arr[], int l, int r)
    {
        if (l < r) 
        {
            //middle point
            int m = l + (r - l) / 2;
            
            // sort 2 subarrays
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // merge the sorted 2 subarrays
            merge(arr, l, m, r);
        }
    }

    public static void main(String[] args) throws IOException 
    {
        /*int arr1[] = {12, 11, 13, 5, 6, 7};
        int arr2[] = {15, 36, 0, -5, 20, 8};

        System.out.println("Before insertion sort:");
        for (int i = 0; i < arr1.length; i++) 
            System.out.print(arr1[i] + " ");    
        System.out.println();
        
        lab5 insertion = new lab5();
        insertion.insertSort(arr1);

        System.out.println("After insertion sort:");
        for (int i = 0; i < arr1.length; i++) 
            System.out.print(arr1[i] + " ");    
        System.out.println();

        System.out.println("Before merge sort:");
        for (int i = 0; i < arr2.length; i++) 
            System.out.print(arr2[i] + " ");    
        System.out.println();

        lab5 merge = new lab5();
        merge.mergeSort(arr2, 0, arr2.length - 1);

        System.out.println("After merge sort:");
        for (int i = 0; i < arr2.length; i++) 
            System.out.print(arr2[i] + " ");    
        System.out.println();*/

        /*int iComp[] = new int[20];
        int mComp[] = new int[20];*/
        
        ArrayList<Integer> iComp = new ArrayList<>(), mComp = new ArrayList<>();
        int x[] = {500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000, 5500, 6000, 6500, 7000, 7500, 8000, 8500, 9000, 9500, 10000};

        for (int n = 500; n <= 10000; n += 500) 
        {
            int I[] = new int[n];
            int M[] = new int[I.length];
            Random random = new Random();

            for (int j = 0; j < n; j++) 
            {
                I[j] = random.nextInt(100);
            }
            
            for (int j = 0; j < I.length; j++)
            {
                M[j] = I[j];
            }   

            lab5 insertion = new lab5();
            insertion.insertSort(I);

            lab5 merge = new lab5();
            merge.mergeSort(M, 0, M.length - 1);

            //need num of comparisons for both sort
            System.out.println("Sorting " + n + " random integers");
            System.out.println("Insertion sort comparisons: " + iComparison);
            System.out.println("Merge sort comparisons: " + mComparison + "\n");

            iComp.add(iComparison);
            mComp.add(mComparison);
            iComparison = 0;
            mComparison = 0;
        }
        
        Plot.Data curve1 = Plot.data();
        Plot.Data curve2 = Plot.data();
        Plot.Data curve3 = Plot.data();
        Plot.Data curve4 = Plot.data();

        for (int i = 0; i < 20; i++) 
        {
            curve1.xy(x[i], iComp.get(i));
            curve2.xy(x[i], mComp.get(i));
            curve3.xy(x[i], Math.pow(x[i], 2));
            curve4.xy(x[i], x[i] * Math.log(x[i]));
        }

        Plot plot = Plot.plot(Plot.plotOpts().title("Sorting Comparisons").
                    legend(Plot.LegendFormat.BOTTOM)).
                    xAxis("n", Plot.axisOpts().range(500, 10000)).
                    yAxis("comparisons", Plot.axisOpts().range(0, 130000)).
                    series("Insertion sort", curve1, Plot.seriesOpts().
                                                    marker(Plot.Marker.CIRCLE).
                                                    markerColor(Color.BLUE).
                                                    color(Color.BLACK)).
                    series("Merge sort", curve2, Plot.seriesOpts().
                                                    marker(Plot.Marker.DIAMOND).
                                                    markerColor(Color.CYAN).
                                                    color(Color.DARK_GRAY)).
                    series("n^2", curve3, Plot.seriesOpts().
                                                    marker(Plot.Marker.SQUARE).
                                                    markerColor(Color.GREEN).
                                                    color(Color.MAGENTA)).
                    series("nlog(n)", curve4, Plot.seriesOpts().
                                                    marker(Plot.Marker.CIRCLE).
                                                    markerColor(Color.MAGENTA).
                                                    color(Color.ORANGE));

        plot.save("Sort Comparisons", "png");
    }
}
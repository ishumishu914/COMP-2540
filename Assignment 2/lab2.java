import java.util.Random;

public class lab2 
{
    // brute force
    public static String searchBF(int sequence[])
    {
        int n = sequence.length;
        int start=0, end=0, maxSum = 0, sum=0, count = n*(n+1) / 2;

        for (int i = 1; i < n; i++) 
        {
            sum = 0;
            for (int j = i; j < n; j++) 
            {
                sum += sequence[j];
                if (sum > maxSum) 
                {
                    maxSum = sum;   
                    start = i;
                    end = j; 
                }    
            }               
        }
        return start + ", " + end + ", " + maxSum + ", iteration: " + count;
    }

    public static int iterationBF(int sequence[])
    {
        int n = sequence.length;
        return n*(n+1) / 2;
    }

    public static String searchI(int sequence[])
    {
        int i = 1, end = -1, start = end;
        int sum = 0, maxSum = sum, count = 0;
        int n = sequence.length;

        for (int j = 1; j < n; j++) 
        {
            count++;
            sum += sequence[j];
            if (sum > maxSum) 
            {
                maxSum = sum;
                start = i;
                end = j;    
            }
            if (sum < 0) 
            {
                i = j + 1;
                sum = 0;    
            }
        }
        return start + ", " + end + ", " + maxSum + ", interations: " + count;
    }

    public static int iterationI(int sequence[])
    {
        return sequence.length;
    }

    public static void main(String[] args) 
    {
        int a[][] = {{-1, -2, -3, -4, -5, -6}, {-1, 1, -1, 1, -1, 1}, {-1, 2, 3, -3, 2}, {1, -5, 2, -1, 3}, {-2, 2, -2, -2, 3, 2}};
                    // 0                        1                       5                   4                   5
        
        System.out.println("MCS using brute force method:-");
        for (int[] i : a) 
        {
            System.out.println(searchBF(i));            
        }

        System.out.println("\nMCS using improved method:-");
        for (int[] j : a) 
        {
            System.out.println(searchI(j));            
        }
    }

}

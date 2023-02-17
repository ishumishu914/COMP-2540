import java.util.Random;

public class lab7 
{
    int heapCount = 0, buildCount = 0, sortCount = 0;

    public void Heapify(int i, int j, int[] arr)
    {
        int k, temp;

        if ((2 * i + 2) <= j)   // arr[i] has 2 children
        {
            heapCount++;
            if ((Math.max(arr[2 * i + 1], arr[2 * i + 2]) == arr[2 * i + 1]))
                k = 2 * i + 1;
            else
                k = 2 * i + 2;
        }
        else if ((2 * i + 1) <= j)  // arr[i] has 1 child
        {
            k = 2 * i + 1;
            heapCount++;
        }
        else    // arr[i] has no child
            return;

        //swap
        if (arr[i] < arr[k]) 
        {
            temp = arr[i];
            arr[i] = arr[k];
            arr[k] = temp;
        }

        Heapify(k, j, arr);
    }

    public void BuildHeap(int[] arr)
    {
        for (int i = (arr.length / 2 - 1); i >= 0; i--)
        {
            Heapify(i, arr.length - 1, arr);
            buildCount++;
        } 
    } 

    public void HeapSort(int[] arr)
    {
        int temp;
        BuildHeap(arr);
        for (int i = arr.length - 1; i >= 1 ; i--) 
        {
            sortCount++;
            temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;    
            Heapify(0, i - 1, arr);       
        }
    }

    public void print(int[] arr)
    {
        for (int i = 0; i < arr.length; i++) 
        {
            System.out.print(arr[i] + " ");
        }
        System.out.println();        
    }

    public static void main(String[] args) 
    {
        lab7 heap = new lab7();

        int[] arr1 = {4, 2, 1, 8, 6, 5, 10, 9, 11, 16}; //{12,10,20,19,8,7,15};
        heap.Heapify(1, 6, arr1);
        heap.print(arr1);
        System.out.println("Time complexity: " + heap.heapCount + "\n");

        int[] arr2 = {12, 10, 20, 19, 8, 7, 15};
        heap.BuildHeap(arr2);
        heap.print(arr2);
        System.out.println("Time complexity: " + heap.buildCount + "\n");

        int[] arr3 = new int[100];
        Random random = new Random();

        for (int i = 0; i < arr3.length; i++) 
            arr3[i] = random.nextInt(500 - 1) + 1;
        
        heap.HeapSort(arr3);
        heap.print(arr3);
        System.out.println("Time complexity: " + heap.sortCount + "\n");
    }    
}
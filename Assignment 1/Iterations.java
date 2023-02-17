import java.util.Scanner;

public class Iterations 
{
    private int m;
    private int n;

    public void gcdIteration(int m, int n)
    {
        this.m = m;
        this.n = n;
        int r;
        int count = 0;
        while ((r = m % n) != 0) 
        {
            m = n;
            n = r; 
            count++;   
        }
        System.out.println("The  GCD (greatest common divisor) for " + this.m + " and " + this.n + " is " + n);
        System.out.println("The number of execution is " + count);
    }
    
    public static void main(String[] args) 
    {
        Iterations result = new Iterations();

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter m: ");
        int m = keyboard.nextInt();
        System.out.print("Enter n: ");
        int n = keyboard.nextInt();
        keyboard.close();   
        result.gcdIteration(m, n);

        result.gcdIteration(14, 21);
        result.gcdIteration(56, 32);
        result.gcdIteration(63, 99);
        result.gcdIteration(210, 54);
        result.gcdIteration(1240, 735);
    }
}

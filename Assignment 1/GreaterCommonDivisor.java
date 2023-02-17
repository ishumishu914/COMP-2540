import java.util.Scanner;

public class GreaterCommonDivisor 
{
    private int m;
    private int n;

    public void GCD(int m, int n)
    {
        this.m = m;
        this.n = n;
        int r;
        while ((r = m % n) != 0) 
        {
            m = n;
            n = r;    
        }
        System.out.println("The  GCD (greatest common divisor) for " + this.m + " and " + this.n + " is " + n);
    }

    public static void main(String[] args) 
    {
        GreaterCommonDivisor result = new GreaterCommonDivisor();

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter m: ");
        int m = keyboard.nextInt();
        System.out.print("Enter n: ");
        int n = keyboard.nextInt();
        keyboard.close();   
        result.GCD(m, n);

        result.GCD(14, 21);
        result.GCD(56, 32);
        result.GCD(63, 99);
        result.GCD(210, 54);
        result.GCD(1240, 735);

        /*
        when (m,n) is (14,21)
        factors of 14: 1, 2, 7, 14
        factors of 21: 1, 3, 7, 21
        GCD: 7

        when (m,n) is (56,32)
        factors of 56: 1,2,4,7,8,14,28,56
        factors of 32: 1,2,4,8,16,32
        GCD: 8
        
        when (m,n) is (63,99)
        factors of 63: 1,3,7,9,21,63
        factors of 99: 1,3,9,11,33,99
        GCD: 9

        when (m,n) is (210,54)
        factors of 210: 1,2,3,5,6,7,10,14,15,21,30,35,42,70,105,210
        factors of 54: 1,2,3,6,9,18,27,54
        GCD: 6

        when (m,n) is (1240,735)
        factors of 1240: 1, 2, 4,5,8,10,20,31
        factors of 735: 1,3,5,15,21,35,49,105,147,245,735
        GCD: 5
        */
    }
}

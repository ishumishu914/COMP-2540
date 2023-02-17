import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class lab3 
{
    static int top = -1;
    //static final int N = 50;
    static int capacity;
    static int wrongPos;

    // push element to stack
    public static char[] push(char[] arr, String str)
    {
        capacity = arr.length;
        // extend stack if full
        if (isFull(arr)) 
        {
            System.out.println("Overflow. Program Terminated\n");
            System.exit(-1);  
        }
        for (int j = 0; j < str.length(); j++) 
        {
            /*if (j < (N - 1)) 
            {
                //j++;
                arr[j] = str.charAt(j);
            }*/
            arr[++top] = str.charAt(j);
        }       
        return arr;
    }

    //pop top element off
    public static char pop(char[] arr)
    {
        //char x = '\0';
        if (isEmpty(arr)) 
        {
            System.out.println("Underflow. Program Terminated\n");
            System.exit(-1);  
        }
        return arr[top--];
    }

    public static int top(char[] arr)
    {
        return !isEmpty(arr) ? arr[top] : null;
    }

    public static int size(char[] arr)
    {
        return arr.length + 1;
    }

    public static boolean isEmpty(char[] arr)
    {
        return top == -1;
    }

    public static boolean isFull(char[] arr)
    {
        return size(arr) == capacity;
    }

    // check if brackers are balanced
    public static boolean isBalanced(String arr)
    {
        /*char[] temp = new char[arr.length()];
        for (int i = 0; i < arr.length(); i++) 
        {
            char x  = arr.charAt(i);
            if (x == '(') 
            {
                push(temp, String.valueOf(x));   
                continue;
            }

            if (x != '(') 
                wrongPos = i;
            
            if (isEmpty(temp))
            {
                wrongPos = i;
                return false;
            } 

            char check;
            switch (x) 
            {
                case ')':
                    check = pop(temp);
                    break;
            }                
        }
        return isEmpty(temp);*/

        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < arr.length(); i++) 
        {
            char x = arr.charAt(i);
            if (x == '(') 
            {
                stack.push(x);
                continue;    
            }    

            if (x != '(') 
                wrongPos = i;

            if (stack.isEmpty()) 
            {
                wrongPos = i;
                return false;
            }                    
            
            //char check;
            switch(x) 
            {
                case ')':
                    stack.pop();
                    break;    
            }
        }
        return stack.isEmpty();
    }

    // Application B: calculator
    public static int calculator()
    {
        Stack<Integer> stack = new Stack<Integer>();
        Scanner inputs = new Scanner(System.in);
        int count = 0, operands = 0, operators = 0;
        String x;   
        ArrayList<String> inputArr = new ArrayList<String>(); 

        do {
            x = inputs.next();
            count++;

            //check if inputs are operands or numbers
            if (Character.isDigit(x.charAt(0))) 
                operands++;
            
            if (x.equals("+") || x.equals("-") || x.equals("*")) 
                operators++;
                
            //check if valid after =
            if (x.equals("="))
            {                
                if (count < 4)
                {
                    System.out.println("Too few inputs!");
                    System.exit(1);                    
                }
                else if (operands != (operators + 1)) 
                {
                    System.out.println("Too many inputs!");
                    System.exit(1);
                }
                else  
                    break;              
            } 
            else
                inputArr.add(x);
                
        } while (true);
        inputs.close();

        for (String input : inputArr) 
        {
            switch (input) 
            {
                case "+":
                case "-":
                case "*":
                    int left = stack.pop();
                    int right = stack.pop();
                    int value = 0;
                    
                    switch (input) 
                    {
                        case "+":
                            value = left + right;
                            break;
                        case "-":
                            value = left - right;
                            break;
                        case "*":
                            value = left * right;
                            break;
                        default:
                            break;
                    }
                    stack.push(value);
                    break;
            
                default:
                    stack.push(Integer.valueOf(input));
                    break;
            }            
        }        
        return stack.pop();
    }

    public static void main(String[] args) 
    {
        /*char[] arr = new char[5];
        System.out.println("Empty: " + isEmpty(arr));    */       
        
        // Application A: parenthesis balance
        System.out.println(isBalanced("()") ? "All brackets matched" : "Mismatched bracket at position " + wrongPos); //b
        System.out.println(isBalanced("()()()()()()()") ? "All brackets matched" : "Mismatched bracket at position " + wrongPos);//b
        System.out.println(isBalanced("((((((((())))))))") ? "All brackets matched": "Mismatched bracket at position " + wrongPos);//nb
        System.out.println(isBalanced("(()((()()())(())))") ? "All brackets matched": "Mismatched bracket at position " + wrongPos);//b 
        System.out.println(isBalanced(")(") ? "All brackets matched": "Mismatched bracket at position " + wrongPos); //nb
        System.out.println(isBalanced("(((((((()))))))") ? "All brackets matched": "Mismatched bracket at position " + wrongPos); //nb
        System.out.println(isBalanced("((()()())))") ? "All brackets matched": "Mismatched bracket at position " + wrongPos); //nb
        System.out.println(isBalanced("()()())()()()") ? "All brackets matched": "Mismatched bracket at position " + wrongPos); //nb 
    
        System.out.println("Start entering integers then operators: ");
        System.out.println(calculator());
    }
}
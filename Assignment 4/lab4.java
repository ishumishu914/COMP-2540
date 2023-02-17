import java.util.ArrayList;
import java.util.Random;

public class lab4 
{
    private int size, front, back;
    private ArrayList<Integer> queue = new ArrayList<>();

    lab4(int size)
    {
        this.size = size;
        front = -1;
        back = -1;
    }

    //insert new element to queue
    public void enqueue(int data) 
    {
        //check if queue full
        if (isFull())
            System.out.println("Queue is full! Element cannot be added");

        //otherwise add
        else if (isEmpty())//(front == -1) 
        {
            front = 0;
            back = 0;
            queue.add(back, data);
        }
        else if (back == size - 1 && front != 0)
        {
            back = 0;
            queue.set(back, data);
        }
        else
        {
            back++;
            if (front <= back) 
                queue.add(back, data);    
            else
                queue.set(back, data);
        }
    }

    //remove element from the front of queue
    public int dequeue()
    {
        //check is empty
        if (isEmpty()) 
        {
            System.out.println("Queue is empty! No element to remove");
            return -1;    
        }
        int temp = queue.get(front);

        //if there's only one element
        if (front == back) 
        {
            front = -1;
            back = -1;    
        }
        else if (front == size - 1) 
            front = 0;
        else
            front++;

        return temp;
    }

    public int front()
    {
        return queue.get(front);
    }

    public int size()
    {
        return queue.size();
    }

    public boolean isEmpty()
    {
        return front == -1;//front == back;
    }

    public boolean isFull()
    {
        return ((front == 0 && back == size - 1) ||
        (back == (front - 1) % size - 1));//((back + 1) % size) == front;
    }

    public void display()
    {
        if (isEmpty()) 
        {
            System.out.println("queue is emtpy");    
        }
        System.out.println("Elements of queue: ");
        if (back >= front) 
        {
            for (int i = front; i <= back; i++) 
            {
                System.out.print(queue.get(i) + " ");    
            }    
            System.out.println();
        }
        else
        {
            for (int i = front; i < size; i++) 
            {
                System.out.print(queue.get(i) + " ");
            }
            for (int i = 0; i <= back; i++) 
            {
                System.out.print(queue.get(i) + " ");    
            }
            System.out.println();
        }
    }

    public static void main(String[] args) 
    {
        //3 servers - 1 queue
        lab4 q1 = new lab4(100);
        Random random = new Random();
        int totWait=0, currentSec = 0, arrived, wait, arrivalT;
        boolean busy[] = {false, false, false};  // 3 servers availability

        while (currentSec >= 0 && q1.size() <= 100) 
        {
            arrivalT = currentSec + random.nextInt(5 - 1) + 1;    //arrival time for customers  
            while (currentSec < arrivalT) 
            {
                currentSec++;
                if (currentSec == arrivalT) 
                {
                    //add customer once the arrival time is over
                    q1.enqueue(currentSec);                    
                }                
            }

            //assign a random server for customer & check availability
            int teller = random.nextInt(3 - 1) + 1;
            if (teller == 1 && busy[teller-1] == true) 
            {
                if (busy[1] == false) 
                    teller = 2;
                else if (busy[2] == false)
                    teller = 3;
            }
            else if (teller == 2 && busy[teller-1] == true) 
            {
                if (busy[0] == false) 
                    teller = 1;
                else if (busy[2] == false)
                    teller = 3;
            }
            else if (teller == 3 && busy[teller-1] == true) 
            {
                if (busy[0] == false) 
                    teller = 1;
                else if (busy[1] == false)
                    teller = 2;
            }

            switch (teller) 
            {
                case 1:
                    busy[0] = true;
                    arrived = currentSec - q1.dequeue();
                    wait = arrived + random.nextInt(20 - 1) + 1; //serviceT;
                    totWait += wait;
                    break;

                case 2:
                    busy[1] = true;
                    arrived = currentSec - q1.dequeue();
                    wait = arrived + random.nextInt(20 - 1) + 1; //serviceT;
                    totWait += wait;
                    break;

                case 3:
                    busy[2] = true;
                    arrived = currentSec - q1.dequeue();
                    wait = arrived + random.nextInt(20 - 1) + 1; //serviceT;
                    totWait += wait;
                    break;
                default:
                    break;
            }
        }
        System.out.println("Total wait time (3 server single queue): " + totWait + " sec");
        System.out.println("Average wait time (3 server single queue): " + (double) totWait/100 + " sec");


        //3 servers - 3 queues
        lab4 q2 = new lab4(34);
        lab4 q3 = new lab4(34);
        lab4 q4 = new lab4(34);
        totWait = 0;
        currentSec = 0;
        arrived = 0;
        wait = 0;
        arrivalT = 0;        
        boolean booked[] = {false, false, false};

        while (currentSec >= 0 && (q2.size() + q3.size() + q4.size()) <= 100) 
        {
            arrivalT = currentSec + random.nextInt(5 - 1) + 1;   //time for customer arrival 
            while (currentSec < arrivalT)  
            {
                currentSec++;
                if (currentSec == arrivalT) 
                {
                    //find the queue with least customer
                    int minQueue = Math.min(Math.min(q2.size(), q3.size()), q4.size());
                    if (minQueue == q2.size()) 
                    {
                        //add customer once the arrival time is over                           
                        q2.enqueue(currentSec);        
                        booked[0] = true; 
                        arrived = currentSec - q2.dequeue();
                        wait = arrived + random.nextInt(20 - 1) + 1; //serviceT;
                        totWait += wait;
                    }
                    else if (minQueue == q3.size()) 
                    {
                        q3.enqueue(currentSec);        
                        booked[1] = true;
                        arrived = currentSec - q3.dequeue();
                        wait = arrived + random.nextInt(20 - 1) + 1; //serviceT;
                        totWait += wait;
                    }
                    else if (minQueue == q4.size()) 
                    {
                        q4.enqueue(currentSec);        
                        booked[2] = true;
                        arrived = currentSec - q4.dequeue();
                        wait = arrived + random.nextInt(20 - 1) + 1; //serviceT;
                        totWait += wait;                           
                    }                                     
                } 
            }
        }
        System.out.println("\nTotal wait time (3 server 3 queues): " + totWait + " sec");
        System.out.println("Average wait time (3 server 3 queues): " + (double) totWait/100 + " sec");
    }
}
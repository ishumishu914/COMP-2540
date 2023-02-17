/* Example simulator for the two-server queueing system
 * of Exercise sheet 7
 * Lecture Network Protocol Design and Evaluation
 *
 * Stefan Ruehrup, <lastname>@informatik.uni-freiburg.de
 * University of Freiburg, Germany
 */

import java.util.*;

public class Simulation 
{ 

    // statistics:		
	static double meanArrival = 1;
	static double meanService = 0.5;
	static int maxNumberOfTasks = 100;
	public static int taskCount = 0;
	
	// simulation clock
	static double simTime = 0.0;
	
	// random number generators for arrival and departure
	static Random rng1 = new Random();
	static Random rng2 = new Random();
	
	// future event set
	static PriorityQueue<Event> eventSet = new PriorityQueue<Event>();  
	
	// servers (with queues)								
	static Server server1 = new Server();	
	static Server server2 = new Server();	
	
	static Dispatcher dispatcher = new Dispatcher(eventSet, server1, server2);
			
							
	public static void main (String args[]) 
	{ 
	    if (args.length == 0) {
				System.err.println("Parameters: \n"+
				"  1. Mean inter-arrival time \n" +
				"  2. Mean service time \n" +
				"  3. Number of tasks \n" +
				"  4. Seed 1 for arrival times \n" +
				"  5. Seed 2 for service times");
				System.exit(0);
		}
	
		if (args.length > 0) {
			try {
				meanArrival = Double.parseDouble(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("First argument must be a floating point value");
				System.exit(1);
			}
		}     
		
		if (args.length > 1) {
			try {
				meanService = Double.parseDouble(args[1]);
			} catch (NumberFormatException e) {
				System.err.println("Second argument must be a floating point value");
				System.exit(1);
			}
		}
		
		if (args.length > 2) {
			try {
				maxNumberOfTasks = Integer.parseInt(args[2]);
			} catch (NumberFormatException e) {
				System.err.println("Third argument must be an integer");
				System.exit(1);
			}
		}
		
		if (args.length > 3) {
			try {
				long seed = Integer.parseInt(args[3]);
				rng1.setSeed(seed);
			} catch (NumberFormatException e) {
				System.err.println("4th argument must be an integer");
				System.exit(1);
			}
		}

		if (args.length > 4) {
			try {
				long seed = Integer.parseInt(args[4]);
				rng2.setSeed(seed);				
			} catch (NumberFormatException e) {
				System.err.println("5th argument must be an integer");
				System.exit(1);
			}
		}		
		
		// Generate first event:
		eventSet.add( new ArrivalEvent( getNextArrival() ) );
		taskCount++;
		
		// Main loop:
		while (!eventSet.isEmpty() && taskCount < maxNumberOfTasks) 
		{
 	        Event e = eventSet.poll();
		    dispatcher.handleEvent(e);
		}  

		// printing results:
		System.out.println("Server 1:");
		System.out.println("Utilization " + server1.getUtilization());
		System.out.println("Waiting Time " + server1.getAvgWaitingTime());
		System.out.println("Queue Length " + server1.getAvgQueueLength());
		System.out.println("Server 2:");
		System.out.println("Utilization " + server2.getUtilization());
		System.out.println("Waiting Time " + server2.getAvgWaitingTime());
		System.out.println("Queue Length " + server2.getAvgQueueLength());

        /* // brief output:
		System.out.println("Server1: " + server1.getUtilization()
								   +" "+ server1.getAvgWaitingTime()
								   +" "+ server1.getAvgQueueLength() );
		System.out.println("Server2: " + server2.getUtilization() 
		                           +" "+ server2.getAvgWaitingTime()
								   +" "+ server2.getAvgQueueLength() );
		*/						   		
	}
	
	
	static public double getNextArrival() {
	    return simTime + randomExponential(rng1, 1/meanArrival);
	}
	
	static public double getNextDeparture() {
	    return simTime + randomExponential(rng2, 1/meanService);
	}

	static double randomExponential(Random rng, double lambda) {
  	    return Math.log( rng.nextDouble() ) / -lambda;
    }
	
	static public void setSimTime(double t) {
	    simTime = t;
	}

	static public double getSimTime() {
	    return simTime;
	}

}	
 
abstract class Event implements Comparable<Event>
{
    protected static int counter=0; 

    protected double scheduledTime;
	protected int number;  // event number (used for tie breaking)
	
	public Event(double scheduledTime) {
	  this.scheduledTime = scheduledTime;
	  number = ++counter;
	}
	
	public double getScheduledTime() {
	  return scheduledTime;
	} 
	
	// comparison method needed for sorting in the priority queue
	public int compareTo(Event e) {
	  if (this.scheduledTime < e.scheduledTime) return -1;
	  else if (this.scheduledTime > e.scheduledTime) return 1;
	  else if (this.number < e.number) return -1;
	  else return 1;
	}
	
	public String toString() {
	  return "Event No. " + number +", scheduled time = " + scheduledTime;
	}
}

class ArrivalEvent extends Event {

    public ArrivalEvent(double scheduledTime) {
	    super(scheduledTime);
	}

	public String toString() {
	  return "Arrival Event, No. " + number +", scheduled time = " + scheduledTime;
	}
}

class DepartureEvent extends Event {

	public Server server;

    public DepartureEvent(double scheduledTime, Server server) {
	    super(scheduledTime);
		this.server = server;
	}	
	
	public String toString() {
	  return "Departure Event, No. " + number +", scheduled time = " + scheduledTime;
	}

}


class Dispatcher {

	PriorityQueue<Event> eventSet; // pointer to event set
    Server server1, server2;       // pointers to servers
	Server currentServer;

    public Dispatcher(PriorityQueue<Event> eventSet, Server s1, Server s2) { 
	    server1 = s1;
		server2 = s2;
		currentServer = s1;
		this.eventSet = eventSet;
	}
	
	public void handleEvent(Event e) {
		Simulation.setSimTime(e.getScheduledTime());	

        System.out.println("Handling event: " + e);

	    if (e instanceof ArrivalEvent)
		    handleArrival( (ArrivalEvent)e );
		else
		    handleDeparture( (DepartureEvent)e );	
	}

    public void handleArrival(ArrivalEvent e) {
	
	    // Task assignment rule: 
		// If both servers are idle, then tasks are assigned alterningly
		// Otherwise, the server with the shorter queue is assigned the task
		if (server1.getQueueLength() < server2.getQueueLength()) 
		    currentServer = server1;
		else if (server1.getQueueLength() > server2.getQueueLength())
		    currentServer = server2;	
		else if (currentServer == server1) currentServer = server2;
		else currentServer = server1;	
	
	    // Task assignment:
	    currentServer.assignTask(e.getScheduledTime());
		// If there is only one task, then the departure can be scheduled
		if (currentServer.getQueueLength() == 1)
 		    eventSet.add( new DepartureEvent( Simulation.getNextDeparture(), currentServer ) );
		// generate the next event of the same type
		eventSet.add( new ArrivalEvent( Simulation.getNextArrival() ) );
		Simulation.taskCount++;
	}
	
    public void handleDeparture(DepartureEvent e) {
	    e.server.completeTask(); 
		// if there are more tasks in the queue, then schedule the next departure:
	    if (!e.server.isIdle())
			eventSet.add( new DepartureEvent( Simulation.getNextDeparture(), e.server ) );
	}	
    
}


class Task {
     public double arrivalTime;
     public double activationTime;

	 public Task(double arrivalTime) {
	     this.arrivalTime = arrivalTime;
	 }
}


class Server {    

    LinkedList<Task> queue = new LinkedList<Task>();
	
	double busyTimeTotal = 0;
	double waitTimeTotal = 0;
	double cumulatedQueueLength = 0;
	int tasksCompleted = 0;
		
	public void assignTask(double arrivalTime) {
	    Task t = new Task(arrivalTime);
		// if the queue is empty, the new task is activated immediately
		if (queue.isEmpty()) t.activationTime = Simulation.getSimTime();
	    queue.add(t);
	}
	
	public void completeTask() {
	    assert(!queue.isEmpty());
		// remove task from queue
	    Task t = queue.poll();
		// calculate statistics:
		tasksCompleted++;
		busyTimeTotal += Simulation.getSimTime() - t.activationTime;	
		waitTimeTotal += t.activationTime - t.arrivalTime;
		cumulatedQueueLength += (t.activationTime - t.arrivalTime) * (queue.size()+1);

        System.out.println("activation: " + t.activationTime + " arrival: " +  t.arrivalTime);

        // if the queue is not empty, then the next task is activated 
		if (!queue.isEmpty())
		    ((Task)queue.peek()).activationTime = Simulation.getSimTime();			
	}
	
	public int getQueueLength() {
	    return queue.size();
	}
	
	public boolean isIdle() {
		return queue.isEmpty();
	}

    public double getUtilization() {
	    return busyTimeTotal / Simulation.getSimTime();
	}
	
	public double getAvgWaitingTime() {
	    return waitTimeTotal / tasksCompleted;
	}

	public double getAvgQueueLength() {
	    return cumulatedQueueLength /  Simulation.getSimTime();
	}
}



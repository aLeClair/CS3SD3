package tutorial1;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

class Baker implements Runnable{
	String name;
	int skill;
	int helping;
	
	public Baker(String name, int skill) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.skill = skill;
	}

	public void run(){
		while(true){
			helpCustomer();
		}
	}

	private synchronized void increaseCounter(){
		Bakery.serving++;
	}
	
	private synchronized int checkCounter(){
		return Bakery.serving;
	}
	
	private void helpCustomer(){
		try {
			Bakery.numCustomers.acquire();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		helping = checkCounter();
		System.out.println("Baker " + name + " now serving: " + helping);
		Bakery.queue.remove();
		increaseCounter();
		int n = Bakery.randomElement.nextInt(3);
		try {
			Thread.sleep(skill*n*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Customer " + helping + " has been helped by " + name + ".");

	}
	
	public void begin(){
		(new Thread(new Baker(this.name, this.skill))).start();
	}
}

class Customer implements Runnable{
	public int number;
	
	public void run(){
		grabTicket();
		Bakery.queue.add(this);
		Bakery.numCustomers.release();
	}

	private synchronized void grabTicket(){
		number = Bakery.ticket;
		System.out.println("Customer " + number + " has entered the shop.");
		Bakery.ticket++;
	}
	
	public void begin(){
		(new Thread(new Customer())).start();
	}
}

public class Bakery {
	public static Queue<Customer> queue = new LinkedList<Customer>();
	public static Semaphore numCustomers; 
	public static int serving, ticket;
	public static Random randomElement = new Random();
	
	public Bakery(){
		numCustomers = new Semaphore(0);
		serving = 0;
		ticket = 0;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Bakery Dempsters = new Bakery();
		Baker b1 = new Baker("Marvin", 1);
		Baker b2 = new Baker("Oscar", 2);
		b1.begin();
		b2.begin();
		while(ticket < 25){
			int n = randomElement.nextInt(4);
			if(n == 2){
				Customer c = new Customer();
				c.begin();
			}
			try {
				Thread.sleep(n*500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

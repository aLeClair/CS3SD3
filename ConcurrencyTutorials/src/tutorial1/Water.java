package tutorial1;

import java.util.Random;
import java.util.concurrent.Semaphore;

//This code is terrible, under revision
class Hydrogen implements Runnable{
	public void run(){
		synchronized (Water.numOxygen){
			synchronized (Water.numHydrogen) {
				Water.numOfHydrogen++;
				Water.numHydrogen.release();
				System.out.println("There are currently " + Water.numOfHydrogen + " Hydrogen atoms!");
				try{
					if((Water.numHydrogen.availablePermits() > 1) && (Water.numOxygen.availablePermits() > 0)){
						System.out.println("We have made " + Water.NUM_WATER + " Water molecules!");
						Water.NUM_WATER++;
						Water.numHydrogen.acquire(2);
						Water.numOfHydrogen = Water.numOfHydrogen - 2;
						Water.numOfOxygen--;
						Water.numOxygen.acquire();	
					}
				} catch (InterruptedException e) {
						//TO DO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public void begin(){
		(new Thread(new Hydrogen())).start();
	}
}

class Oxygen implements Runnable{
	public void run(){
		synchronized (Water.numOxygen){
			Water.numOxygen.release();
			Water.numOfOxygen++;
			System.out.println("There are currently " + Water.numOfOxygen + " Oxygen atoms!");
				synchronized (Water.numHydrogen) {
					try{
						if(Water.numHydrogen.availablePermits() > 1){
							System.out.println("We have made " + Water.NUM_WATER + " Water molecules!");
							Water.NUM_WATER++;
							Water.numHydrogen.acquire(2);
							Water.numOfHydrogen = Water.numOfHydrogen - 2;
							Water.numOfOxygen--;
							Water.numOxygen.acquire();	
						}
					} catch (InterruptedException e) {
							//TO DO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
	}

	public void begin(){
		(new Thread(new Oxygen())).start();
	}
}


public class Water {

	public static int NUM_WATER = 0;
    public static Semaphore numHydrogen, numOxygen;
    public static int numOfHydrogen, numOfOxygen;
	
	public Water(){
		numHydrogen = new Semaphore(0);
		numOxygen = new Semaphore(0);
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Water water = new Water();
		Random randomElement = new Random();
		while (NUM_WATER < 20){
			int n = randomElement.nextInt(30);
			if (n % 3 == 0){
				Oxygen ThreadO = new Oxygen();
				ThreadO.begin();
			}
			else{
				Hydrogen ThreadH = new Hydrogen();
				ThreadH.begin();
			}
		}
	}

}

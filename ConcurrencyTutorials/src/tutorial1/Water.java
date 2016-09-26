package tutorial1;

import java.util.Random;
import java.util.concurrent.Semaphore;

class numHydOxy{
	private int numberH, numberO;
	public numHydOxy(){
		numberH = 0;
		numberO = 0;
	}
	
	public int getH(){
		return numberH;
	}
	public void incH(){
		numberH++;
	}
	
	public int getO(){
		return numberO;
	}
	public void incO(){
		numberO++;
	}
}

class Hydrogen implements Runnable{
	public void run(){
		synchronized (Water.numOxygen){
		synchronized (Water.numHydrogen) {
				Water.numHydrogen.release();
				System.out.println("Hydrogen made!");
				try{
					if((Water.numHydrogen.availablePermits() > 0) && (Water.numHydrogen.availablePermits() % 2 == 0) && (Water.numOxygen.availablePermits() > 0)){
						System.out.println("A water is made!");
						Water.NUM_WATER--;
						Water.numHydrogen.acquire(2);
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
			Water.numOxygen.release();
			System.out.println("Oxygen made!");
		
	}

	public void begin(){
		(new Thread(new Oxygen())).start();
	}
}


public class Water {

	public static int NUM_WATER = 20;
	static numHydOxy num;
    public static Semaphore numHydrogen, numOxygen;
	
	public Water(){
		numHydrogen = new Semaphore(0);
		numOxygen = new Semaphore(0);
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Water water = new Water();
		Random randomElement = new Random();
		while (NUM_WATER > 0){
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

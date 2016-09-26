package tutorial1;

public class MyRunnable implements Runnable{
	public void run(){
		//Executable Code
		System.out.println("Hello from a Runnable THread");
	}

	public void begin(){
		(new Thread(new MyRunnable())).start();
	}
}

package tutorial1;

public class MyThread extends Thread{
	public void run(){
		//Executable Code
		System.out.println("Hello from a AThread");
	}
	
	public void begin(){
		(new MyThread()).start();
	}
}

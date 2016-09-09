package tuts;

public class MyRunnable implements Runnable {
	 
    public void run() {
        System.out.println("Hello from a Runnable class!");
    }
 
    public void begin() {
        (new Thread(new MyRunnable())).start();
    }
 
}

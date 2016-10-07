package tutorial1;

//Under progress
public class MazeProblem {
	
	byte[][] byteMaze = {{(byte) 1100,(byte) 0001,(byte) 0011},
			{(byte) 1001,(byte) 0110,(byte) 1010},
			{(byte) 1110,(byte) 1101,(byte) 0110}};
	
	public class Runner implements Runnable{
		
		private final int start, direction, id;
		
		public Runner(int start, int direction, int id) {
			// TODO Auto-generated constructor stub
			this.start = start;
			this.direction = direction;
			this.id = id;
		}
		
		public void run(){
			
		}

		public void begin(){
			(new Thread(new MyRunnable())).start();
		}
	}
	
}

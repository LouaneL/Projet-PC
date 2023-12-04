package prodcons.v6;

import BaseProdConso.Message;

public class Consommateur extends Thread {
	private ProdConsBuffer buffer;
	private int consTime;

	public Consommateur(ProdConsBuffer buffer, int consTime) {
		this.buffer = buffer;
		this.consTime = consTime;
	}

	public void run() {
		while (true) {
			try {
				Message m = buffer.get();
				System.out.println("Consommateur" + " a consommé le message " + m);
				Thread.sleep(consTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
}

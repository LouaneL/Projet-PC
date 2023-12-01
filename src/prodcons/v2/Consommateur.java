package prodcons.v2;

import BaseProdConso.Message;

public class Consommateur extends Thread {
	private ProdConsBuffer buffer;
	private int consTime;

	public Consommateur(ProdConsBuffer buffer, int consTime) {
		this.buffer = buffer;
		this.consTime = consTime;
	}

	public void run() {
		try {
			Thread.sleep(consTime);
			Message m = buffer.get();
			System.out.println("Consommateur " + this.getName() + " a consommé le message " + m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

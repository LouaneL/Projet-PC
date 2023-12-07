package prodcons.v5;

import BaseProdConso.Message;

public class Consommateur extends Thread {
	private ProdConsBuffer buffer;
	private int consTime;
	private int minCons;
	private int maxCons;

	public Consommateur(ProdConsBuffer buffer, int consTime, int minCons, int maxCons) {
		this.buffer = buffer;
		this.consTime = consTime;
		this.minCons = minCons;
		this.maxCons = maxCons;
	}

	public void run() {
		while (true) {
			try {
				int nb = (int) (Math.random() * (maxCons - minCons) + minCons);
				System.out.println("Essai de consommation "+this.getId()+" de " + nb + " messages");
				Message[] ms = buffer.get(nb);
				Thread.sleep(consTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	}
}
}

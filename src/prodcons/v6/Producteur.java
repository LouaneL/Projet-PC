package prodcons.v6;

import BaseProdConso.Message;

public class Producteur extends Thread {
	private ProdConsBuffer buffer;
	private int prodTime;
	private int minProd;
	private int maxProd;

	public Producteur(ProdConsBuffer buffer, int prodTime, int minProd, int maxProd) {
		this.buffer = buffer;
		this.prodTime = prodTime;
		this.minProd = minProd;
		this.maxProd = maxProd;
	}

	public void run() {
		try {
			Thread.sleep(prodTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int nb = (int) (Math.random() * (maxProd - minProd) + minProd);
		try {
			System.out.println("Producteur " + this.getId() + " - produit n x " + nb + "Message");
			buffer.put(nb,new Message("Message x " + nb,nb));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
}

package prodcons.v3;

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
			buffer.get();
			System.out.println("Consommateur " + this.getId() + " consomme");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

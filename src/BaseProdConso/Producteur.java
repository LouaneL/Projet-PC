package BaseProdConso;

import prodcons.v3.ProdConsBuffer;

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
        while(true) {
            try {
                Thread.sleep(prodTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int nb = (int) (Math.random() * (maxProd - minProd) + minProd);
            for(int i = 0; i < nb; i++) {
                try {
					buffer.put(new Message("Message " + i, i));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }
}

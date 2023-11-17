package BaseProdConso;

import prodcons.v3.ProdConsBuffer;

public class Consommateur extends Thread {
    private ProdConsBuffer buffer;
    private int consTime;
    
    public Consommateur(ProdConsBuffer buffer, int consTime) {
        this.buffer = buffer;
        this.consTime = consTime;
    }
    
    public void run() {
        while(true) {
            try {
                Thread.sleep(consTime);
                buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

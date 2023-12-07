package prodcons.v5;

import java.util.concurrent.Semaphore;

import BaseProdConso.Message;

public class ProdConsBuffer extends prodcons.v1.ProdConsBuffer implements IProdConsBufferMulti {
	Semaphore fifo;

	public ProdConsBuffer(int bufSz) {
		super(bufSz);
		fifo = new Semaphore(1,true);
	}

	@Override
	public Message get() throws InterruptedException {
		// TODO Auto-generated method stub
		return get(1)[1];
	}

	@Override
	public Message[] get(int k) throws InterruptedException {
		Message[] tmp = new Message[k];
		fifo.acquire();
		synchronized (this) {
			int i = 0;
			while (i < k) {
				while (nfull <= 0) {
					try {
						wait();
					} catch (InterruptedException e) {
					}
				}
				tmp[i] = buffer[out];
				System.out.println("Consommateur" + " a consommé le message " + out);
				out = (out + 1) % bufSz;
				i++;
				nfull--;
				nempty++;
			}
			System.out.println("Consommateur" + " a consommé " + k + " messsages");
			notifyAll();
		}
		fifo.release();
		return tmp;
	}

}

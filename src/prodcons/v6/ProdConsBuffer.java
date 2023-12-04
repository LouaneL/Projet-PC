package prodcons.v6;

import BaseProdConso.Message;

public class ProdConsBuffer extends prodcons.v1.ProdConsBuffer implements IProdConsBufferMulti {

	public ProdConsBuffer(int bufSz) {
		super(bufSz);
	}

	@Override
	public synchronized void put(int k, Message m) throws InterruptedException {
		while (k>0) {
			while (nempty <= 0) {
				try {
					wait();
				} catch (InterruptedException e) {
				}
			}
			buffer[in] = m;
			in = (in+1)%bufSz; 
			nfull++;
			nempty--;
			k--;
		}
		notifyAll();
	}
	
	@Override
	public synchronized Message get() throws InterruptedException {
		while (nfull <= 0) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
		Message tmp = buffer[out];
		out = (out+1)%bufSz;
		nfull--;
		nempty++;
		try {
			wait();
		} catch (InterruptedException e) {}
		return tmp;
	}

}

package prodcons.v5;

import BaseProdConso.Message;

public class ProdConsBuffer extends prodcons.v1.ProdConsBuffer implements IProdConsBufferMulti {

	public ProdConsBuffer(int bufSz) {
		super(bufSz);
	}
	
	@Override
	public synchronized Message get() throws InterruptedException {
		// TODO Auto-generated method stub
		return get(1)[1];
	}

	@Override
	public synchronized Message[] get(int k) throws InterruptedException {
		Message[] tmp = new Message[k];
		int i = 0;
		while (i < k) {
			while (nfull <= 0) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
			tmp[i] = buffer[out];
			System.out.println("Consommateur" + " a consommé le message " + out);
			out = (out + 1) % bufSz;
			i++;
			nfull--;
			nempty++;
		}
		notifyAll();
		return tmp;
	}

}

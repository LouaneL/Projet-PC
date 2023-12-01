package prodcons.v5;

import BaseProdConso.Message;

public class ProdConsBuffer extends prodcons.v1.ProdConsBuffer implements IProdConsBufferMulti {

	public ProdConsBuffer(int bufSz) {
		super(bufSz);
	}

	@Override
	public Message[] get(int k) throws InterruptedException {
		Message[] tmp = null;
		int i = 0;
		while (i < k) {
			while (nfull <= 0) {
				try {
					wait();
				} catch (InterruptedException e) {}
			}
			tmp[i] = buffer[out];
			out = (out + 1) % bufSz;
			i++;
			nfull--;
			nempty++;
		}
		return tmp;
	}

}

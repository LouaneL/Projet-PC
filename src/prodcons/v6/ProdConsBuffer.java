package prodcons.v6;

import java.util.concurrent.Semaphore;

import BaseProdConso.Message;

public class ProdConsBuffer implements IProdConsBufferMulti {

	protected int nfull;
	protected int nempty;
	protected Message[] buffer;
	protected int[] nbConsoParMessage;
	protected int bufSz;
	protected int in;
	protected int out;

	public ProdConsBuffer(int bufSz) {
		this.nfull = 0;
		this.nempty = bufSz;
		buffer = new Message[bufSz];
		nbConsoParMessage = new int[bufSz];
		this.bufSz = bufSz;
		in = 0;
		out = 0;
	}

	@Override
	public synchronized void put(int k, Message m) throws InterruptedException {
		while (nempty < k && nfull + k <= bufSz) {
			wait();
		}
		int prev = in;
		buffer[in] = m;
		nbConsoParMessage[in] = k;
		nfull += k;
		nempty -= k;
		in = (in + 1) % bufSz;
		notifyAll();
		while (nbConsoParMessage[prev] > 0){
			wait();
		}
		notifyAll();
	}

	@Override
	public int nmsg() {
		return nempty;
	}

	@Override
	public int totmsg() {
		return nfull;
	}

	@Override
	public synchronized Message get() throws InterruptedException {
		while (nfull == 0) {
			wait();
		}
		Message m = buffer[out];
		nfull--;
		nempty++;
		nbConsoParMessage[out]--;
		int prev = out;
		if (nbConsoParMessage[out] == 0){
			out = (out + 1) % bufSz;
		}
		notifyAll();
		while (nbConsoParMessage[prev] > 0){
			wait();
		}
		notifyAll();
		return m;
	}

	@Override
	public void put(Message m) throws InterruptedException {
		put(1, m);
	}

}

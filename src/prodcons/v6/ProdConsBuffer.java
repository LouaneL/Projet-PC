package prodcons.v6;

import java.util.concurrent.Semaphore;

import BaseProdConso.Message;

public class ProdConsBuffer implements IProdConsBufferMulti {

	private int bufSz;
	private Message[] buffer;
	private int nempty;
	private int nfull;
	private Semaphore empty;
	private Semaphore full;
	private int in;
	private int out;

	public ProdConsBuffer(int bufSz) {
		this.bufSz = bufSz;
		this.nempty = bufSz;
		this.nfull = 0;
		this.empty = new Semaphore(bufSz);
		this.full = new Semaphore(0);
		this.buffer = new Message[bufSz];
		this.in = 0;
		this.out = 0;
	}

	@Override
	public void put(int k, Message m) throws InterruptedException {
		empty.acquire(k);
		synchronized (this) {
			nempty -= k;
		}
		for (int i = 0; i < k; i++) {
			buffer[in] = m;
			in = (in + 1) % bufSz;
		}
		synchronized (this) {
			nfull += k;
		}
		full.release(k);
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
	public void put(Message m) throws InterruptedException {
	 	put(1, m);
	}

	@Override
	public Message get() throws InterruptedException {
		full.acquire();
		synchronized (this) {
			nfull--;
		}
		Message m = buffer[out];
		out = (out + 1) % bufSz;
		synchronized (this) {
			nempty++;
		}
		empty.release();
		return m;
	}

}

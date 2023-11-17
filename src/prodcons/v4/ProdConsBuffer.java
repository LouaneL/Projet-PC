package prodcons.v4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import BaseProdConso.IProdConsBuffer;
import BaseProdConso.Message;

public class ProdConsBuffer implements IProdConsBuffer{

	int nfull;
	int nempty;
	Message[] buffer;
	int bufSz;
	int in;
	int out;

	private ReentrantLock lock;
	private Condition notFull;
	private Condition notEmpty;

	public ProdConsBuffer(int bufSz) {
		this.bufSz = bufSz;
		this.nfull = 0;
		this.nempty = bufSz;
		this.buffer = new Message[bufSz];
		this.in = 0;
		this.out = 0;
		this.lock = new ReentrantLock();
		this.notFull = lock.newCondition();
		this.notEmpty = lock.newCondition();
	}

	public void put(Message m) throws InterruptedException {
		lock.lock();
		while(nempty <= 0) {
			notFull.await();
		}
		buffer[in] = m;
		in = (in + 1) % bufSz;
		nfull++;
		nempty--;
		notEmpty.signal();

		lock.unlock();
	}

	public Message get() throws InterruptedException {
		lock.lock();
		try {
			while(nfull <= 0) {
				notEmpty.await();
			}
			Message m = buffer[out];
			out = (out + 1) % bufSz;
			nfull--;
			nempty++;
			notFull.signal();
			return m;
		} finally {
			lock.unlock();
		}
	}

	@Override
	public int nmsg() {
		return nempty;
	}

	@Override
	public int totmsg() {
		return nfull;
	}
}

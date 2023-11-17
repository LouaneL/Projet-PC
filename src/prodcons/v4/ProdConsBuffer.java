package prodcons.v4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import BaseProdConso.IProdConsBuffer;
import BaseProdConso.Message;

public class ProdConsBuffer implements IProdConsBuffer{
	private int bufSz;
	private Message[] buffer;
	private int in;
	private int out;
	private ReentrantLock lock;
	private Condition notFull;
	private Condition notEmpty;

	public ProdConsBuffer(int bufSz) {
		this.bufSz = bufSz;
		this.buffer = new Message[bufSz];
		this.in = 0;
		this.out = 0;
		this.lock = new ReentrantLock();
		this.notFull = lock.newCondition();
		this.notEmpty = lock.newCondition();
	}

	public void put(Message m) throws InterruptedException {
		lock.lock();
		while (in - out == bufSz) {
			notFull.await();
		}
		buffer[in] = m;
		in = (in + 1) % bufSz;
		notEmpty.signal();
		lock.unlock();

	}

	public Message get() throws InterruptedException {
		lock.lock();
		while (in == out) {
			notEmpty.await();
		}
		Message m = buffer[out];
		out = (out + 1) % bufSz;
		notFull.signal();
		lock.unlock();
		return m;

	}

	public int nmsg() {
		return in - out;
	}

	public int totmsg() {
		return in;
	}
}
